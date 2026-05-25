package com.motosport.arriendo.service;

import java.util.List;

import com.motosport.arriendo.dto.ArriendoDto;
import com.motosport.arriendo.dto.ResponseDto;

public interface ArriendoService {

    ArriendoDto addArriendo(ArriendoDto dto);

    ArriendoDto getArriendo(Long id);

    List<ArriendoDto> getAllArriendos();

    ArriendoDto updateArriendo(Long id, ArriendoDto dto);

    ResponseDto deleteArriendo(Long id);
}
