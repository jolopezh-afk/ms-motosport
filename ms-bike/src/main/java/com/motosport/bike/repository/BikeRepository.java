package com.motosport.bike.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motosport.bike.model.Bike;

public interface BikeRepository extends JpaRepository<Bike, Long>{

}
