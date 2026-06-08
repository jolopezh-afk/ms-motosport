package com.bikesport.bike.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikesport.bike.model.Bike;

public interface BikeRepository extends JpaRepository<Bike, Long>{

}
