package com.deliveryslots.concurrency;

import com.deliveryslots.BaseIntegrationTest;
import com.deliveryslots.dto.request.ReservationRequest;
import com.deliveryslots.dto.response.ReservationResponse;
import com.deliveryslots.exception.SlotExhaustedException;
import com.deliveryslots.repository.DeliveryWindowRepository;
import com.deliveryslots.repository.WindowZoneCapacityRepository;
import com.deliveryslots.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Estos tests validan que el sistema de locking funciona correctamente
 * con row-level locking de PostgreSQL — no con table-level de H2.
 */
class ConcurrencyTest extends BaseIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private WindowZoneCapacityRepository capacityRepository;

    @Autowired
    private DeliveryWindowRepository windowRepository;

    
     // 10 threads intentan reservar el MISMO slot simultáneamente.
     // Zona Sur (id=3) en ventana 1 tiene capacidad = 1.
     // Solo 1 thread debe ganar, los otros 9 reciben SlotExhaustedException.
    @Test
    void concurrentReservations_shouldNotExceedCapacity() throws Exception {
        // Ventana 1 (15 mar 09-11), Zona Sur (id=3), capacidad = 1
        Long windowId = 1L;
        Long zoneId = 3L;

        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            futures.add(executor.submit(() -> {
                latch.await(); // todos esperan la señal
                ReservationRequest req = new ReservationRequest();
                req.setWindowId(windowId);
                req.setZoneId(zoneId);
                req.setCommune("La Florida");
                req.setCustomerName("Cliente " + idx);
                req.setCustomerAddress("Dirección " + idx);
                try {
                    ReservationResponse r = reservationService.reserve(req);
                    return "SUCCESS:" + r.getId();
                } catch (SlotExhaustedException e) {
                    return "EXHAUSTED";
                }
            }));
        }

        latch.countDown(); // todos los threads arrancan

        List<String> results = new ArrayList<>();
        for (Future<String> f : futures) {
            results.add(f.get());
        }
        executor.shutdown();

        long successes = results.stream().filter(r -> r.startsWith("SUCCESS")).count();
        long exhausted = results.stream().filter(r -> r.equals("EXHAUSTED")).count();

        // Solo 1 debe tener éxito (capacidad = 1 para Zona Sur)
        assertThat(successes).isEqualTo(1);
        assertThat(exhausted).isEqualTo(threadCount - 1);

        // Verificar en BD: capacidad de zona agotada
        var zoneCap = capacityRepository.findByDeliveryWindowIdAndZoneId(windowId, zoneId)
                .orElseThrow();
        assertThat(zoneCap.getReservedCount()).isEqualTo(zoneCap.getTotalCapacity());

        // Verificar en BD: contador global incrementado en 1
        var window = windowRepository.findById(windowId).orElseThrow();
        assertThat(window.getReservedCount()).isEqualTo(1);
    }

    
     // 10 threads cancelan la MISMA reserva simultáneamente.
     // Solo 1 debe decrementar los contadores, los otros 9 ven que ya está cancelada (idempotencia).
     
    @Test
    void concurrentCancellations_shouldDecrementOnlyOnce() throws Exception {
        // Usar ventana 2 (15 mar 11-13), Zona Norte (id=1), capacidad=2
        Long windowId = 2L;
        Long zoneId = 1L;

        // Crear una reserva para después cancelarla
        ReservationRequest req = new ReservationRequest();
        req.setWindowId(windowId);
        req.setZoneId(zoneId);
        req.setCommune("Huechuraba");
        req.setCustomerName("Test Cancel");
        req.setCustomerAddress("Dirección test");
        ReservationResponse reservation = reservationService.reserve(req);
        Long reservationId = reservation.getId();

        // Verificar que la reserva incrementó los contadores
        var capBefore = capacityRepository.findByDeliveryWindowIdAndZoneId(windowId, zoneId)
                .orElseThrow();
        assertThat(capBefore.getReservedCount()).isEqualTo(1);

        // Lanza 10 threads cancelando la misma reserva
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            futures.add(executor.submit(() -> {
                latch.await();
                ReservationResponse result = reservationService.cancel(reservationId);
                return result.getStatus().name();
            }));
        }

        latch.countDown();

        List<String> results = new ArrayList<>();
        for (Future<String> f : futures) {
            results.add(f.get());
        }
        executor.shutdown();

        // Todos reciben CANCELLED (el primero la canceló, los demás la vieron cancelada)
        assertThat(results).allMatch(r -> r.equals("CANCELLED"));

        // Verificar que los contadores volvieron a 0 — solo UN decremento ocurrió
        var capAfter = capacityRepository.findByDeliveryWindowIdAndZoneId(windowId, zoneId)
                .orElseThrow();
        assertThat(capAfter.getReservedCount()).isEqualTo(0);

        var windowAfter = windowRepository.findById(windowId).orElseThrow();
        assertThat(windowAfter.getReservedCount()).isEqualTo(0);
    }
}