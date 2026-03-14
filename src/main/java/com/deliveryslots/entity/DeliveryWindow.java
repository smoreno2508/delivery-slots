package com.deliveryslots.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "delivery_windows")
public class DeliveryWindow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name="start_time", nullable = false)
    private LocalTime startTime;

    @Column(name="end_time", nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(name = "capacity_total", nullable = false)
    private int capacityTotal;

    @Column(name = "reserved_count", nullable = false)
    private int reservedCount = 0;

    @Version
    private Long version;

    public DeliveryWindow() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
    public int getCapacityTotal() { return capacityTotal; }
    public void setCapacityTotal(int capacityTotal) { this.capacityTotal = capacityTotal; }
    public int getReservedCount() { return reservedCount; }
    public void setReservedCount(int reservedCount) { this.reservedCount = reservedCount; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public int getAvailableCapacity() {
        return capacityTotal - reservedCount;
    }   
}