package com.motosport.arriendo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.motosport.arriendo.dto.AuthResponse;
import com.motosport.arriendo.dto.LoginRequest;
import com.motosport.arriendo.dto.RegisterRequest;
import com.motosport.arriendo.exception.AuthenticationException;
import com.motosport.arriendo.exception.ValidationException;
import com.motosport.arriendo.model.User;
import com.motosport.arriendo.repository.UserRepository;
import com.motosport.arriendo.util.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        // Validar que el username no exista
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new ValidationException("Username already exists");
        }

        // Validar que username y password no estén vacíos
        if (request.username() == null || request.username().isBlank()) {
            throw new ValidationException("Username cannot be empty");
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new ValidationException("Password cannot be empty");
        }

        // Hash la contraseña
        String passwordHash = passwordEncoder.encode(request.password());

        // Crear y guardar el usuario
        User user = new User();
        user.setUsername(request.username());
        user.setPasswordHash(passwordHash);

        userRepository.save(user);

        // Generar JWT
        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(token, jwtUtil.getExpiration(), user.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        // Validar que username y password no estén vacíos
        if (request.username() == null || request.username().isBlank()) {
            throw new ValidationException("Username cannot be empty");
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new ValidationException("Password cannot be empty");
        }

        // Buscar usuario por username
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new AuthenticationException("Invalid username or password"));

        // Validar contraseña
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new AuthenticationException("Invalid username or password");
        }

        // Generar JWT
        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(token, jwtUtil.getExpiration(), user.getUsername());
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}
