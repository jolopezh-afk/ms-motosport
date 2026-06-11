package com.motosport.rent.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.motosport.rent.dto.RentDto;
import com.motosport.rent.dto.ResponseDto;
import com.motosport.rent.service.RentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rents")
public class RentController {
    
    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    public ResponseEntity<List<RentDto>> getAllRents() {

        List<RentDto> rents =
                rentService.getAllRents();

        return ResponseEntity.ok(rents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentDto> getRentById(
            @PathVariable Long id) {

        RentDto rent =
                rentService.getRent(id);

        return ResponseEntity.ok(rent);
    }

    @PostMapping
    public ResponseEntity<RentDto> createRent(
            @Valid @RequestBody RentDto rentDto) {

        RentDto createdRent =
                rentService.addRent(rentDto);

        return ResponseEntity.ok(createdRent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentDto> updateRent(
            @PathVariable Long id,
            @Valid @RequestBody RentDto rentDto) {

        RentDto updatedRent =
                rentService.updateRent(id, rentDto);

        return ResponseEntity.ok(updatedRent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteRent(
            @PathVariable Long id) {

        ResponseDto response =
                rentService.deleteRent(id);

        return ResponseEntity.ok(response);
    }
}