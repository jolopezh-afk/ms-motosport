package com.motosport.bike.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.motosport.bike.dto.BikeDto;
import com.motosport.bike.dto.ResponseDto;
import com.motosport.bike.service.BikeService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/bikes")
@Tag(
    name = "Bikes",
    description = "CRUD de bicicletas del microservicio de motos"
)
public class BikeController {

    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todas las bikes",
        description = "Retorna la lista completa de bicicletas registradas"
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<BikeDto>> getAllBikes() {
        return ResponseEntity.ok(bikeService.getAllBike());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener bike por ID",
        description = "Busca una bicicleta por su identificador"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Bike encontrada"),
        @ApiResponse(responseCode = "404", description = "Bike no encontrada")
    })
    public ResponseEntity<BikeDto> getBikeById(@PathVariable Long id) {
        return ResponseEntity.ok(bikeService.getBike(id));
    }

    @PostMapping
    @Operation(
        summary = "Crear bike",
        description = "Registra una nueva bicicleta en el sistema"
    )
    @ApiResponse(responseCode = "201", description = "Bike creada correctamente")
    public ResponseEntity<BikeDto> createBike(@Valid @RequestBody BikeDto bikeDto) {
        BikeDto createdBike = bikeService.addBike(bikeDto);
        return ResponseEntity.status(201).body(createdBike);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar bike",
        description = "Actualiza una bicicleta existente"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Bike actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Bike no encontrada")
    })
    public ResponseEntity<BikeDto> updateBike(
            @PathVariable Long id,
            @Valid @RequestBody BikeDto bikeDto) {

        return ResponseEntity.ok(bikeService.updateBike(id, bikeDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar bike",
        description = "Elimina una bicicleta por ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Bike eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Bike no encontrada")
    })
    public ResponseEntity<ResponseDto> deleteBike(@PathVariable Long id) {
        return ResponseEntity.ok(bikeService.deleteBike(id));
    }
}