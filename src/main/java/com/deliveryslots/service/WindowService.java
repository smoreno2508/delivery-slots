package com.deliveryslots.service;

import com.deliveryslots.dto.response.DateAvailabilityResponse;
import com.deliveryslots.dto.response.WindowResponse;
import com.deliveryslots.entity.DeliveryWindow;
import com.deliveryslots.entity.WindowZoneCapacity;
import com.deliveryslots.repository.DeliveryWindowRepository;
import com.deliveryslots.repository.WindowZoneCapacityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WindowService {

    private final DeliveryWindowRepository windowRepository;
    private final WindowZoneCapacityRepository capacityRepository;

    public WindowService(DeliveryWindowRepository windowRepository,
                         WindowZoneCapacityRepository capacityRepository) {
        this.windowRepository = windowRepository;
        this.capacityRepository = capacityRepository;
    }

    public List<WindowResponse> findByZoneAndDate(Long zoneId, LocalDate date) {
        List<DeliveryWindow> windows = windowRepository.findAllByZoneAndDate(zoneId, date);
        return windows.stream()
                .map(w -> {
                    var cap = capacityRepository.findByDeliveryWindowIdAndZoneId(w.getId(), zoneId)
                              .orElse(null);
                    return WindowResponse.from(w, cap);
                })
                .toList();
    }

    public List<DateAvailabilityResponse> findAvailableDates(Long zoneId) {
        LocalDate today = LocalDate.now();
        // Un solo query trae todo: ventanas + capacidad por zona
        List<WindowZoneCapacity> allCapacities = capacityRepository
            .findAllWithWindowByZoneAndDateFrom(zoneId, today);

        // Agrupar por fecha en Java
        return allCapacities.stream()
            .collect(Collectors.groupingBy(c -> c.getDeliveryWindow().getDate()))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> {
                int totalSlots = entry.getValue().stream()
                        .mapToInt(c -> {
                            int zoneAvailable = c.getAvailableCapacity();
                            int globalAvailable = c.getDeliveryWindow().getAvailableCapacity();
                            return Math.max(0, Math.min(zoneAvailable, globalAvailable));
                        })
                        .sum();
                return new DateAvailabilityResponse(entry.getKey(), totalSlots > 0, totalSlots);
            })
            .toList();
    }
}