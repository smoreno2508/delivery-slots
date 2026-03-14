package com.deliveryslots.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "zone_communes")
public class ZoneCommune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @Column(name = "commune_name", nullable = false, unique = true)
    private String communeName;

    public ZoneCommune() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Zone getZone() { return zone; }
    public void setZone(Zone zone) { this.zone = zone; }
    public String getCommuneName() { return communeName; }
    public void setCommuneName(String communeName) { this.communeName = communeName; }

}