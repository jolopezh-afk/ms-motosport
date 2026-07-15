package com.motosport.rent.exception;

public class RentNotFoundException extends RuntimeException {

    public RentNotFoundException(String message) {
        super(message);
    }
}