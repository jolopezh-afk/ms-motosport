package com.motosport.auth.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.motosport.auth.domain.UserAccount;

// @DataJpaTest (en vez de @SpringBootTest) carga solo la capa JPA y auto-configura
// una base H2 embebida, evitando levantar JwtKeyConfig/JwksController, que requieren
// archivos de llaves RSA en disco no disponibles en el entorno de test.
@DataJpaTest
@ActiveProfiles("test")
class UserAccountRepositoryTest {

	// En test de integracion, Spring inyecta el bean real del repository.
	@Autowired
	private UserAccountRepository userAccountRepository;

	private UserAccount save(String name, String email, boolean enabled) {
		UserAccount user = new UserAccount();
		user.setName(name);
		user.setEmail(email);
		user.setPasswordHash("hashDeClave");
		user.setRole("USER");
		user.setEnabled(enabled);
		user.setCreatedAt(Instant.now());
		return userAccountRepository.save(user);
	}

	@BeforeEach
	@SuppressWarnings("unused")
	void setUp() {
		userAccountRepository.deleteAll();
		save("Link", "link@hyrule.com", true);
		save("Ganondorf", "ganondorf@hyrule.com", false);
	}

	@Test
	void findByEmailIgnoreCase_shouldFindUserRegardlessOfCase() {
		var result = userAccountRepository.findByEmailIgnoreCase("LINK@Hyrule.com");

		assertTrue(result.isPresent());
		assertEquals("link@hyrule.com", result.get().getEmail());
	}

	@Test
	void findByEmailIgnoreCase_shouldReturnEmptyForUnknownEmail() {
		var result = userAccountRepository.findByEmailIgnoreCase("no-existe@hyrule.com");

		assertFalse(result.isPresent());
	}

	@Test
	void existsByEmailIgnoreCase_shouldReturnTrueForRegisteredEmailRegardlessOfCase() {
		assertTrue(userAccountRepository.existsByEmailIgnoreCase("Link@Hyrule.com"));
	}

	@Test
	void existsByEmailIgnoreCase_shouldReturnTrueEvenForDisabledUser() {
		// existsByEmailIgnoreCase no filtra por habilitado: se usa para evitar emails duplicados al registrar.
		assertTrue(userAccountRepository.existsByEmailIgnoreCase("ganondorf@hyrule.com"));
	}

	@Test
	void existsByEmailIgnoreCase_shouldReturnFalseForUnregisteredEmail() {
		assertFalse(userAccountRepository.existsByEmailIgnoreCase("no-existe@hyrule.com"));
	}
}
