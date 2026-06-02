CREATE TABLE arriendo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    moto_id BIGINT NOT NULL,
    cliente_id BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    observacion VARCHAR(255)
);