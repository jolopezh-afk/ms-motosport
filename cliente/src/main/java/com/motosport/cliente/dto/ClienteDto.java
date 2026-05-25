package com.motosport.cliente.dto;

import java.time.LocalDate;

public record ClienteDto(
    Long id,
    String rut,
    String nombre,
    String apellidos,
    Integer numeroTelefono,
    String correo,
    String nroLicencia,
    LocalDate fechaVencimiento,
    LocalDate fechaRegistro
) {
}