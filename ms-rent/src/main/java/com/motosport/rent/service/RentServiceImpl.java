package com.motosport.rent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motosport.rent.client.CustomerClient;
import com.motosport.rent.client.BikeClient;
import com.motosport.rent.dto.RentDto;
import com.motosport.rent.dto.CustomerDto;
import com.motosport.rent.dto.BikeDto;
import com.motosport.rent.dto.ResponseDto;
import com.motosport.rent.exception.BikeNoDisponibleException;
import com.motosport.rent.model.Rent;
import com.motosport.rent.repository.RentRepository;

@Service
public class RentServiceImpl implements RentService {
    
    private final RentRepository repository;
    private final CustomerClient customerClient;
    private final BikeClient bikeClient;

    public RentServiceImpl(
            RentRepository repository,
            CustomerClient customerClient,
            BikeClient bikeClient) {

        this.repository = repository;
        this.customerClient = customerClient;
        this.bikeClient = bikeClient;
    }

    @Override
    public RentDto addRent(RentDto dto) {
        customerClient.getCustomerById(dto.customerId());

        BikeDto bike =
                bikeClient.getBikeById(dto.bikeId());

        if (!bike.disponibilidad()) {
            throw new BikeNoDisponibleException("Bike no disponible");
        }

        if (dto.fechaFin().isBefore(dto.fechaInicio())) {
            throw new RuntimeException(
                    "La fecha fin no puede ser menor a la fecha inicio");
        }

        BikeDto bikeActualizada = new BikeDto(
                bike.id(),
                bike.marca(),
                bike.modelo(),
                bike.patente(),
                bike.valor(),
                bike.annio(),
                bike.color(),
                bike.kilometraje(),
                false
        );

        bikeClient.updateBike(
                bike.id(),
                bikeActualizada
        );
        Rent rent =
                repository.save(dtoToModel(dto));

        return modelToDto(rent);
    }

    @Override
    public RentDto getRent(Long id) {
        Rent rent = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rent no encontrado"));
        return modelToDto(rent);
    }

    @Override
    public List<RentDto> getAllRents() {
        List<Rent> rents = repository.findAll();
        return rents.stream()
                .map(this::modelToDto)
                .toList();
    }

    @Override
    public RentDto updateRent(Long id, RentDto dto) {
        Rent rent = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rent no encontrado"));

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
                .orElseThrow(() -> new RuntimeException("Rent no encontrado"));

        BikeDto bike = bikeClient.getBikeById(rent.getBikeId());

        BikeDto bikeActualizada = new BikeDto(
                bike.id(),
                bike.marca(),
                bike.modelo(),
                bike.patente(),
                bike.valor(),
                bike.annio(),
                bike.color(),
                bike.kilometraje(),
                true
        );

        bikeClient.updateBike(bike.id(), bikeActualizada);

        repository.deleteById(id);

        return new ResponseDto("Rent eliminado correctamente");
    }

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
