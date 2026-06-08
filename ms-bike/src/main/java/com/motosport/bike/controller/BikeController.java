package com.bikesport.bike.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikesport.bike.dto.BikeDto;
import com.bikesport.bike.dto.ResponseDto;
import com.bikesport.bike.service.BikeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bikes")
public class BikeController {

    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping
    public ResponseEntity<List<BikeDto>> getAllBikes() {
        List<BikeDto> bikes = bikeService.getAllBike();
        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeDto> getBikeById(@PathVariable Long id) {
        BikeDto bike = bikeService.getBike(id);
        return ResponseEntity.ok(bike);
    }

    @PostMapping
    public ResponseEntity<BikeDto> createBike(@Valid @RequestBody BikeDto bikeDto) {
        BikeDto createdBike = bikeService.addBike(bikeDto);
        return ResponseEntity.ok(createdBike);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BikeDto> updateBike(
            @PathVariable Long id,
            @Valid @RequestBody BikeDto bikeDto) {

        BikeDto updatedBike = bikeService.updateBike(id, bikeDto);
        return ResponseEntity.ok(updatedBike);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteBike(@PathVariable Long id) {
        ResponseDto response = bikeService.deleteBike(id);
        return ResponseEntity.ok(response);
    }
}