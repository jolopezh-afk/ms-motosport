package com.motosport.arriendo.dto;

public record AuthResponse(
    String token,
    long expiresIn,
    String username
) {
}
