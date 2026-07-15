package com.motosport.bff.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import com.motosport.bff.dto.BikeDto;

// Adaptado de BikeServiceImplTest del otro proyecto: ms-motosport no tiene una interfaz
// BikeService/BikeServiceImpl, el rol equivalente (envolver las llamadas RestClient al
// microservicio de bicicletas) lo cumple BikeClient.
@ExtendWith(MockitoExtension.class)
class BikeClientTest {

	private static final String BIKE_URL = "http://localhost:4003";

	// Usamos RETURNS_DEEP_STUBS para que Mockito entienda el encadenamiento
	// .get().uri().retrieve().body()
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private RestClient restClient;

	private BikeDto suzuki() {
		return new BikeDto(1L, "Suzuki", "Gixxer", "AB1234", new BigDecimal("3200000"), 2019, "Negro", 15000, true);
	}

	@Test
	void findById_shouldCallRestClientAndReturnBike() {
		BikeClient bikeClient = new BikeClient(restClient, BIKE_URL);
		BikeDto mockBike = suzuki();

		when(restClient.get()
				.uri(anyString(), eq(1L))
				.retrieve()
				.body(BikeDto.class))
				.thenReturn(mockBike);

		BikeDto result = bikeClient.findById(1L);

		assertNotNull(result);
		assertEquals("Suzuki", result.marca());
	}

	@Test
	void findAll_shouldCallRestClientAndReturnBikeList() {
		BikeClient bikeClient = new BikeClient(restClient, BIKE_URL);

		when(restClient.get()
				.uri(anyString())
				.retrieve()
				.body(any(org.springframework.core.ParameterizedTypeReference.class)))
				.thenReturn(List.of(suzuki()));

		List<BikeDto> result = bikeClient.findAll();

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	void create_shouldPostAndReturnSavedBike() {
		BikeClient bikeClient = new BikeClient(restClient, BIKE_URL);
		BikeDto inputDto = new BikeDto(null, "Ducati", "Monster", "CD5678", new BigDecimal("8500000"), 2022, "Rojo", 3000, true);
		BikeDto savedDto = new BikeDto(99L, "Ducati", "Monster", "CD5678", new BigDecimal("8500000"), 2022, "Rojo", 3000, true);

		when(restClient.post()
				.uri(anyString())
				.body(any(BikeDto.class))
				.retrieve()
				.body(BikeDto.class))
				.thenReturn(savedDto);

		BikeDto result = bikeClient.create(inputDto);

		assertNotNull(result);
		assertEquals(99L, result.id());
	}

	@Test
	void delete_shouldCallRestClientDelete() {
		BikeClient bikeClient = new BikeClient(restClient, BIKE_URL);

		when(restClient.delete()
				.uri(anyString(), eq(10L))
				.retrieve()
				.toBodilessEntity())
				.thenReturn(null);

		bikeClient.delete(10L);

		verify(restClient.delete().uri(anyString(), eq(10L)).retrieve()).toBodilessEntity();
	}
}
