package com.motosport.bff.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.motosport.bff.dto.HealthResponseDto;

class HealthControllerTest {

	@Test
	void health_shouldReturnUpStatusForBffService() {
		// Arrange: Instanciamos el controlador de tu proyecto
		HealthController healthController = new HealthController();

		// Act: Ejecutamos el metodo que revisa el estado del BFF
		HealthResponseDto result = healthController.health();

		// Assert: Validamos que informe estado UP para el servicio "bff" y traiga timestamp
		assertNotNull(result);
		assertEquals("UP", result.status());
		assertEquals("bff", result.service());
		assertNotNull(result.timestamp());
	}
}
