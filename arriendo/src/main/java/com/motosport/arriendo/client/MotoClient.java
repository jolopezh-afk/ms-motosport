package com.motosport.arriendo.client;

import com.motosport.arriendo.dto.MotoDto;

public interface MotoClient {

    MotoDto getMotoById(Long id);
    MotoDto updateMoto(Long id, MotoDto motoDto);
}