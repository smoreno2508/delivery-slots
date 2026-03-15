# Delivery Slots — Sistema de Reserva de Ventanas de Despacho

Sistema de reserva de ventanas de despacho para órdenes de retail con cobertura geográfica, control de capacidad por zona y manejo seguro de concurrencia.

## Requisitos

- **Java 17** (JDK)
- **Maven 3.9+**
- **Docker** (para tests de concurrencia y ejecución con PostgreSQL)

## Ejecución

### Opción 1: H2 in-memory (sin dependencias externas)

```bash
mvn clean spring-boot:run
```

### Opción 2: PostgreSQL con Docker Compose

```bash
docker-compose up --build
```

*Nota: La primera ejecución puede tardar unos segundos mientras Docker descarga la imagen de PostgreSQL.*

Ambas opciones inician en `http://localhost:8080`.

| URL | Descripción |
|---|---|
| http://localhost:8080 | Frontend de reservas |
| http://localhost:8080/swagger-ui.html | Documentación interactiva de la API |
| http://localhost:8080/h2-console | Consola H2 (solo en Opción 1, JDBC URL: `jdbc:h2:mem:deliveryslots`, user: `sa`, sin password) |

## Tests

```bash
mvn test
```

Requiere Docker corriendo (Testcontainers levanta PostgreSQL automáticamente).

| Tipo | Clase | Qué valida |
|---|---|---|
| Unit (Mockito) | `ReservationServiceTest` | Lógica de reserva: contadores, validación comuna-zona, capacidad agotada |
| Integration (MockMvc) | `ReservationControllerTest` | Endpoints HTTP: 201 Created, 400 Bad Request, 409 Conflict |
| Concurrencia (Testcontainers) | `ConcurrencyTest` | 10 threads concurrentes contra PostgreSQL con row-level locking |

### Tests de concurrencia

Se ejecutan contra PostgreSQL real (no H2) para validar el comportamiento de `SELECT FOR UPDATE` con row-level locking nativo. Testcontainers levanta un contenedor PostgreSQL temporal, ejecuta los tests, y lo destruye.

- **Reserva concurrente**: 10 threads compiten por el último slot de Zona Sur (capacidad=1). Solo 1 gana, los otros 9 reciben `409 Conflict`.
- **Cancelación concurrente**: 10 threads cancelan la misma reserva. Solo 1 decrementa los contadores, los otros 9 ven que ya está cancelada (idempotencia).

## API REST

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/zones` | Listar zonas geográficas |
| `GET` | `/api/zones/resolve?commune={nombre}` | Resolver zona por comuna |
| `GET` | `/api/zones/{id}/communes` | Comunas cubiertas por una zona |
| `GET` | `/api/windows/dates?zoneId={id}` | Fechas disponibles para una zona |
| `GET` | `/api/windows?zoneId={id}&date={yyyy-MM-dd}` | Ventanas horarias por zona y fecha |
| `POST` | `/api/reservations` | Crear reserva |
| `GET` | `/api/reservations/{id}` | Consultar reserva |
| `DELETE` | `/api/reservations/{id}` | Cancelar reserva |

### Ejemplo: Flujo completo

```bash
# 1. Resolver zona por comuna
curl "http://localhost:8080/api/zones/resolve?commune=Providencia"

# 2. Consultar fechas disponibles
curl "http://localhost:8080/api/windows/dates?zoneId=2"

# 3. Ver horarios de una fecha
curl "http://localhost:8080/api/windows?zoneId=2&date=2026-03-15"

# 4. Reservar
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{"windowId":1,"zoneId":2,"commune":"Providencia","customerName":"Juan Pérez","customerAddress":"Av. Providencia 1234"}'

# 5. Cancelar
curl -X DELETE http://localhost:8080/api/reservations/1
```
> Colección de Postman disponible en `docs/DeliverySlots.postman_collection.json` para importar y probar todos los endpoints.

## Modelo de dominio

### Cobertura geográfica

Cada zona agrupa un conjunto de comunas. Al ingresar su comuna, el sistema resuelve automáticamente la zona de cobertura correspondiente y determina qué ventanas y capacidad están disponibles para esa dirección.

Al reservar, el backend valida server-side que la comuna pertenece a la zona declarada en el request. No se confía en el `zoneId` del cliente — se resuelve desde la comuna y se compara. Si no coinciden, la reserva es rechazada.

### Capacidad dual

Cada ventana de despacho tiene dos niveles de capacidad:

1. **Capacidad global** (`delivery_windows.capacity_total`): Tope absoluto de despachos por bloque horario, representando la capacidad operacional (vehículos, personal).
2. **Capacidad por zona** (`window_zone_capacity.total_capacity`): Slots asignados a cada zona dentro de la ventana.

Al reservar se validan ambos niveles. La capacidad disponible real para un cliente es el mínimo entre ambos: si la zona tiene 2 slots pero la global solo tiene 1 disponible, solo se puede reservar 1.

capacidadDisponible = min(capacidadZonaDisponible, capacidadGlobalDisponible)

## Estrategia de concurrencia

### Reserva

1. **Pessimistic Locking** (`SELECT ... FOR UPDATE`): Lock exclusivo sobre la fila de la ventana (capacidad global) y sobre la fila de capacidad zona+ventana. Serializa reservas concurrentes al mismo slot.
2. **Optimistic Locking (`@Version`)**: Campo de versión presente en las entidades como defensa adicional ante actualizaciones concurrentes fuera del flujo principal de reserva.
3. **CHECK constraints en BD**: Red de seguridad a nivel de base de datos que rechaza operaciones que excedan la capacidad.

### Cancelación

La cancelación lockea la reserva primero con `SELECT FOR UPDATE`, y después valida el estado. Esto previene doble decremento:

Sin lock: Hilo A lee `CONFIRMED` → Hilo B lee `CONFIRMED` → ambos decrementan → doble decremento.
Con lock: Hilo A lockea la reserva → Hilo B espera → A cancela y libera → B lee `CANCELLED` → operación sin efecto.

La cancelación es idempotente: si la reserva ya está cancelada, el servicio retorna la reserva sin modificar contadores.

### ¿Por qué pessimistic locking?

En ventanas de despacho populares (horarios de mañana), la contención es alta. Con solo optimistic locking, múltiples transacciones podrían leer la misma versión y competir al momento del commit. y solo uno ganaría al hacer commit — el resto tendría que reintentar. Pessimistic locking serializa de entrada, evitando trabajo desperdiciado.

### Producción: consideraciones adicionales

- PostgreSQL implementa `SELECT FOR UPDATE` con row-level locking nativo, permitiendo reservas paralelas en ventanas diferentes.
- Se podría agregar `SKIP LOCKED` para mejorar throughput en muy alta concurrencia.
- Timeouts configurables en los locks para evitar deadlocks prolongados.
- En escenarios de altísima carga, un sistema de colas podría complementar este enfoque para desacoplar el procesamiento de reservas, aunque el control de consistencia seguiría estando en la base de datos.

## Base de datos

### Perfiles

El sistema soporta dos bases de datos:

- **H2** (perfil default): BD in-memory para desarrollo y evaluación rápida. No requiere instalación.
- **PostgreSQL** (perfil `pg`): BD de producción. Se activa con `docker-compose up` o con `-Dspring.profiles.active=pg`.

### Migraciones

El esquema se gestiona con Flyway. Las migraciones están versionadas en `src/main/resources/db/migration/{vendor}/` con sintaxis específica para cada motor. Hibernate solo valida que las entidades coincidan con el esquema (`ddl-auto: validate`), nunca modifica la BD.

## Datos semilla

- 3 zonas: Norte (8 comunas), Centro (7 comunas), Sur (8 comunas)
- 48 ventanas de despacho entre el 15-30 de marzo 2026 (3 bloques/día)
- Capacidad global: 5 (mañana/mediodía) o 4 (tarde)
- Capacidad por zona: Norte=2, Centro=1-2, Sur=1

## Tecnologías

- Java 17, Spring Boot 3.2.5
- Spring Data JPA + Hibernate 6
- H2 + PostgreSQL (perfiles)
- Flyway (migraciones)
- Testcontainers (tests de concurrencia)
- SpringDoc OpenAPI (Swagger UI)
- Thymeleaf + vanilla JS (frontend)
- Docker + Docker Compose
- JUnit 5 + Mockito + AssertJ
