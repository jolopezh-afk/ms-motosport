package com.motosport.arriendo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.motosport.arriendo.dto.ClienteDto;

@Component
public class ClienteClientImpl implements ClienteClient {

    private final RestClient restClient;

    public ClienteClientImpl(
            @Value("${api.cliente.baseUrl}") String baseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public ClienteDto getClienteById(Long id) {

        ResponseEntity<ClienteDto> response = restClient.get()
                .uri("/api/clientes/{id}", id)
                .retrieve()
                .toEntity(ClienteDto.class);

        ClienteDto body = response.getBody();

        HttpStatusCode status = response.getStatusCode();

        if (status.is2xxSuccessful() && body != null) {
            return body;
        }

        throw new RuntimeException(
                "Error al obtener cliente: " + status);
    }
}
