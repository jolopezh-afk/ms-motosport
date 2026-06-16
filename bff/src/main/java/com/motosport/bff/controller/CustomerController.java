package com.motosport.bff.controller;

import com.motosport.bff.client.CustomerClient;
import com.motosport.bff.dto.CustomerDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerClient customerClient;

    public CustomerController(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @GetMapping
    public List<CustomerDto> getAll() {
        return customerClient.findAll();
    }

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable Long id) {
        return customerClient.findById(id);
    }

    @PostMapping
    public CustomerDto create(@Valid @RequestBody CustomerDto dto) {
        return customerClient.create(dto);
    }

    @PutMapping("/{id}")
    public CustomerDto update(@PathVariable Long id,
                              @Valid @RequestBody CustomerDto dto) {
        return customerClient.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        customerClient.delete(id);
    }
}