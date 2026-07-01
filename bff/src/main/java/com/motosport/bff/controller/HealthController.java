package com.motosport.bff.controller;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.motosport.bff.dto.HealthResponseDto;

@RestController
public class HealthController {

	@GetMapping("/api/health")
	public HealthResponseDto health() {
		return new HealthResponseDto("UP", "bff", Instant.now().toString());
	}
}
