package com.motosport.auth.config;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.motosport.auth.controller.AuthController;
import com.motosport.auth.service.AuthService;

// A diferencia de los tests de service/controller, aca SI necesitamos levantar
// el filtro de seguridad real para comprobar el comportamiento de autorizacion.
//
// Nota: el SecurityConfig de ms-motosport usa anyRequest().permitAll(), es decir,
// TODO se permite sin autenticacion (a diferencia del otro proyecto, donde solo
// login/register estaban abiertos y el resto devolvia 403). Por eso este test
// verifica que ninguna ruta sea bloqueada por el filtro de seguridad (403); una
// ruta no mapeada debe llegar igualmente al dispatcher y responder 404, no 403.
@WebMvcTest(controllers = AuthController.class)
@Import({ SecurityConfig.class, SecurityConfigTest.TestConfig.class })
class SecurityConfigTest {

	@Autowired
	private MockMvc mockMvc;

	static class TestConfig {
		@Bean
		AuthService authService() {
			return Mockito.mock(AuthService.class);
		}
	}

	@Test
	void login_shouldBeAllowedWithoutAuthentication() throws Exception {
		MvcResult result = mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"email\":\"juan@test.com\",\"password\":\"clave1234\"}"))
			.andReturn();

		assertNotEquals(403, result.getResponse().getStatus());
	}

	@Test
	void register_shouldBeAllowedWithoutAuthentication() throws Exception {
		MvcResult result = mockMvc.perform(post("/api/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Juan\",\"email\":\"nuevo@test.com\",\"password\":\"clave1234\"}"))
			.andReturn();

		assertNotEquals(403, result.getResponse().getStatus());
	}

	@Test
	void unmappedEndpoint_shouldNotBeForbiddenByTheSecurityFilter() throws Exception {
		// permitAll() deja pasar la request hasta el dispatcher; como no hay
		// controller para esta ruta, el resultado esperado es 404, nunca 403.
		MvcResult result = mockMvc.perform(get("/api/auth/perfil")).andReturn();

		assertNotEquals(403, result.getResponse().getStatus());
	}
}
