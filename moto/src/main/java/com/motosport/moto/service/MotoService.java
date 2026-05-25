package com.motosport.moto.service;

import java.util.List;

import com.motosport.moto.dto.MotoDto;
import com.motosport.moto.dto.ResponseDto;

public interface MotoService {
    MotoDto addMoto(MotoDto dto);
    MotoDto getMoto(Long id);
    List<MotoDto> getAllMoto();
    MotoDto updateMoto(Long id, MotoDto dto);
    ResponseDto deleteMoto(Long id);
}
