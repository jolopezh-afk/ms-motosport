package com.motosport.bff.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.motosport.bff.client.BikeClient;
import com.motosport.bff.dto.BikeDto;

// A diferencia del otro proyecto (donde BikeController depende de un BikeService),
// en ms-motosport el BikeController delega directamente en el BikeClient (wrapper de RestClient).
@ExtendWith(MockitoExtension.class)
class BikeControllerTest {

	@Mock
	private BikeClient bikeClient;

	private BikeDto kawasaki() {
		return new BikeDto(1L, "Kawasaki", "Ninja 400", "AB1234", new BigDecimal("4500000"), 2023, "Verde", 1200, true);
	}

	@Test
	void getAll_shouldReturnListOfBikes() {
		BikeController bikeController = new BikeController(bikeClient);
		when(bikeClient.findAll()).thenReturn(List.of(kawasaki()));

		List<BikeDto> result = bikeController.getAll();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Ninja 400", result.get(0).modelo());
	}

	@Test
	void getById_shouldReturnBikeDto() {
		BikeController bikeController = new BikeController(bikeClient);
		BikeDto yamaha = new BikeDto(2L, "Yamaha", "R6", "CD5678", new BigDecimal("6000000"), 2021, "Azul", 8000, true);
		when(bikeClient.findById(2L)).thenReturn(yamaha);

		BikeDto result = bikeController.getById(2L);

		assertNotNull(result);
		assertEquals("Yamaha", result.marca());
	}

	@Test
	void create_shouldReturnCreatedBike() {
		BikeController bikeController = new BikeController(bikeClient);
		BikeDto honda = new BikeDto(null, "Honda", "CBR", "EF9012", new BigDecimal("2500000"), 2020, "Rojo", 500, true);
		BikeDto hondaSaved = new BikeDto(3L, "Honda", "CBR", "EF9012", new BigDecimal("2500000"), 2020, "Rojo", 500, true);
		when(bikeClient.create(any(BikeDto.class))).thenReturn(hondaSaved);

		BikeDto result = bikeController.create(honda);

		assertNotNull(result);
		assertEquals(3L, result.id());
	}

	@Test
	void update_shouldReturnUpdatedBike() {
		BikeController bikeController = new BikeController(bikeClient);
		BikeDto updated = new BikeDto(4L, "Suzuki", "Gixxer", "GH3456", new BigDecimal("3200000"), 2019, "Negro", 15000, false);
		when(bikeClient.update(any(Long.class), any(BikeDto.class))).thenReturn(updated);

		BikeDto result = bikeController.update(4L, updated);

		assertNotNull(result);
		assertEquals(false, result.disponibilidad());
	}

	@Test
	void delete_shouldCallClientDelete() {
		BikeController bikeController = new BikeController(bikeClient);

		bikeController.delete(5L);

		verify(bikeClient).delete(5L);
	}
}
