package com.motosport.bff.controller;

import com.motosport.bff.dto.RentDto;
import com.motosport.bff.dto.ResponseDto;
import com.motosport.bff.service.RentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rents")
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    public List<RentDto> getAll() {
        return rentService.getAll();
    }

    @GetMapping("/{id}")
    public RentDto getById(@PathVariable Long id) {
        return rentService.getById(id);
    }

    @PostMapping
    public RentDto create(@Valid @RequestBody RentDto dto) {
        return rentService.create(dto);
    }

    @PutMapping("/{id}")
    public RentDto update(@PathVariable Long id,
                          @Valid @RequestBody RentDto dto) {
        return rentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        return rentService.delete(id);
    }
}