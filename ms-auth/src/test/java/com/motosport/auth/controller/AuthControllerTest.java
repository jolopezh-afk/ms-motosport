package com.motosport.auth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.motosport.auth.dto.AuthResponse;
import com.motosport.auth.dto.LoginRequest;
import com.motosport.auth.dto.RegisterRequest;
import com.motosport.auth.service.AuthService;

// Integra Mockito con JUnit 5.
// Aisla la capa web mockeando AuthService: no se levanta contexto de Spring.
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

	@Mock
	private AuthService authService;

	@Test
	void login_shouldReturnOkWithAuthResponseFromService() {
		AuthController authController = new AuthController(authService);
		AuthResponse expected = new AuthResponse("token-123", "Bearer", 1800L);
		when(authService.login(any())).thenReturn(expected);

		ResponseEntity<AuthResponse> result = authController.login(new LoginRequest("juan@test.com", "clave123"));

		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("token-123", result.getBody().accessToken());
	}

	@Test
	void register_shouldReturnCreatedAndDelegateToService() {
		AuthController authController = new AuthController(authService);
		doNothing().when(authService).register(any());

		ResponseEntity<Void> result = authController.register(
				new RegisterRequest("Nuevo Usuario", "nuevo@test.com", "clave123"));

		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		verify(authService).register(any());
	}
}
