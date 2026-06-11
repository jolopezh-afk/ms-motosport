package com.motosport.bike.service;

import java.util.List;

import com.motosport.bike.dto.BikeDto;
import com.motosport.bike.dto.ResponseDto;

public interface BikeService {
    BikeDto addBike(BikeDto dto);
    BikeDto getBike(Long id);
    List<BikeDto> getAllBike();
    BikeDto updateBike(Long id, BikeDto dto);
    ResponseDto deleteBike(Long id);
}
