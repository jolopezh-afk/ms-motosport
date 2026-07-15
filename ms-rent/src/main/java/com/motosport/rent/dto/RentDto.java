package com.motosport.rent.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RentDto(

    Long id,

    @NotNull(message = "El ID de la bike es obligatorio")
    Long bikeId,

    @NotNull(message = "El ID del customer es obligatorio")
    Long customerId,

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio no puede ser pasada")
    LocalDate fechaInicio,

    @NotNull(message = "La fecha de fin es obligatoria")
    LocalDate fechaFin,

    @Size(max = 255, message = "La observación no puede superar los 255 caracteres")
    String observacion

) {}