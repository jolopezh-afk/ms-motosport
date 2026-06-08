package com.motosport.rent.dto;

public record AuthResponse(
    String token,
    long expiresIn,
    String username
) {
}
