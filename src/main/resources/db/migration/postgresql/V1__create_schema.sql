-- =====================================================
-- V1: Esquema inicial — Ventanas de despacho
-- Motor: PostgreSQL
-- =====================================================

CREATE TABLE zones (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE zone_communes (
    id BIGSERIAL PRIMARY KEY,
    zone_id BIGINT NOT NULL,
    commune_name VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT fk_commune_zone FOREIGN KEY (zone_id) REFERENCES zones(id)
);

CREATE TABLE delivery_windows (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    capacity_total INT NOT NULL,
    reserved_count INT NOT NULL DEFAULT 0,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT chk_window_capacity CHECK (reserved_count >= 0 AND reserved_count <= capacity_total)
);

CREATE TABLE window_zone_capacity (
    id BIGSERIAL PRIMARY KEY,
    delivery_window_id BIGINT NOT NULL,
    zone_id BIGINT NOT NULL,
    total_capacity INT NOT NULL,
    reserved_count INT NOT NULL DEFAULT 0,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_capacity_window FOREIGN KEY (delivery_window_id) REFERENCES delivery_windows(id),
    CONSTRAINT fk_capacity_zone FOREIGN KEY (zone_id) REFERENCES zones(id),
    CONSTRAINT uk_window_zone UNIQUE (delivery_window_id, zone_id),
    CONSTRAINT chk_zone_capacity CHECK (reserved_count >= 0 AND reserved_count <= total_capacity)
);

CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,
    delivery_window_id BIGINT NOT NULL,
    zone_id BIGINT NOT NULL,
    customer_name VARCHAR(200) NOT NULL,
    customer_address VARCHAR(500) NOT NULL,
    commune VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reservation_window FOREIGN KEY (delivery_window_id) REFERENCES delivery_windows(id),
    CONSTRAINT fk_reservation_zone FOREIGN KEY (zone_id) REFERENCES zones(id)
);

-- Índices de rendimiento
CREATE INDEX idx_windows_date ON delivery_windows(date);
CREATE INDEX idx_capacity_zone ON window_zone_capacity(zone_id);
CREATE INDEX idx_reservations_window ON reservations(delivery_window_id);
CREATE INDEX idx_reservations_zone ON reservations(zone_id);