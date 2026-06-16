package com.motosport.auth.service;

import com.motosport.auth.domain.User;
import com.motosport.auth.dto.AuthResponse;
import com.motosport.auth.dto.LoginRequest;
import com.motosport.auth.dto.RegisterRequest;
import com.motosport.auth.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public AuthResponse login(LoginRequest request) {
		User user = userRepository.findByEmailAndActiveTrue(request.email())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
		}

		return new AuthResponse(jwtService.generateToken(user.getEmail()));
	}

	public AuthResponse register(RegisterRequest request) {
		if (userRepository.existsById(request.email())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
		}

		User user = new User(request.email(), passwordEncoder.encode(request.password()));
		userRepository.save(user);

		return new AuthResponse(jwtService.generateToken(user.getEmail()));
	}
}
