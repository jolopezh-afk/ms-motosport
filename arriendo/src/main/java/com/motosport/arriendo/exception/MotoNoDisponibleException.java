package com.motosport.arriendo.exception;

import org.springframework.http.HttpStatus;

public class MotoNoDisponibleException extends ApiException {

    public MotoNoDisponibleException(String message) {

        super(
                "MOTO_NO_DISPONIBLE",
                message,
                HttpStatus.BAD_REQUEST
        );
    }
}