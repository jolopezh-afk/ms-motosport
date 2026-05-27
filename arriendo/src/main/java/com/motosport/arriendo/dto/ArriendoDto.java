package com.motosport.arriendo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ArriendoDto(

    Long id,

    @NotNull(message = "El ID de la moto es obligatorio")
    Long motoId,

    @NotNull(message = "El ID del cliente es obligatorio")
    Long clienteId,

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio no puede ser pasada")
    LocalDate fechaInicio,

    @NotNull(message = "La fecha de fin es obligatoria")
    LocalDate fechaFin,

    @Size(max = 255, message = "La observación no puede superar los 255 caracteres")
    String observacion

) {}