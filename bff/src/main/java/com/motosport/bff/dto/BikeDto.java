package com.motosport.bff.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record BikeDto(

    Long id,

    @NotBlank(message = "La marca es obligatoria")
    String marca,

    @NotBlank(message = "El modelo es obligatorio")
    String modelo,

    @NotBlank(message = "La patente es obligatoria")
    @Size(min = 6, max = 6)
    @Pattern(regexp = "^[A-Z0-9]{6}$", message = "La patente debe ser alfanumérica en mayúsculas")
    String patente,

    @NotNull(message = "El valor es obligatorio")
    @Min(value = 0)
    BigDecimal valor,

    @NotNull(message = "El año es obligatorio")
    @Min(1900)
    @Max(2100)
    Integer anio,

    @NotBlank(message = "El color es obligatorio")
    String color,

    @NotNull(message = "El kilometraje es obligatorio")
    @Min(0)
    Integer kilometraje,

    @NotNull(message = "La disponibilidad es obligatoria")
    Boolean disponibilidad

) {}