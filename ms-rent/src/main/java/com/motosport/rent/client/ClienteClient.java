package com.motosport.rent.client;

import com.motosport.rent.dto.ClienteDto;

public interface ClienteClient {
    ClienteDto getClienteById(Long id);
}