package com.motosport.rent.exception;
import org.springframework.http.HttpStatus;

public class AuthorizationException extends ApiException {
    public AuthorizationException(String message) {
        super("AUTHORIZATION_ERROR", message, HttpStatus.FORBIDDEN);
    }
}
