package com.motosport.rent.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.motosport.rent.dto.CustomerDto;

@Component
public class CustomerClientImpl implements CustomerClient {

    private final RestClient restClient;

    public CustomerClientImpl(
            @Value("${api.customer.baseUrl}") String baseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public CustomerDto getCustomerById(Long id) {

        ResponseEntity<CustomerDto> response = restClient.get()
                .uri("/api/customers/{id}", id)
                .retrieve()
                .toEntity(CustomerDto.class);

        CustomerDto body = response.getBody();

        HttpStatusCode status = response.getStatusCode();

        if (status.is2xxSuccessful() && body != null) {
            return body;
        }

        throw new RuntimeException(
                "Error al obtener customer: " + status);
    }
}
