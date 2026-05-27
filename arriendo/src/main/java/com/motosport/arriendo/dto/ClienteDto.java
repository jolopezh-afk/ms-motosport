package com.motosport.arriendo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record ClienteDto(

    Long id,

    @NotBlank(message = "El rut es obligatorio")
    String rut,

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    String nombre,

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres")
    String apellidos,

    @NotNull(message = "El número de teléfono es obligatorio")
    Integer numeroTelefono,

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ingresar un correo válido")
    String correo,

    @NotBlank(message = "El número de licencia es obligatorio")
    @Size(min = 5, max = 30, message = "El número de licencia debe tener entre 5 y 30 caracteres")
    String nroLicencia,

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Future(message = "La fecha de vencimiento debe ser futura")
    LocalDate fechaVencimiento,

    @NotNull(message = "La fecha de registro es obligatoria")
    @PastOrPresent(message = "La fecha de registro no puede ser futura")
    LocalDate fechaRegistro

) {
}