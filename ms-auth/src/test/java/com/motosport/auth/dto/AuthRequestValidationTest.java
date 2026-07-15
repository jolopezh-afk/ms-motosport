package com.motosport.auth.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class AuthRequestValidationTest {

	// Validator es el componente que ejecuta las reglas de Jakarta Validation (@NotBlank, @Email, etc.).
	private Validator validator;

	@BeforeEach
	@SuppressWarnings("unused")
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void validLoginRequest_shouldHaveNoViolations() {
		LoginRequest dto = new LoginRequest("juan@test.com", "clave123");

		var violations = validator.validate(dto);

		assertTrue(violations.isEmpty());
	}

	@Test
	void invalidLoginRequest_shouldReportEmailAndPassword() {
		LoginRequest dto = new LoginRequest("no-es-un-email", "");

		var violations = validator.validate(dto);

		assertEquals(2, violations.size());
	}

	@Test
	void validRegisterRequest_shouldHaveNoViolations() {
		RegisterRequest dto = new RegisterRequest("Juan Perez", "nuevo@test.com", "clave123");

		var violations = validator.validate(dto);

		assertTrue(violations.isEmpty());
	}

	@Test
	void invalidRegisterRequest_shouldReportNameEmailAndPassword() {
		// RegisterRequest en ms-motosport agrega el campo "name" (@NotBlank), a diferencia
		// de la version original de este test contra el otro proyecto.
		RegisterRequest dto = new RegisterRequest("", "no-es-un-email", "");

		var violations = validator.validate(dto);

		assertEquals(3, violations.size());
	}
}
