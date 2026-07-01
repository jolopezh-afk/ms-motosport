package com.motosport.bff.dto;

public record AuthResponse(String accessToken, String tokenType, long expiresIn) {
}
