package com.motosport.rent.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.motosport.rent.dto.BikeDto;

@Component
public class BikeClientImpl implements BikeClient {

    private final RestClient restClient;

    public BikeClientImpl(
            @Value("${api.bike.baseUrl}") String baseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public BikeDto getBikeById(Long id) {

        ResponseEntity<BikeDto> response = restClient.get()
                .uri("/api/bikes/{id}", id)
                .retrieve()
                .toEntity(BikeDto.class);

        BikeDto body = response.getBody();

        HttpStatusCode status = response.getStatusCode();

        if (status.is2xxSuccessful() && body != null) {
            return body;
        }

        throw new RuntimeException(
                "Error al obtener bike: " + status);
    }

    @Override
    public BikeDto updateBike(Long id, BikeDto bikeDto) {

        ResponseEntity<BikeDto> response = restClient.put()
                .uri("/api/bikes/{id}", id)
                .body(bikeDto)
                .retrieve()
                .toEntity(BikeDto.class);

        BikeDto body = response.getBody();

        HttpStatusCode status = response.getStatusCode();

        if (status.is2xxSuccessful() && body != null) {
            return body;
        }

        throw new RuntimeException(
                "Error al actualizar bike: " + status);
    }
}