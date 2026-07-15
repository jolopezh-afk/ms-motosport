CREATE TABLE rent (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bike_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    observacion VARCHAR(255)
);