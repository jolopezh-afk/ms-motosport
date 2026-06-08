package com.motosport.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motosport.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
