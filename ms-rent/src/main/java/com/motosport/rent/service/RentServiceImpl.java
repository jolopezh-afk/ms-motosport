package com.motosport.rent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motosport.rent.client.ClienteClient;
import com.motosport.rent.client.MotoClient;
import com.motosport.rent.dto.RentDto;
import com.motosport.rent.dto.ClienteDto;
import com.motosport.rent.dto.MotoDto;
import com.motosport.rent.dto.ResponseDto;
import com.motosport.rent.exception.MotoNoDisponibleException;
import com.motosport.rent.model.Rent;
import com.motosport.rent.repository.RentRepository;

@Service
public class RentServiceImpl implements RentService {
    
    private final RentRepository repository;
    private final ClienteClient clienteClient;
    private final MotoClient motoClient;

    public RentServiceImpl(
            RentRepository repository,
            ClienteClient clienteClient,
            MotoClient motoClient) {

        this.repository = repository;
        this.clienteClient = clienteClient;
        this.motoClient = motoClient;
    }

    @Override
    public RentDto addRent(RentDto dto) {
        clienteClient.getClienteById(dto.clienteId());

        MotoDto moto =
                motoClient.getMotoById(dto.motoId());

        if (!moto.disponibilidad()) {
            throw new MotoNoDisponibleException("Moto no disponible");
        }

        if (dto.fechaFin().isBefore(dto.fechaInicio())) {
            throw new RuntimeException(
                    "La fecha fin no puede ser menor a la fecha inicio");
        }

        MotoDto motoActualizada = new MotoDto(
                moto.id(),
                moto.marca(),
                moto.modelo(),
                moto.patente(),
                moto.valor(),
                moto.annio(),
                moto.color(),
                moto.kilometraje(),
                false
        );

        motoClient.updateMoto(
                moto.id(),
                motoActualizada
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

        rent.setMotoId(dto.motoId());
        rent.setClienteId(dto.clienteId());
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

        MotoDto moto = motoClient.getMotoById(rent.getMotoId());

        MotoDto motoActualizada = new MotoDto(
                moto.id(),
                moto.marca(),
                moto.modelo(),
                moto.patente(),
                moto.valor(),
                moto.annio(),
                moto.color(),
                moto.kilometraje(),
                true
        );

        motoClient.updateMoto(moto.id(), motoActualizada);

        repository.deleteById(id);

        return new ResponseDto("Rent eliminado correctamente");
    }

    private RentDto modelToDto(Rent model) {
        return new RentDto(
                model.getId(),
                model.getMotoId(),
                model.getClienteId(),
                model.getFechaInicio(),
                model.getFechaFin(),
                model.getObservacion()
        );
    }

    private Rent dtoToModel(RentDto dto) {
        Rent rent = new Rent();
        rent.setId(dto.id());
        rent.setMotoId(dto.motoId());
        rent.setClienteId(dto.clienteId());
        rent.setFechaInicio(dto.fechaInicio());
        rent.setFechaFin(dto.fechaFin());
        rent.setObservacion(dto.observacion());
        return rent;
    }
}
