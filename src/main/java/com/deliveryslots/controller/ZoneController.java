package com.deliveryslots.controller;

import com.deliveryslots.dto.response.ZoneResponse;
import com.deliveryslots.service.ZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping
    public ResponseEntity<List<ZoneResponse>> findAll() {
        return ResponseEntity.ok(zoneService.findAll());
    }

    @GetMapping("/resolve")
    public ResponseEntity<ZoneResponse> resolveByCommune(@RequestParam String commune) {
        return ResponseEntity.ok(ZoneResponse.from(zoneService.resolveByCommune(commune)));
    }

    @GetMapping("/{id}/communes")
    public ResponseEntity<List<String>> getCommunesByZone(@PathVariable Long id) {
        return ResponseEntity.ok(zoneService.findCommunesByZone(id));
    }
}