package com.deliveryslots.repository;

import com.deliveryslots.entity.DeliveryWindow;

import jakarta.persistence.LockModeType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface DeliveryWindowRepository extends JpaRepository<DeliveryWindow, Long> {
 
    // Todas las ventanas de una fecha que tienen capacidad configurada para una zona
    // Incluye agotadas - el frontend necesita mostrar "Agotado".
    @Query("""
        SELECT DISTINCT w FROM DeliveryWindow w
        JOIN WindowZoneCapacity c ON c.deliveryWindow = w
        WHERE c.zone.id = :zoneId 
            AND w.date = :date
        ORDER BY w.startTime
    """)
    List<DeliveryWindow> findAllByZoneAndDate(
        @Param("zoneId") Long zoneId,
        @Param("date") LocalDate date
    );

    

    // Fechas que tienen al menos una ventana configurada para la zona.
    @Query("""
        SELECT DISTINCT w.date FROM DeliveryWindow w
        JOIN WindowZoneCapacity c ON c.deliveryWindow = w
        WHERE c.zone.id = :zoneId 
            AND w.date >= :fromDate
        ORDER BY w.date
    """)
    List<LocalDate> findDatesByZone(
        @Param("zoneId") Long zoneId,
        @Param("fromDate") LocalDate fromDate
    );


    // SELECT FOR UPDATE: lock exclusivo sobre la ventana.
    // Mientras un thread tiene este lock, otros threads que intenten reservar la misma ventana quedarán bloqueados esperando
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM DeliveryWindow w WHERE w.id = :id")
    Optional<DeliveryWindow> findByIdWithLock(@Param("id") Long id);

}
