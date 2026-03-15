package com.deliveryslots.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReservationRequest {

    @NotNull(message = "windowId es obligatiorio")
    private Long windowId;

    @NotNull(message = "zoneId es obligatorio")
    private Long zoneId;

    @NotBlank(message = "commune es obligatorio")
    private String commune;

    @NotBlank(message = "customerName es obligatorio")
    private String customerName;

    @NotBlank(message = "customerAddress es obligatorio")
    private String customerAddress;

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
}
