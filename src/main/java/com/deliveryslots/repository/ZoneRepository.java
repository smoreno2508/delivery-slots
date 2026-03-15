package com.deliveryslots.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.deliveryslots.entity.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    
}
