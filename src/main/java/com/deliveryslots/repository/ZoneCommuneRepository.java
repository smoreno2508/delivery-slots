package com.deliveryslots.repository;

import com.deliveryslots.entity.ZoneCommune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;


public interface ZoneCommuneRepository extends JpaRepository<ZoneCommune, Long> {
    
    // JOIN FETCH zc.zone: carga la zona en el mismo query (no lazy)
    @Query("SELECT zc FROM ZoneCommune zc JOIN FETCH zc.zone WHERE LOWER(zc.communeName) = LOWER(:name)")
    Optional<ZoneCommune> findByCommuneNameIgnoreCase(@Param("name") String name);

    @Query("SELECT zc FROM ZoneCommune zc JOIN FETCH zc.zone WHERE zc.zone.id = :zoneId ORDER BY zc.communeName")
    List<ZoneCommune> findByZoneId(@Param("zoneId") Long zoneId);
}
