package com.motosport.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.motosport.auth.domain.UserAccount;
import com.motosport.auth.dto.AuthResponse;
import com.motosport.auth.dto.LoginRequest;
import com.motosport.auth.dto.RegisterRequest;
import com.motosport.auth.repository.UserAccountRepository;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	// Mock: reemplaza las dependencias reales por dobles de prueba controlables.
	@Mock
	private UserAccountRepository userAccountRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtService jwtService;

	private UserAccount buildUser(String email, String passwordHash) {
		UserAccount user = new UserAccount();
		user.setName("Juan Perez");
		user.setEmail(email);
		user.setPasswordHash(passwordHash);
		user.setRole("USER");
		user.setEnabled(true);
		return user;
	}

	@Test
	void login_shouldReturnTokenWhenCredentialsAreValid() {
		AuthService authService = new AuthService(userAccountRepository, passwordEncoder, jwtService);
		UserAccount user = buildUser("juan@test.com", "hashClave");
		when(userAccountRepository.findByEmailIgnoreCase("juan@test.com")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("clave123", "hashClave")).thenReturn(true);
		when(jwtService.generateAccessToken(user)).thenReturn("token-generado");
		when(jwtService.getAccessTokenTtlSeconds()).thenReturn(1800L);

		AuthResponse result = authService.login(new LoginRequest("juan@test.com", "clave123"));

		assertEquals("token-generado", result.accessToken());
		assertEquals("Bearer", result.tokenType());
		assertEquals(1800L, result.expiresIn());
	}

	@Test
	void login_shouldThrowBadCredentialsWhenUserMissing() {
		AuthService authService = new AuthService(userAccountRepository, passwordEncoder, jwtService);
		when(userAccountRepository.findByEmailIgnoreCase("nadie@test.com")).thenReturn(Optional.empty());

		assertThrows(
			BadCredentialsException.class,
			() -> authService.login(new LoginRequest("nadie@test.com", "clave123"))
		);

		verify(jwtService, never()).generateAccessToken(any());
	}

	@Test
	void login_shouldThrowBadCredentialsWhenPasswordDoesNotMatch() {
		AuthService authService = new AuthService(userAccountRepository, passwordEncoder, jwtService);
		UserAccount user = buildUser("juan@test.com", "hashClave");
		when(userAccountRepository.findByEmailIgnoreCase("juan@test.com")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("claveMala", "hashClave")).thenReturn(false);

		assertThrows(
			BadCredentialsException.class,
			() -> authService.login(new LoginRequest("juan@test.com", "claveMala"))
		);

		verify(jwtService, never()).generateAccessToken(any());
	}

	@Test
	void login_shouldThrowBadCredentialsWhenUserIsDisabled() {
		AuthService authService = new AuthService(userAccountRepository, passwordEncoder, jwtService);
		UserAccount user = buildUser("juan@test.com", "hashClave");
		user.setEnabled(false);
		when(userAccountRepository.findByEmailIgnoreCase("juan@test.com")).thenReturn(Optional.of(user));

		assertThrows(
			BadCredentialsException.class,
			() -> authService.login(new LoginRequest("juan@test.com", "clave123"))
		);
	}

	@Test
	void register_shouldThrowIllegalArgumentWhenEmailAlreadyExists() {
		AuthService authService = new AuthService(userAccountRepository, passwordEncoder, jwtService);
		when(userAccountRepository.existsByEmailIgnoreCase("existente@test.com")).thenReturn(true);

		assertThrows(
			IllegalArgumentException.class,
			() -> authService.register(new RegisterRequest("Existente", "existente@test.com", "clave123"))
		);

		verify(userAccountRepository, never()).save(any());
	}

	@Test
	void register_shouldSaveNewUserWithEncodedPassword() {
		AuthService authService = new AuthService(userAccountRepository, passwordEncoder, jwtService);
		when(userAccountRepository.existsByEmailIgnoreCase(anyString())).thenReturn(false);
		when(passwordEncoder.encode("clave123")).thenReturn("claveEncriptada");

		authService.register(new RegisterRequest("Nuevo Usuario", "Nuevo@Test.com", "clave123"));

		// Captor permite inspeccionar el argumento real enviado al mock.
		ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
		verify(userAccountRepository).save(captor.capture());
		assertEquals("Nuevo Usuario", captor.getValue().getName());
		assertEquals("nuevo@test.com", captor.getValue().getEmail());
		assertEquals("claveEncriptada", captor.getValue().getPasswordHash());
		assertEquals("USER", captor.getValue().getRole());
		assertTrue(captor.getValue().getEnabled());
	}
}
