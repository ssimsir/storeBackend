package com.sdk.store.services.security.impl;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sdk.store.dto.security.JwtAuthenticationResponse;
import com.sdk.store.dto.security.RefreshTokenRequest;
import com.sdk.store.dto.security.SignInRequest;
import com.sdk.store.dto.security.SignUpRequest;
import com.sdk.store.entities.User;
import com.sdk.store.repository.UserRepository;
import com.sdk.store.services.security.AuthenticationService;
import com.sdk.store.services.security.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final JWTService jwtService;
	
	public User signup(SignUpRequest signUpRequest) {
		
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setUserName(signUpRequest.getUserName());
		user.setRoles(signUpRequest.getRoles());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		return userRepository.save(user);
	}
	
	public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
		var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		jwtAuthenticationResponse.setEmail(user.getEmail());
		jwtAuthenticationResponse.setFirstName(user.getFirstName());
		jwtAuthenticationResponse.setLastName(user.getLastName());
		jwtAuthenticationResponse.setUserName(user.getUsername());
		jwtAuthenticationResponse.setUserId(user.getUserId());
		
		return jwtAuthenticationResponse;
	}
	
	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String userEmail=jwtService.extractUserName(refreshTokenRequest.getToken());
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
			var jwt=jwtService.generateToken(user);
			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
			return jwtAuthenticationResponse;
			
		}
		return null;
	}
}
