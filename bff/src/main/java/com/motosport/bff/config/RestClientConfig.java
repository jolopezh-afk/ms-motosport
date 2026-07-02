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
     * Bean necesario porque Spring Boot no siempre expone automáticamente
     * RestClient.Builder en el contexto.
     */
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    /**
     * RestClient con interceptor para reenviar el header Authorization
     * hacia los microservicios downstream.
     */
    @Bean
    public RestClient restClient(RestClient.Builder builder) {
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

    /**
     * Obtiene el header Authorization del request actual del BFF
     */
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