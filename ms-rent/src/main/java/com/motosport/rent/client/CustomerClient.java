package com.motosport.rent.client;

import com.motosport.rent.dto.CustomerDto;

public interface CustomerClient {
    CustomerDto getCustomerById(Long id);
}