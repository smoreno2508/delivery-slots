package com.deliveryslots.dto.response;

import com.deliveryslots.entity.Reservation;
import com.deliveryslots.enums.ReservationStatus;

import java.time.LocalDateTime;

public class ReservationResponse {

    private Long id;
    private Long windowId;
    private Long zoneId;
    private String commune;
    private String customerName;
    private String customerAddress;
    private ReservationStatus status;
    private LocalDateTime createdAt;

    public static ReservationResponse from(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.id = reservation.getId();
        response.windowId = reservation.getDeliveryWindow().getId();
        response.zoneId = reservation.getZone().getId();
        response.commune = reservation.getCommune();
        response.customerName = reservation.getCustomerName();
        response.customerAddress = reservation.getCustomerAddress();
        response.status = reservation.getStatus();
        response.createdAt = reservation.getCreatedAt();
        return response;
    }

    public Long getId() { return id; }
    public Long getWindowId() { return windowId; }
    public Long getZoneId() { return zoneId; }
    public String getCommune() { return commune; }
    public String getCustomerName() { return customerName; }
    public String getCustomerAddress() { return customerAddress; }
    public ReservationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}