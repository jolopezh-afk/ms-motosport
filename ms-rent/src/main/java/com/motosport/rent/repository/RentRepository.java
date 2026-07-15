package com.motosport.rent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motosport.rent.model.Rent;

public interface RentRepository extends JpaRepository<Rent, Long> {

}
