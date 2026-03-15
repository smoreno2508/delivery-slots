package com.deliveryslots.dto.response;

import java.time.LocalDate;

public class DateAvailabilityResponse {

    private LocalDate date;
    private boolean available;
    private int slotsAvailable;

    public DateAvailabilityResponse(LocalDate date, boolean available, int slotsAvailable) {
        this.date = date;
        this.available = available;
        this.slotsAvailable = slotsAvailable;
    }

    public LocalDate getDate() { return date; }
    public boolean isAvailable() { return available; }
    public int getSlotsAvailable() { return slotsAvailable; }
}