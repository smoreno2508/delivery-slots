package com.deliveryslots.repository;

import com.deliveryslots.entity.Reservation;

import jakarta.persistence.LockModeType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // LOCK para cancelacion: serializa cancelaciones concurrentes sobre la misma reserva, evitando doble decremento
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Reservation r WHERE r.id = :id") 
    Optional<Reservation> findByIdWithLock(@Param("id") Long id);

}
