package com.motosport.arriendo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MotoDto(

    Long id,

    @NotBlank(message = "La marca es obligatoria")
    String marca,

    @NotBlank(message = "El modelo es obligatorio")
    String modelo,

    @NotBlank(message = "La patente es obligatoria")
    @Size(min = 6, max = 6, message = "La patente debe tener 6 caracteres")
    String patente,

    @NotNull(message = "El valor es obligatorio")
    @Min(value = 0, message = "El valor no puede ser negativo")
    Integer valor,

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "Año inválido")
    @Max(value = 2100, message = "Año inválido")
    Integer annio,

    @NotBlank(message = "El color es obligatorio")
    String color,

    @NotNull(message = "El kilometraje es obligatorio")
    @Min(value = 0, message = "El kilometraje no puede ser negativo")
    Integer kilometraje,

    @NotNull(message = "La disponibilidad es obligatoria")
    Boolean disponibilidad

) {
}