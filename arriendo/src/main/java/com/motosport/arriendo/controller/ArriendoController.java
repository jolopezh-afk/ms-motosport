package com.motosport.arriendo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.motosport.arriendo.dto.ArriendoDto;
import com.motosport.arriendo.dto.ResponseDto;
import com.motosport.arriendo.service.ArriendoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/arriendos")
public class ArriendoController {
    
    private final ArriendoService arriendoService;

    public ArriendoController(ArriendoService arriendoService) {
        this.arriendoService = arriendoService;
    }

    @GetMapping
    public ResponseEntity<List<ArriendoDto>> getAllArriendos() {

        List<ArriendoDto> arriendos =
                arriendoService.getAllArriendos();

        return ResponseEntity.ok(arriendos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArriendoDto> getArriendoById(
            @PathVariable Long id) {

        ArriendoDto arriendo =
                arriendoService.getArriendo(id);

        return ResponseEntity.ok(arriendo);
    }

    @PostMapping
    public ResponseEntity<ArriendoDto> createArriendo(
            @Valid @RequestBody ArriendoDto arriendoDto) {

        ArriendoDto createdArriendo =
                arriendoService.addArriendo(arriendoDto);

        return ResponseEntity.ok(createdArriendo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArriendoDto> updateArriendo(
            @PathVariable Long id,
            @Valid @RequestBody ArriendoDto arriendoDto) {

        ArriendoDto updatedArriendo =
                arriendoService.updateArriendo(id, arriendoDto);

        return ResponseEntity.ok(updatedArriendo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteArriendo(
            @PathVariable Long id) {

        ResponseDto response =
                arriendoService.deleteArriendo(id);

        return ResponseEntity.ok(response);
    }
}