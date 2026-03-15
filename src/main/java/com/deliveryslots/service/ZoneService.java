package com.deliveryslots.service;

import com.deliveryslots.dto.response.ZoneResponse;
import com.deliveryslots.entity.Zone;
import com.deliveryslots.entity.ZoneCommune;
import com.deliveryslots.exception.ResourceNotFoundException;
import com.deliveryslots.repository.ZoneCommuneRepository;
import com.deliveryslots.repository.ZoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ZoneService {

    private static final Logger log = LoggerFactory.getLogger(ZoneService.class);

    private final ZoneRepository zoneRepository;
    private final ZoneCommuneRepository communeRepository;

    public ZoneService(ZoneRepository zoneRepository, ZoneCommuneRepository communeRepository) {
        this.zoneRepository = zoneRepository;
        this.communeRepository = communeRepository;
    }

    public List<ZoneResponse> findAll() {
        return zoneRepository.findAll().stream()
                .map(ZoneResponse::from)
                .toList();
    }

    public Zone findById(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con id: " + id));
    }

    // Resuelve la zona a partir de una comuna.
    // El JOIN FETCH en el repository garantiza que la Zone viene cargada.
    public Zone resolveByCommune(String commune) {
        ZoneCommune zc = communeRepository.findByCommuneNameIgnoreCase(commune.trim())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No hay cobertura de despacho para la comuna: " + commune));
        log.info("Comuna '{}' resuelta a zona '{}'", commune, zc.getZone().getName());
        return zc.getZone();
    }

    public List<String> findCommunesByZone(Long zoneId) {
        findById(zoneId);
        return communeRepository.findByZoneId(zoneId).stream()
                .map(ZoneCommune::getCommuneName)
                .toList();
    }
}