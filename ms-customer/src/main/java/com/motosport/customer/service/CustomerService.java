package com.motosport.customer.service;

import java.util.List;

import com.motosport.customer.dto.CustomerDto;
import com.motosport.customer.dto.ResponseDto;

public interface CustomerService {
    CustomerDto addCustomer(CustomerDto dto);
    CustomerDto getCustomer(Long id);
    List<CustomerDto> getAllCustomer();
    CustomerDto updateCustomer(Long id, CustomerDto dto);
    ResponseDto deleteCustomer(Long id);
}
