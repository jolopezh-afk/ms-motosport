package com.motosport.arriendo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.motosport.arriendo.dto.MotoDto;

@Component
public class MotoClientImpl implements MotoClient {

    private final RestClient restClient;

    public MotoClientImpl(
            @Value("${api.moto.baseUrl}") String baseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public MotoDto getMotoById(Long id) {

        ResponseEntity<MotoDto> response = restClient.get()
                .uri("/api/motos/{id}", id)
                .retrieve()
                .toEntity(MotoDto.class);

        MotoDto body = response.getBody();

        HttpStatusCode status = response.getStatusCode();

        if (status.is2xxSuccessful() && body != null) {
            return body;
        }

        throw new RuntimeException(
                "Error al obtener moto: " + status);
    }

    @Override
    public MotoDto updateMoto(Long id, MotoDto motoDto) {

        ResponseEntity<MotoDto> response = restClient.put()
                .uri("/api/motos/{id}", id)
                .body(motoDto)
                .retrieve()
                .toEntity(MotoDto.class);

        MotoDto body = response.getBody();

        HttpStatusCode status = response.getStatusCode();

        if (status.is2xxSuccessful() && body != null) {
            return body;
        }

        throw new RuntimeException(
                "Error al actualizar moto: " + status);
    }
}