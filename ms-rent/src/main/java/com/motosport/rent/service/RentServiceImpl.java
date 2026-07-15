package com.motosport.rent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motosport.rent.dto.RentDto;
import com.motosport.rent.dto.ResponseDto;
import com.motosport.rent.exception.RentNotFoundException;
import com.motosport.rent.model.Rent;
import com.motosport.rent.repository.RentRepository;

@Service
public class RentServiceImpl implements RentService {

    private final RentRepository repository;

    public RentServiceImpl(RentRepository repository) {
        this.repository = repository;
    }

    @Override
    public RentDto addRent(RentDto dto) {

        validarFechas(dto);

        Rent rent = repository.save(dtoToModel(dto));

        return modelToDto(rent);
    }

    @Override
    public RentDto getRent(Long id) {

        Rent rent = repository.findById(id)
                .orElseThrow(() -> new RentNotFoundException("Rent no encontrado"));

        return modelToDto(rent);
    }

    @Override
    public List<RentDto> getAllRents() {

        return repository.findAll()
                .stream()
                .map(this::modelToDto)
                .toList();
    }

    @Override
    public RentDto updateRent(Long id, RentDto dto) {

        Rent rent = repository.findById(id)
                .orElseThrow(() -> new RentNotFoundException("Rent no encontrado"));

        validarFechas(dto);

        rent.setBikeId(dto.bikeId());
        rent.setCustomerId(dto.customerId());
        rent.setFechaInicio(dto.fechaInicio());
        rent.setFechaFin(dto.fechaFin());
        rent.setObservacion(dto.observacion());

        repository.save(rent);

        return modelToDto(rent);
    }

    @Override
    public ResponseDto deleteRent(Long id) {

        Rent rent = repository.findById(id)
                .orElseThrow(() -> new RentNotFoundException("Rent no encontrado"));

        repository.delete(rent);

        return new ResponseDto("Rent eliminado correctamente");
    }

    // -------------------------
    // VALIDACIÓN DE NEGOCIO
    // -------------------------

    private void validarFechas(RentDto dto) {

        if (dto.fechaFin().isBefore(dto.fechaInicio())) {
            throw new IllegalArgumentException(
                    "La fecha fin no puede ser menor a la fecha inicio"
            );
        }
    }

    // -------------------------
    // MAPPER
    // -------------------------

    private RentDto modelToDto(Rent model) {

        return new RentDto(
                model.getId(),
                model.getBikeId(),
                model.getCustomerId(),
                model.getFechaInicio(),
                model.getFechaFin(),
                model.getObservacion()
        );
    }

    private Rent dtoToModel(RentDto dto) {

        Rent rent = new Rent();
        rent.setId(dto.id());
        rent.setBikeId(dto.bikeId());
        rent.setCustomerId(dto.customerId());
        rent.setFechaInicio(dto.fechaInicio());
        rent.setFechaFin(dto.fechaFin());
        rent.setObservacion(dto.observacion());

        return rent;
    }
}