package com.motosport.moto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motosport.moto.dto.MotoDto;
import com.motosport.moto.dto.ResponseDto;
import com.motosport.moto.model.Moto;
import com.motosport.moto.repository.MotoRepository;

@Service
public class MotoServiceImpl implements MotoService{

    private final MotoRepository repository;

    public MotoServiceImpl(MotoRepository repository){
        this.repository = repository;
    }

    @Override
    public MotoDto addMoto(MotoDto dto){
        Moto moto = repository.save(dtoToModel(dto));
        return modelToDto(moto);
    }

    @Override
    public MotoDto getMoto(Long id) {
        Moto moto = repository.findById(id).orElseThrow(() -> new RuntimeException("Moto no encontrada"));
        return modelToDto(moto);
    }
    
    @Override
    public List<MotoDto> getAllMoto() {
        List<Moto> motos =
            repository.findAll();
         return motos.stream()
                .map(this::modelToDto)
                .toList();
    }
    
    @Override
    public MotoDto updateMoto(Long id, MotoDto dto) {
        Moto moto = repository.findById(id).orElseThrow(() -> new RuntimeException("Moto no encontrada"));
        moto.setMarca(dto.marca());
        moto.setModelo(dto.modelo());
        moto.setPatente(dto.patente());
        moto.setValor(dto.valor());
        moto.setAnnio(dto.annio());
        moto.setColor(dto.color());
        moto.setKilometraje(dto.kilometraje());
        moto.setDisponibilidad(dto.disponibilidad());
        repository.save(moto);
        return modelToDto(moto);
    
    }

    @Override
    public ResponseDto deleteMoto (Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return new ResponseDto("Id eliminada correctamente.");
        } else{
            return new ResponseDto("Id no encontrada.");
        }
    }

    private MotoDto modelToDto(Moto model){
        return new MotoDto(
            model.getId(),
            model.getMarca(),
            model.getModelo(),
            model.getPatente(),
            model.getValor(),
            model.getAnnio(),
            model.getColor(),
            model.getKilometraje(),
            model.getDisponibilidad()
        );
    }

    private Moto dtoToModel(MotoDto dto){
        Moto moto = new Moto();
        moto.setId(dto.id());
        moto.setMarca(dto.marca());
        moto.setModelo(dto.modelo());
        moto.setPatente(dto.patente());
        moto.setValor(dto.valor());
        moto.setAnnio(dto.annio());
        moto.setColor(dto.color());
        moto.setKilometraje(dto.kilometraje());
        moto.setDisponibilidad(dto.disponibilidad());
        return moto;
    }


}
