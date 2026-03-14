package com.deliveryslots.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "window_zone_capacity", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"delivery_window_id", "zone_id"})})
public class WindowZoneCapacity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_window_id", nullable = false)
    private DeliveryWindow deliveryWindow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @Column(name = "total_capacity", nullable = false)
    private int totalCapacity;

    @Column(name = "reserved_count", nullable = false)
    private int reservedCount = 0;

    @Version
    private Long version;

    public WindowZoneCapacity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public DeliveryWindow getDeliveryWindow() { return deliveryWindow; }
    public void setDeliveryWindow(DeliveryWindow deliveryWindow) { this.deliveryWindow = deliveryWindow; }
    public Zone getZone() { return zone; }
    public void setZone(Zone zone) { this.zone = zone; }
    public int getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(int totalCapacity) { this.totalCapacity = totalCapacity; }
    public int getReservedCount() { return reservedCount; }
    public void setReservedCount(int reservedCount) { this.reservedCount = reservedCount; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public int getAvailableCapacity() {
        return totalCapacity - reservedCount;
    }

}