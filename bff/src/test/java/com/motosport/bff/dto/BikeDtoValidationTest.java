package com.motosport.bff.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class BikeDtoValidationTest {

	private Validator validator;

	@BeforeEach
	void setUp() {
		// Inicializa el validador real
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void validBikeDto_shouldHaveNoViolations() {
		// Arrange: Una moto con todos sus datos correctos, segun el BikeDto real (8 campos)
		BikeDto dto = new BikeDto(1L, "Kawasaki", "Ninja 400", "AB1234", new BigDecimal("4500000"), 2023, "Verde", 1200, true);

		// Act
		var violations = validator.validate(dto);

		// Assert: No deberian existir errores
		assertTrue(violations.isEmpty());
	}

	@Test
	void invalidBikeDto_shouldReportBlankAndNegativeFields() {
		// Arrange: marca/modelo/patente/color vacios, valor y kilometraje negativos, anio invalido
		BikeDto dto = new BikeDto(null, "", "", "", new BigDecimal("-100"), 1800, "", -50, null);

		// Act
		var violations = validator.validate(dto);
		Set<String> fields = violations.stream()
			.map(v -> v.getPropertyPath().toString())
			.collect(Collectors.toSet());

		// Assert: cada restriccion del BikeDto real debe dispararse
		assertEquals(Set.of("marca", "modelo", "patente", "valor", "anio", "color", "kilometraje", "disponibilidad"), fields);
	}

	@Test
	void invalidBikeDto_shouldReportPatenteWithWrongFormat() {
		BikeDto dto = new BikeDto(1L, "Honda", "CBR", "ab-123", new BigDecimal("2500000"), 2022, "Rojo", 500, true);

		var violations = validator.validate(dto);
		Set<String> fields = violations.stream()
			.map(v -> v.getPropertyPath().toString())
			.collect(Collectors.toSet());

		assertEquals(Set.of("patente"), fields);
	}
}
