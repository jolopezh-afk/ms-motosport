package com.motosport.bff.controller;

import com.motosport.bff.dto.HealthResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@Tag(
    name = "Health",
    description = "Endpoints de monitoreo del estado del BFF"
)
public class HealthController {

    @GetMapping("/health")
    @Operation(
        summary = "Health check",
        description = "Verifica si el servicio está activo y devuelve información básica del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio operativo"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<HealthResponseDto> health() {

        return ResponseEntity.ok(
            new HealthResponseDto(
                "UP",
                "motosport-bff",
                Instant.now().toString()
            )
        );
    }
}