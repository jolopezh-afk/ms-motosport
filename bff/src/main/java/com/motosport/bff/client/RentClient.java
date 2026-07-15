package com.motosport.bff.client;

import com.motosport.bff.dto.RentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class RentClient {

    private final RestClient restClient;
    private final String rentUrl;

    public RentClient(RestClient restClient,
                      @Value("${rent.service.url}") String rentUrl) {
        this.restClient = restClient;
        this.rentUrl = rentUrl;
    }

    public List<RentDto> findAll() {
        return restClient.get()
                .uri(rentUrl + "/api/rents")
                .retrieve()
                .body(new ParameterizedTypeReference<List<RentDto>>() {});
    }

    public RentDto findById(Long id) {
        return restClient.get()
                .uri(rentUrl + "/api/rents/{id}", id)
                .retrieve()
                .body(RentDto.class);
    }

    public RentDto create(RentDto dto) {
        return restClient.post()
                .uri(rentUrl + "/api/rents")
                .body(dto)
                .retrieve()
                .body(RentDto.class);
    }

    public RentDto update(Long id, RentDto dto) {
        return restClient.put()
                .uri(rentUrl + "/api/rents/{id}", id)
                .body(dto)
                .retrieve()
                .body(RentDto.class);
    }

    public void delete(Long id) {
        restClient.delete()
                .uri(rentUrl + "/api/rents/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}