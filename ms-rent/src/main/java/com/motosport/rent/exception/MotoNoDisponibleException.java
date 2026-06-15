package com.motosport.rent.exception;

import org.springframework.http.HttpStatus;

public class BikeNoDisponibleException extends ApiException {

    public BikeNoDisponibleException(String message) {

        super(
                "MOTO_NO_DISPONIBLE",
                message,
                HttpStatus.BAD_REQUEST
        );
    }
}