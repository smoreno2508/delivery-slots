-- =====================================================
-- V2: Datos semilla
-- Motor: PostgreSQL
-- =====================================================

-- Zonas geográficas
INSERT INTO zones (name, description) VALUES ('Zona Norte', 'Sector norte de la ciudad');
INSERT INTO zones (name, description) VALUES ('Zona Centro', 'Sector centro de la ciudad');
INSERT INTO zones (name, description) VALUES ('Zona Sur', 'Sector sur de la ciudad');

-- Zona Norte (id=1)
INSERT INTO zone_communes (zone_id, commune_name) VALUES (1, 'Huechuraba');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (1, 'Conchali');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (1, 'Independencia');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (1, 'Recoleta');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (1, 'Quilicura');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (1, 'Vitacura');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (1, 'Las Condes');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (1, 'Lo Barnechea');

-- Zona Centro (id=2)
INSERT INTO zone_communes (zone_id, commune_name) VALUES (2, 'Santiago');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (2, 'Providencia');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (2, 'Nunoa');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (2, 'Macul');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (2, 'San Joaquin');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (2, 'Estacion Central');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (2, 'Quinta Normal');

-- Zona Sur (id=3)
INSERT INTO zone_communes (zone_id, commune_name) VALUES (3, 'La Florida');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (3, 'Puente Alto');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (3, 'San Bernardo');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (3, 'Maipu');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (3, 'La Cisterna');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (3, 'El Bosque');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (3, 'La Pintana');
INSERT INTO zone_communes (zone_id, commune_name) VALUES (3, 'San Ramon');

-- =====================================================
-- Ventanas de despacho: 15 al 30 de marzo 2026
-- 3 bloques por día:
--   Mañana  (09-11): $2.990, capacidad 5
--   Mediodía (11-13): $1.990, capacidad 5
--   Tarde   (14-16): $2.490, capacidad 4
-- =====================================================

INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-15', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-15', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-15', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-16', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-16', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-16', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-17', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-17', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-17', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-18', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-18', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-18', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-19', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-19', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-19', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-20', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-20', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-20', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-21', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-21', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-21', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-22', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-22', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-22', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-23', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-23', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-23', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-24', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-24', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-24', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-25', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-25', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-25', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-26', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-26', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-26', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-27', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-27', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-27', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-28', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-28', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-28', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-29', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-29', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-29', '14:00', '16:00', 2490, 4);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-30', '09:00', '11:00', 2990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-30', '11:00', '13:00', 1990, 5);
INSERT INTO delivery_windows (date, start_time, end_time, cost, capacity_total) VALUES ('2026-03-30', '14:00', '16:00', 2490, 4);

-- =====================================================
-- Capacidad por zona en cada ventana
-- Mañana/Mediodía (global=5): Norte=2, Centro=2, Sur=1
-- Tarde           (global=4): Norte=2, Centro=1, Sur=1
-- =====================================================

INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (1, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (1, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (1, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (2, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (2, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (2, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (3, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (3, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (3, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (4, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (4, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (4, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (5, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (5, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (5, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (6, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (6, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (6, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (7, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (7, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (7, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (8, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (8, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (8, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (9, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (9, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (9, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (10, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (10, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (10, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (11, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (11, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (11, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (12, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (12, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (12, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (13, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (13, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (13, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (14, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (14, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (14, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (15, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (15, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (15, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (16, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (16, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (16, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (17, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (17, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (17, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (18, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (18, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (18, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (19, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (19, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (19, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (20, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (20, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (20, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (21, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (21, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (21, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (22, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (22, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (22, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (23, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (23, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (23, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (24, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (24, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (24, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (25, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (25, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (25, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (26, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (26, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (26, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (27, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (27, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (27, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (28, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (28, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (28, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (29, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (29, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (29, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (30, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (30, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (30, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (31, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (31, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (31, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (32, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (32, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (32, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (33, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (33, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (33, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (34, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (34, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (34, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (35, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (35, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (35, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (36, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (36, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (36, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (37, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (37, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (37, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (38, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (38, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (38, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (39, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (39, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (39, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (40, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (40, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (40, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (41, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (41, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (41, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (42, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (42, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (42, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (43, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (43, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (43, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (44, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (44, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (44, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (45, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (45, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (45, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (46, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (46, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (46, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (47, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (47, 2, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (47, 3, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (48, 1, 2);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (48, 2, 1);
INSERT INTO window_zone_capacity (delivery_window_id, zone_id, total_capacity) VALUES (48, 3, 1);