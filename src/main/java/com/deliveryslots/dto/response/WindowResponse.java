package com.deliveryslots.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.deliveryslots.entity.DeliveryWindow;
import com.deliveryslots.entity.WindowZoneCapacity;

public class WindowResponse {
    
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal cost;
    private int availableCapacity;
    private boolean exhausted;

    public static WindowResponse from(DeliveryWindow window, WindowZoneCapacity zoneCapacity) {
        WindowResponse response = new WindowResponse();
        response.id = window.getId();
        response.date = window.getDate();
        response.startTime = window.getStartTime();
        response.endTime = window.getEndTime();
        response.cost = window.getCost();

        int zoneAvailable = zoneCapacity != null ? zoneCapacity.getAvailableCapacity() : 0;
        int globalAvailable = window.getAvailableCapacity();
        // El mínimo entre ambos: si la zona tiene 2 pero la global solo 1,
        // solo se puede reservar 1.
        response.availableCapacity = Math.max(0, Math.min(zoneAvailable, globalAvailable));
        response.exhausted = response.availableCapacity <= 0;
        return response;
    }

    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public BigDecimal getCost() { return cost; }
    public int getAvailableCapacity() { return availableCapacity; }
    public boolean isExhausted() { return exhausted; }
}
