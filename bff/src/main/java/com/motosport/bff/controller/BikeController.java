package com.motosport.bff.controller;

import com.motosport.bff.client.BikeClient;
import com.motosport.bff.dto.BikeDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    private final BikeClient bikeClient;

    public BikeController(BikeClient bikeClient) {
        this.bikeClient = bikeClient;
    }

    @GetMapping
    public List<BikeDto> getAll() {
        return bikeClient.findAll();
    }

    @GetMapping("/{id}")
    public BikeDto getById(@PathVariable Long id) {
        return bikeClient.findById(id);
    }

    @PostMapping
    public BikeDto create(@RequestBody BikeDto dto) {
        return bikeClient.create(dto);
    }

    @PutMapping("/{id}")
    public BikeDto update(@PathVariable Long id,
                          @RequestBody BikeDto dto) {
        return bikeClient.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bikeClient.delete(id);
    }
}