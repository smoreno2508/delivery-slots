# ============================
# Etapa 1: Build
# Usa Maven + JDK para compilar el proyecto y generar el .jar
# ============================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Primero copia solo el pom.xml y descarga dependencias.
# Docker cachea esta capa — si el pom no cambia, no vuelve a descargar.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Ahora copia el código fuente y compila.
# -DskipTests: no correr tests en el build de Docker (ya los corrimos con mvn test)
COPY src ./src
RUN mvn package -DskipTests -B

# ============================
# Etapa 2: Runtime
# Solo JRE (más liviano que JDK), copia el jar compilado y lo ejecuta
# ============================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia el jar de la etapa de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]