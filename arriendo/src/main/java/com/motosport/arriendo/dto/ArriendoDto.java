package com.motosport.arriendo.dto;

import java.time.LocalDate;

public record ArriendoDto(
    Long id,
    Long motoId,
    Long clienteId,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    String observacion
) {

}
