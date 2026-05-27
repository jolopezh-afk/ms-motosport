package com.motosport.arriendo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motosport.arriendo.client.ClienteClient;
import com.motosport.arriendo.client.MotoClient;
import com.motosport.arriendo.dto.ArriendoDto;
import com.motosport.arriendo.dto.ClienteDto;
import com.motosport.arriendo.dto.MotoDto;
import com.motosport.arriendo.dto.ResponseDto;
import com.motosport.arriendo.exception.MotoNoDisponibleException;
import com.motosport.arriendo.model.Arriendo;
import com.motosport.arriendo.repository.ArriendoRepository;

@Service
public class ArriendoServiceImpl implements ArriendoService {
    
    private final ArriendoRepository repository;
    private final ClienteClient clienteClient;
    private final MotoClient motoClient;

    public ArriendoServiceImpl(
            ArriendoRepository repository,
            ClienteClient clienteClient,
            MotoClient motoClient) {

        this.repository = repository;
        this.clienteClient = clienteClient;
        this.motoClient = motoClient;
    }

    @Override
    public ArriendoDto addArriendo(ArriendoDto dto) {
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
        Arriendo arriendo =
                repository.save(dtoToModel(dto));

        return modelToDto(arriendo);
    }

    @Override
    public ArriendoDto getArriendo(Long id) {
        Arriendo arriendo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arriendo no encontrado"));
        return modelToDto(arriendo);
    }

    @Override
    public List<ArriendoDto> getAllArriendos() {
        List<Arriendo> arriendos = repository.findAll();
        return arriendos.stream()
                .map(this::modelToDto)
                .toList();
    }

    @Override
    public ArriendoDto updateArriendo(Long id, ArriendoDto dto) {
        Arriendo arriendo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arriendo no encontrado"));

        arriendo.setMotoId(dto.motoId());
        arriendo.setClienteId(dto.clienteId());
        arriendo.setFechaInicio(dto.fechaInicio());
        arriendo.setFechaFin(dto.fechaFin());
        arriendo.setObservacion(dto.observacion());

        repository.save(arriendo);

        return modelToDto(arriendo);
    }

    @Override
    public ResponseDto deleteArriendo(Long id) {
        Arriendo arriendo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arriendo no encontrado"));

        MotoDto moto = motoClient.getMotoById(arriendo.getMotoId());

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

        return new ResponseDto("Arriendo eliminado correctamente");
    }

    private ArriendoDto modelToDto(Arriendo model) {
        return new ArriendoDto(
                model.getId(),
                model.getMotoId(),
                model.getClienteId(),
                model.getFechaInicio(),
                model.getFechaFin(),
                model.getObservacion()
        );
    }

    private Arriendo dtoToModel(ArriendoDto dto) {
        Arriendo arriendo = new Arriendo();
        arriendo.setId(dto.id());
        arriendo.setMotoId(dto.motoId());
        arriendo.setClienteId(dto.clienteId());
        arriendo.setFechaInicio(dto.fechaInicio());
        arriendo.setFechaFin(dto.fechaFin());
        arriendo.setObservacion(dto.observacion());
        return arriendo;
    }
}
