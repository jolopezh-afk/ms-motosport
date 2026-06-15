package com.motosport.rent.client;

import com.motosport.rent.dto.BikeDto;

public interface BikeClient {

    BikeDto getBikeById(Long id);
    BikeDto updateBike(Long id, BikeDto bikeDto);
}