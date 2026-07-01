package com.motosport.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.motosport.auth.domain.UserAccount;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

// Nota: a diferencia del otro proyecto (que firma con HMAC usando un secreto compartido
// via jjwt), el JwtService de ms-motosport firma con RS256 usando un JwtEncoder de Spring
// Security respaldado por un par de llaves RSA (ver JwtKeyConfig). Para testear sin depender
// de archivos de llaves ni levantar el contexto de Spring, generamos un keypair RSA en memoria.
class JwtServiceTest {

	private static final long ACCESS_TOKEN_MINUTES = 30;
	private static final String ISSUER = "motosport-auth-test";

	private JwtService jwtService;
	private JwtDecoder jwtDecoder;

	@BeforeEach
	@SuppressWarnings("unused")
	void setUp() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair keyPair = generator.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID("auth-key-1")
				.build();

		JwtEncoder jwtEncoder = new NimbusJwtEncoder(new ImmutableJWKSet<>(new com.nimbusds.jose.jwk.JWKSet(rsaKey)));
		jwtService = new JwtService(jwtEncoder, ISSUER, ACCESS_TOKEN_MINUTES);
		jwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build();
	}

	private UserAccount buildUser(String email, String name, String role) {
		UserAccount user = new UserAccount();
		user.setEmail(email);
		user.setName(name);
		user.setRole(role);
		return user;
	}

	@Test
	void generateAccessToken_shouldContainEmailAsSubject() {
		String token = jwtService.generateAccessToken(buildUser("juan@test.com", "Juan", "USER"));

		Jwt jwt = jwtDecoder.decode(token);

		assertEquals("juan@test.com", jwt.getSubject());
	}

	@Test
	void generateAccessToken_shouldIncludeNameAndRoleClaims() {
		String token = jwtService.generateAccessToken(buildUser("juan@test.com", "Juan", "ADMIN"));

		Jwt jwt = jwtDecoder.decode(token);

		assertEquals("Juan", jwt.getClaimAsString("name"));
		assertEquals("ADMIN", jwt.getClaimAsString("role"));
	}

	@Test
	void generateAccessToken_shouldSetIssuerAndExpirationInTheFuture() {
		String token = jwtService.generateAccessToken(buildUser("juan@test.com", "Juan", "USER"));

		Jwt jwt = jwtDecoder.decode(token);

		assertTrue(jwt.getIssuer().toString().contains(ISSUER));
		assertTrue(jwt.getExpiresAt().isAfter(Instant.now()));
		assertTrue(jwt.getExpiresAt().isAfter(jwt.getIssuedAt()));
	}

	@Test
	void getAccessTokenTtlSeconds_shouldConvertMinutesToSeconds() {
		assertEquals(ACCESS_TOKEN_MINUTES * 60, jwtService.getAccessTokenTtlSeconds());
	}
}
