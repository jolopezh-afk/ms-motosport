package com.motosport.moto.controller;

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

import com.motosport.moto.dto.MotoDto;
import com.motosport.moto.dto.ResponseDto;
import com.motosport.moto.service.MotoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    private final MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping
    public ResponseEntity<List<MotoDto>> getAllMotos() {
        List<MotoDto> motos = motoService.getAllMoto();
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoDto> getMotoById(@PathVariable Long id) {
        MotoDto moto = motoService.getMoto(id);
        return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<MotoDto> createMoto(@Valid @RequestBody MotoDto motoDto) {
        MotoDto createdMoto = motoService.addMoto(motoDto);
        return ResponseEntity.ok(createdMoto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoDto> updateMoto(
            @PathVariable Long id,
            @Valid @RequestBody MotoDto motoDto) {

        MotoDto updatedMoto = motoService.updateMoto(id, motoDto);
        return ResponseEntity.ok(updatedMoto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteMoto(@PathVariable Long id) {
        ResponseDto response = motoService.deleteMoto(id);
        return ResponseEntity.ok(response);
    }
}