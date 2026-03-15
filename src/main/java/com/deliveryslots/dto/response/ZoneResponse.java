package com.deliveryslots.dto.response;

import com.deliveryslots.entity.Zone;

public class ZoneResponse {
    
    private Long id;
    private String name;
    private String description;

    public static ZoneResponse from(Zone zone) {
        ZoneResponse response = new ZoneResponse();
        response.id = zone.getId();
        response.name = zone.getName();
        response.description = zone.getDescription();
        return response;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

}
