package com.deliveryslots.repository;

import com.deliveryslots.entity.WindowZoneCapacity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface WindowZoneCapacityRepository extends JpaRepository<WindowZoneCapacity, Long> {
    
    // Lock para reserva: serializa operaciones sobre la misma zona + ventana
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM WindowZoneCapacity c WHERE c.deliveryWindow.id = :windowId AND c.zone.id = :zoneId")
    Optional<WindowZoneCapacity> findByWindowIdAndZoneIdWithLock(
        @Param("windowId") Long windowId,
        @Param("zoneId") Long zoneId
    );

    //sin lock: para consultas de lectura (listar ventanas disponibles)
    Optional<WindowZoneCapacity> findByDeliveryWindowIdAndZoneId(Long windowId, Long zoneId);

    @Query("""
        SELECT c FROM WindowZoneCapacity c
        JOIN FETCH c.deliveryWindow w
        WHERE c.zone.id = :zoneId
        AND w.date >= :fromDate
        ORDER BY w.date, w.startTime
    """)
    List<WindowZoneCapacity> findAllWithWindowByZoneAndDateFrom(
            @Param("zoneId") Long zoneId,
            @Param("fromDate") LocalDate fromDate);

    //Suma de capacidades por zona de una ventana
    // Util para validar integridad de configuracion.
    @Query("SELECT COALESCE(SUM(c.totalCapacity), 0) FROM WindowZoneCapacity c WHERE c.deliveryWindow.id = :windowId")
    int sumTotalCapacityByWindowId(@Param("windowId") Long windowId);

}
