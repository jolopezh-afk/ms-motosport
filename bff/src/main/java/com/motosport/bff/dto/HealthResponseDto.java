package com.motosport.bff.dto;

public record HealthResponseDto(
        String status,
        String service,
        String timestamp
) {
}