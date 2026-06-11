package com.motosport.bike.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.motosport.bike.dto.BikeDto;
import com.motosport.bike.dto.ResponseDto;
import com.motosport.bike.model.Bike;
import com.motosport.bike.repository.BikeRepository;

@Service
public class BikeServiceImpl implements BikeService{

    private final BikeRepository repository;

    public BikeServiceImpl(BikeRepository repository){
        this.repository = repository;
    }

    @Override
    public BikeDto addBike(BikeDto dto){
        Bike bike = repository.save(dtoToModel(dto));
        return modelToDto(bike);
    }

    @Override
    public BikeDto getBike(Long id) {
        Bike bike = repository.findById(id).orElseThrow(() -> new RuntimeException("Bike no encontrada"));
        return modelToDto(bike);
    }
    
    @Override
    public List<BikeDto> getAllBike() {
        List<Bike> bikes =
            repository.findAll();
         return bikes.stream()
                .map(this::modelToDto)
                .toList();
    }
    
    @Override
    public BikeDto updateBike(Long id, BikeDto dto) {
        Bike bike = repository.findById(id).orElseThrow(() -> new RuntimeException("Bike no encontrada"));
        bike.setMarca(dto.marca());
        bike.setModelo(dto.modelo());
        bike.setPatente(dto.patente());
        bike.setValor(dto.valor());
        bike.setAnnio(dto.annio());
        bike.setColor(dto.color());
        bike.setKilometraje(dto.kilometraje());
        bike.setDisponibilidad(dto.disponibilidad());
        repository.save(bike);
        return modelToDto(bike);
    
    }

    @Override
    public ResponseDto deleteBike (Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return new ResponseDto("Id eliminada correctamente.");
        } else{
            return new ResponseDto("Id no encontrada.");
        }
    }

    private BikeDto modelToDto(Bike model){
        return new BikeDto(
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

    private Bike dtoToModel(BikeDto dto){
        Bike bike = new Bike();
        bike.setId(dto.id());
        bike.setMarca(dto.marca());
        bike.setModelo(dto.modelo());
        bike.setPatente(dto.patente());
        bike.setValor(dto.valor());
        bike.setAnnio(dto.annio());
        bike.setColor(dto.color());
        bike.setKilometraje(dto.kilometraje());
        bike.setDisponibilidad(dto.disponibilidad());
        return bike;
    }


}
