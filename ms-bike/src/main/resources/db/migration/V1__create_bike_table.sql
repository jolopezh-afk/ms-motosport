CREATE TABLE bike (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    patente VARCHAR(6) NOT NULL UNIQUE,
    valor INT NOT NULL,
    annio INT NOT NULL,
    color VARCHAR(30) NOT NULL,
    kilometraje INT NOT NULL,
    disponibilidad BOOLEAN NOT NULL
);