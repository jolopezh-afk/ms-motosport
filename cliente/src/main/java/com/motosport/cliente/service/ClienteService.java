package com.motosport.cliente.service;

import java.util.List;

import com.motosport.cliente.dto.ClienteDto;
import com.motosport.cliente.dto.ResponseDto;

public interface ClienteService {
    ClienteDto addCliente(ClienteDto dto);
    ClienteDto getCliente(Long id);
    List<ClienteDto> getAllCliente();
    ClienteDto updateCliente(Long id, ClienteDto dto);
    ResponseDto deleteCliente(Long id);
}
