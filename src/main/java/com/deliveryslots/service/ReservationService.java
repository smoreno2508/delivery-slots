package com.deliveryslots.service;

import com.deliveryslots.dto.request.ReservationRequest;
import com.deliveryslots.dto.response.ReservationResponse;
import com.deliveryslots.entity.DeliveryWindow;
import com.deliveryslots.entity.Reservation;
import com.deliveryslots.entity.WindowZoneCapacity;
import com.deliveryslots.entity.Zone;
import com.deliveryslots.enums.ReservationStatus;
import com.deliveryslots.exception.ResourceNotFoundException;
import com.deliveryslots.exception.SlotExhaustedException;
import com.deliveryslots.repository.DeliveryWindowRepository;
import com.deliveryslots.repository.ReservationRepository;
import com.deliveryslots.repository.WindowZoneCapacityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final WindowZoneCapacityRepository capacityRepository;
    private final DeliveryWindowRepository windowRepository;
    private final ZoneService zoneService;

    public ReservationService(ReservationRepository reservationRepository,
                              WindowZoneCapacityRepository capacityRepository,
                              DeliveryWindowRepository windowRepository,
                              ZoneService zoneService) {
        this.reservationRepository = reservationRepository;
        this.capacityRepository = capacityRepository;
        this.windowRepository = windowRepository;
        this.zoneService = zoneService;
    }

    @Transactional
    public ReservationResponse reserve(ReservationRequest request) {
        log.info("Reservando ventana {} zona {} comuna '{}'",
                request.getWindowId(), request.getZoneId(), request.getCommune());

        // 1. Validar que la comuna pertenece a la zona declarada.
        Zone resolvedZone = zoneService.resolveByCommune(request.getCommune());
        if (!resolvedZone.getId().equals(request.getZoneId())) {
            throw new IllegalArgumentException(
                    "La comuna '" + request.getCommune() + "' pertenece a '" +
                    resolvedZone.getName() + "', no a la zona " + request.getZoneId());
        }

        // 2. Lock sobre capacidad global de la ventana.
        DeliveryWindow window = windowRepository.findByIdWithLock(request.getWindowId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ventana no encontrada con id: " + request.getWindowId()));

        if (window.getReservedCount() >= window.getCapacityTotal()) {
            log.warn("Slot agotado (global) ventana {} — rechazando", request.getWindowId());
            throw new SlotExhaustedException(
                    "Ventana agotada (capacidad global) en horario " +
                    window.getStartTime() + "-" + window.getEndTime() +
                    " del " + window.getDate());
        }

        // 3. Lock sobre capacidad de la zona en esta ventana.
        WindowZoneCapacity capacity = capacityRepository
                .findByWindowIdAndZoneIdWithLock(request.getWindowId(), request.getZoneId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe capacidad configurada para ventana " +
                        request.getWindowId() + " en zona " + request.getZoneId()));

        if (capacity.getReservedCount() >= capacity.getTotalCapacity()) {
            log.warn("Slot agotado (zona '{}') ventana {} — rechazando",
                    resolvedZone.getName(), request.getWindowId());
            throw new SlotExhaustedException(
                    "Ventana agotada para zona '" + resolvedZone.getName() +
                    "' en horario " + window.getStartTime() + "-" +
                    window.getEndTime() + " del " + window.getDate());
        }

        // 4. Incrementar ambos contadores.
        window.setReservedCount(window.getReservedCount() + 1);
        windowRepository.save(window);

        capacity.setReservedCount(capacity.getReservedCount() + 1);
        capacityRepository.save(capacity);

        // 5. Crear la reserva.
        Reservation reservation = new Reservation();
        reservation.setDeliveryWindow(window);
        reservation.setZone(resolvedZone);
        reservation.setCommune(request.getCommune().trim());
        reservation.setCustomerName(request.getCustomerName());
        reservation.setCustomerAddress(request.getCustomerAddress());
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation = reservationRepository.save(reservation);

        log.info("Reserva {} creada para '{}' — capacidad restante zona: {}, global: {}",
                reservation.getId(), request.getCustomerName(),
                capacity.getAvailableCapacity(), window.getAvailableCapacity());

        return ReservationResponse.from(reservation);
    }

    @Transactional(readOnly = true)
    public ReservationResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        return ReservationResponse.from(reservation);
    }

    @Transactional
    public ReservationResponse cancel(Long id) {
        // Lock sobre la reserva: serializa cancelaciones concurrentes.
        Reservation reservation = reservationRepository.findByIdWithLock(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));

        // Idempotente: si ya está cancelada, retornar sin modificar.
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            log.warn("Cancelación idempotente — reserva {} ya estaba cancelada", id);
            return ReservationResponse.from(reservation);
        }

        // Decrementar capacidad global.
        DeliveryWindow window = windowRepository.findByIdWithLock(reservation.getDeliveryWindow().getId())
                .orElseThrow();
        window.setReservedCount(window.getReservedCount() - 1);
        windowRepository.save(window);

        // Decrementar capacidad de zona.
        WindowZoneCapacity capacity = capacityRepository
                .findByWindowIdAndZoneIdWithLock(
                        reservation.getDeliveryWindow().getId(),
                        reservation.getZone().getId())
                .orElseThrow();
        capacity.setReservedCount(capacity.getReservedCount() - 1);
        capacityRepository.save(capacity);

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

        log.info("Reserva {} cancelada — capacidad liberada zona: {}, global: {}",
                id, capacity.getAvailableCapacity(), window.getAvailableCapacity());

        return ReservationResponse.from(reservation);
    }
}