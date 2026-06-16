package com.motosport.bff.client;

import com.motosport.bff.dto.BikeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class BikeClient {

    private final RestClient restClient;
    private final String bikeUrl;

    public BikeClient(RestClient restClient,
                      @Value("${bike.service.url}") String bikeUrl) {
        this.restClient = restClient;
        this.bikeUrl = bikeUrl;
    }

    public List<BikeDto> findAll() {
        return restClient.get()
                .uri(bikeUrl + "/api/bikes")
                .retrieve()
                .body(new ParameterizedTypeReference<List<BikeDto>>() {});
    }

    public BikeDto findById(Long id) {
        return restClient.get()
                .uri(bikeUrl + "/api/bikes/{id}", id)
                .retrieve()
                .body(BikeDto.class);
    }

    public BikeDto create(BikeDto dto) {
        return restClient.post()
                .uri(bikeUrl + "/api/bikes")
                .body(dto)
                .retrieve()
                .body(BikeDto.class);
    }

    public BikeDto update(Long id, BikeDto dto) {
        return restClient.put()
                .uri(bikeUrl + "/api/bikes/{id}", id)
                .body(dto)
                .retrieve()
                .body(BikeDto.class);
    }

    public void delete(Long id) {
        restClient.delete()
                .uri(bikeUrl + "/api/bikes/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}