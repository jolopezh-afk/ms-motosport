package com.motosport.bff.service;

import com.motosport.bff.client.AuthClient;
import com.motosport.bff.dto.AuthResponse;
import com.motosport.bff.dto.LoginRequest;
import com.motosport.bff.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private final AuthClient authClient;

	public AuthService(AuthClient authClient) {
		this.authClient = authClient;
	}

	public AuthResponse login(LoginRequest request) {
		return authClient.login(request);
	}

	public AuthResponse register(RegisterRequest request) {
		return authClient.register(request);
	}
}
