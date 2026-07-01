package com.motosport.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class RestClientConfig {

	/**
	 * Antes no existía ningún bean de tipo RestClient en el contexto, por lo que
	 * AuthClient, BikeClient, CustomerClient y RentClient no podían inyectarse
	 * (Spring Boot solo autoconfigura RestClient.Builder, no RestClient) y la
	 * aplicación no arrancaba.
	 *
	 * Además, se agrega un interceptor que reenvía el header Authorization que
	 * llegó al BFF hacia los microservicios downstream, para no perder el
	 * contexto del usuario autenticado en las llamadas salientes.
	 */
	@Bean
	RestClient restClient(RestClient.Builder builder) {
		return builder
				.requestInterceptor((request, body, execution) -> {
					String authorization = currentAuthorizationHeader();
					if (authorization != null) {
						request.getHeaders().add(HttpHeaders.AUTHORIZATION, authorization);
					}
					return execution.execute(request, body);
				})
				.build();
	}

	private String currentAuthorizationHeader() {
		ServletRequestAttributes attributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			return null;
		}
		HttpServletRequest servletRequest = attributes.getRequest();
		return servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
	}
}
