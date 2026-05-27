package com.motosport.cliente.controller;

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

import com.motosport.cliente.dto.ClienteDto;
import com.motosport.cliente.dto.ResponseDto;
import com.motosport.cliente.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> getAllCliente() {

    List<ClienteDto> clientes = clienteService.getAllCliente();

    return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> getClienteById(@PathVariable Long id) {

        ClienteDto cliente = clienteService.getCliente(id);

        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<ClienteDto> createCliente(
            @Valid @RequestBody ClienteDto clienteDto) {

        ClienteDto createdCliente = clienteService.addCliente(clienteDto);

        return ResponseEntity.ok(createdCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> updateCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDto clienteDto) {

        ClienteDto updatedCliente =
                clienteService.updateCliente(id, clienteDto);

        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteCliente(
            @PathVariable Long id) {

        ResponseDto response = clienteService.deleteCliente(id);

        return ResponseEntity.ok(response);
    }
}