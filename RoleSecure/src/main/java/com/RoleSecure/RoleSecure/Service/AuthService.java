package com.RoleSecure.RoleSecure.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.RoleSecure.RoleSecure.Config.JwtProvider;
import com.RoleSecure.RoleSecure.Repository.UserRepository;
import com.RoleSecure.RoleSecure.models.User;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final JwtProvider jwtProvider;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
	}

	public void register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public String login(String username, String password) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		return jwtProvider.generateToken((Authentication) user);
	}
}
