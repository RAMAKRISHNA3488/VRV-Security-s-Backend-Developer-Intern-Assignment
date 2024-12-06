package com.RoleSecure.RoleSecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.RoleSecure.RoleSecure.Service.AuthService;
import com.RoleSecure.RoleSecure.models.User;
import com.RoleSecure.RoleSecure.req.and.repo.LoginRequest;
import com.RoleSecure.RoleSecure.req.and.repo.LoginResponse;

@Controller
@RequestMapping("/api")
public class UserController {
	private final AuthService authService;

	@Autowired
	public UserController(AuthService authService) {
		this.authService = authService;
	}

	// Endpoint for user registration
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody User user) {
		try {
			authService.register(user);
			return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error registering user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// Endpoint for user login
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		try {
			String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
			return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error during login: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

}
