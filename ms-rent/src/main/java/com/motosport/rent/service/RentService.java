package com.motosport.rent.service;

import java.util.List;

import com.motosport.rent.dto.RentDto;
import com.motosport.rent.dto.ResponseDto;

public interface RentService {

    RentDto addRent(RentDto dto);

    RentDto getRent(Long id);

    List<RentDto> getAllRents();

    RentDto updateRent(Long id, RentDto dto);

    ResponseDto deleteRent(Long id);
}
