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
    public void setId(Long id) { this.id = id; }
    public Long getWindowId() { return windowId; }
    public void setWindowId(Long windowId) { this.windowId = windowId; }
    public Long getZoneId() { return zoneId; }
    public void setZoneId(Long zoneId) { this.zoneId = zoneId; }
    public String getCommune() { return commune; }
    public void setCommune(String commune) { this.commune = commune; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}