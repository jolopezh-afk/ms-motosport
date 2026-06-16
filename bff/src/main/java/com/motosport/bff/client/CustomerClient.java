package com.motosport.bff.client;

import com.motosport.bff.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class CustomerClient {

    private final RestClient restClient;
    private final String customerUrl;

    public CustomerClient(RestClient restClient,
                          @Value("${customer.service.url}") String customerUrl) {
        this.restClient = restClient;
        this.customerUrl = customerUrl;
    }

    public List<CustomerDto> findAll() {
        return restClient.get()
                .uri(customerUrl + "/api/customers")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CustomerDto>>() {});
    }

    public CustomerDto findById(Long id) {
        return restClient.get()
                .uri(customerUrl + "/api/customers/{id}", id)
                .retrieve()
                .body(CustomerDto.class);
    }

    public CustomerDto create(CustomerDto dto) {
        return restClient.post()
                .uri(customerUrl + "/api/customers")
                .body(dto)
                .retrieve()
                .body(CustomerDto.class);
    }

    public CustomerDto update(Long id, CustomerDto dto) {
        return restClient.put()
                .uri(customerUrl + "/api/customers/{id}", id)
                .body(dto)
                .retrieve()
                .body(CustomerDto.class);
    }

    public void delete(Long id) {
        restClient.delete()
                .uri(customerUrl + "/api/customers/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}