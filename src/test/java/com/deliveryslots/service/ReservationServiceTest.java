package com.deliveryslots.service;

import com.deliveryslots.dto.request.ReservationRequest;
import com.deliveryslots.dto.response.ReservationResponse;
import com.deliveryslots.entity.DeliveryWindow;
import com.deliveryslots.entity.Reservation;
import com.deliveryslots.entity.WindowZoneCapacity;
import com.deliveryslots.entity.Zone;
import com.deliveryslots.exception.ResourceNotFoundException;
import com.deliveryslots.exception.SlotExhaustedException;
import com.deliveryslots.repository.DeliveryWindowRepository;
import com.deliveryslots.repository.ReservationRepository;
import com.deliveryslots.repository.WindowZoneCapacityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock private ReservationRepository reservationRepository;
    @Mock private WindowZoneCapacityRepository capacityRepository;
    @Mock private DeliveryWindowRepository windowRepository;
    @Mock private ZoneService zoneService;

    
    @InjectMocks
    private ReservationService reservationService;

    // Objetos que reutilizamos en cada test
    private DeliveryWindow window;
    private Zone zone;
    private WindowZoneCapacity capacity;


    @BeforeEach
    void setUp() {
        window = new DeliveryWindow();
        window.setId(1L);
        window.setDate(LocalDate.of(2026, 3, 15));
        window.setStartTime(LocalTime.of(9, 0));
        window.setEndTime(LocalTime.of(11, 0));
        window.setCost(new BigDecimal("2990"));
        window.setCapacityTotal(5);
        window.setReservedCount(0);

        zone = new Zone("Zona Norte", "Sector norte");
        zone.setId(1L);

        capacity = new WindowZoneCapacity();
        capacity.setId(1L);
        capacity.setDeliveryWindow(window);
        capacity.setZone(zone);
        capacity.setTotalCapacity(2);
        capacity.setReservedCount(0);
    }

    private ReservationRequest buildRequest(Long windowId, Long zoneId) {
        ReservationRequest request = new ReservationRequest();
        request.setWindowId(windowId);
        request.setZoneId(zoneId);
        request.setCommune("Huechuraba");
        request.setCustomerName("Juan Pérez");
        request.setCustomerAddress("Av. Principal 123");
        return request;
    }

    @Test
    void reserve_shouldCreateReservationAndIncrementBothCounters() {
        // Arrange: configurar qué retornan los mocks
        ReservationRequest request = buildRequest(1L, 1L);

        when(zoneService.resolveByCommune("Huechuraba")).thenReturn(zone);
        when(windowRepository.findByIdWithLock(1L)).thenReturn(Optional.of(window));
        when(capacityRepository.findByWindowIdAndZoneIdWithLock(1L, 1L))
                .thenReturn(Optional.of(capacity));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(inv -> {
            Reservation r = inv.getArgument(0);
            r.setId(1L);
            return r;
        });

    
        ReservationResponse result = reservationService.reserve(request);

        // Assert: verificar resultados
        assertThat(result.getCustomerName()).isEqualTo("Juan Pérez");
        assertThat(result.getCommune()).isEqualTo("Huechuraba");
        assertThat(capacity.getReservedCount()).isEqualTo(1);
        assertThat(window.getReservedCount()).isEqualTo(1);

        // Verificar que se guardaron los cambios
        verify(capacityRepository).save(capacity);
        verify(windowRepository).save(window);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void reserve_shouldThrowWhenCommuneDoesNotMatchZone() {
        // Providencia pertenece a Zona Centro (id=2), no a Zona Norte (id=1)
        Zone zoneCentro = new Zone("Zona Centro", "Centro");
        zoneCentro.setId(2L);

        ReservationRequest request = buildRequest(1L, 1L);
        request.setCommune("Providencia");

        when(zoneService.resolveByCommune("Providencia")).thenReturn(zoneCentro);

        assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Zona Centro");

        // Verificar que NUNCA se intentó lockear la ventana ni guardar nada
        verify(windowRepository, never()).findByIdWithLock(any());
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void reserve_shouldThrowWhenGlobalCapacityExhausted() {
        window.setReservedCount(5); // lleno a nivel global

        ReservationRequest request = buildRequest(1L, 1L);

        when(zoneService.resolveByCommune("Huechuraba")).thenReturn(zone);
        when(windowRepository.findByIdWithLock(1L)).thenReturn(Optional.of(window));

        assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(SlotExhaustedException.class)
                .hasMessageContaining("capacidad global");
    }

    @Test
    void reserve_shouldThrowWhenZoneCapacityExhausted() {
        capacity.setReservedCount(2); // lleno a nivel de zona

        ReservationRequest request = buildRequest(1L, 1L);

        when(zoneService.resolveByCommune("Huechuraba")).thenReturn(zone);
        when(windowRepository.findByIdWithLock(1L)).thenReturn(Optional.of(window));
        when(capacityRepository.findByWindowIdAndZoneIdWithLock(1L, 1L))
                .thenReturn(Optional.of(capacity));

        assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(SlotExhaustedException.class);
    }

    @Test
    void reserve_shouldThrowWhenWindowNotFound() {
        ReservationRequest request = buildRequest(999L, 1L);

        when(zoneService.resolveByCommune("Huechuraba")).thenReturn(zone);
        when(windowRepository.findByIdWithLock(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.reserve(request))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}