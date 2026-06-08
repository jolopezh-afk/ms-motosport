package com.motosport.rent.client;

import com.motosport.rent.dto.MotoDto;

public interface MotoClient {

    MotoDto getMotoById(Long id);
    MotoDto updateMoto(Long id, MotoDto motoDto);
}