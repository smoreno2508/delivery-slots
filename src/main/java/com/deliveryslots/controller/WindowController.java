package com.deliveryslots.controller;

import com.deliveryslots.dto.response.DateAvailabilityResponse;
import com.deliveryslots.dto.response.WindowResponse;
import com.deliveryslots.service.WindowService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/windows")
public class WindowController {

    private final WindowService windowService;

    public WindowController(WindowService windowService) {
        this.windowService = windowService;
    }

    @GetMapping
    public ResponseEntity<List<WindowResponse>> findByZoneAndDate(
            @RequestParam Long zoneId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(windowService.findByZoneAndDate(zoneId, date));
    }

    @GetMapping("/dates")
    public ResponseEntity<List<DateAvailabilityResponse>> findAvailableDates(
            @RequestParam Long zoneId) {
        return ResponseEntity.ok(windowService.findAvailableDates(zoneId));
    }
}