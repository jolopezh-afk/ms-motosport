CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    numero_telefono INT NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    nro_licencia VARCHAR(50) NOT NULL UNIQUE,
    fecha_vencimiento DATE NOT NULL,
    fecha_registro DATE NOT NULL
);