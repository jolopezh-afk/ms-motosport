package com.motosport.arriendo.client;

import com.motosport.arriendo.dto.ClienteDto;

public interface ClienteClient {
    ClienteDto getClienteById(Long id);
}