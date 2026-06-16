package com.motosport.bff.client;

import com.motosport.bff.dto.AuthResponse;
import com.motosport.bff.dto.LoginRequest;
import com.motosport.bff.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AuthClient {

	private final RestClient restClient;
	private final String authBaseUrl;

	public AuthClient(RestClient restClient, @Value("${auth.service.base-url}") String authBaseUrl) {
		this.restClient = restClient;
		this.authBaseUrl = authBaseUrl;
	}

	public AuthResponse login(LoginRequest request) {
		return restClient.post()
			.uri(authBaseUrl + "/login")
			.body(request)
			.retrieve()
			.body(AuthResponse.class);
	}

	public AuthResponse register(RegisterRequest request) {
		return restClient.post()
			.uri(authBaseUrl + "/register")
			.body(request)
			.retrieve()
			.body(AuthResponse.class);
	}
}

