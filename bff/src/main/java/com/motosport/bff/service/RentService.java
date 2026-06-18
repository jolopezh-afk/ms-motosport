package com.motosport.bff.service;

import com.motosport.bff.client.BikeClient;
import com.motosport.bff.client.CustomerClient;
import com.motosport.bff.client.RentClient;
import com.motosport.bff.dto.BikeDto;
import com.motosport.bff.dto.CustomerDto;
import com.motosport.bff.dto.RentDto;
import com.motosport.bff.dto.ResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentService {

    private final RentClient rentClient;
    private final BikeClient bikeClient;
    private final CustomerClient customerClient;

    public RentService(
            RentClient rentClient,
            BikeClient bikeClient,
            CustomerClient customerClient) {

        this.rentClient = rentClient;
        this.bikeClient = bikeClient;
        this.customerClient = customerClient;
    }

    public List<RentDto> getAll() {
        return rentClient.findAll();
    }

    public RentDto getById(Long id) {
        return rentClient.findById(id);
    }

    public RentDto create(RentDto dto) {

        BikeDto bike = bikeClient.findById(dto.bikeId());

        CustomerDto customer =
                customerClient.findById(dto.customerId());

        validarBikeDisponible(bike);
        validarLicencia(customer);
        validarFechas(dto);

        RentDto rent = rentClient.create(dto);

        BikeDto bikeActualizada = new BikeDto(
                bike.id(),
                bike.marca(),
                bike.modelo(),
                bike.patente(),
                bike.valor(),
                bike.anio(),
                bike.color(),
                bike.kilometraje(),
                false
        );

        bikeClient.update(bike.id(), bikeActualizada);

        return rent;
    }

    public RentDto update(Long id, RentDto dto) {

        BikeDto bike = bikeClient.findById(dto.bikeId());

        CustomerDto customer =
                customerClient.findById(dto.customerId());

        validarBikeDisponible(bike);
        validarLicencia(customer);
        validarFechas(dto);

        return rentClient.update(id, dto);
    }

    public ResponseDto delete(Long id) {

        RentDto rent = rentClient.findById(id);

        BikeDto bike = bikeClient.findById(rent.bikeId());

        BikeDto bikeDisponible = new BikeDto(
                bike.id(),
                bike.marca(),
                bike.modelo(),
                bike.patente(),
                bike.valor(),
                bike.anio(),
                bike.color(),
                bike.kilometraje(),
                true
        );

        bikeClient.update(bike.id(), bikeDisponible);

        rentClient.delete(id);

        return new ResponseDto(
                "Rent eliminado correctamente"
        );
    }

    private void validarBikeDisponible(BikeDto bike) {

        if (!bike.disponibilidad()) {
            throw new IllegalArgumentException(
                    "La bike no está disponible"
            );
        }
    }

    private void validarLicencia(CustomerDto customer) {

        if (customer.fechaVencimiento()
                .isBefore(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "La licencia está vencida"
            );
        }
    }

    private void validarFechas(RentDto dto) {

        if (dto.fechaFin()
                .isBefore(dto.fechaInicio())) {

            throw new IllegalArgumentException(
                    "La fecha fin no puede ser menor a la fecha inicio"
            );
        }
    }
}