package com.sdk.store.controller.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdk.store.dto.security.JwtAuthenticationResponse;
import com.sdk.store.dto.security.RefreshTokenRequest;
import com.sdk.store.dto.security.SignInRequest;
import com.sdk.store.dto.security.SignUpRequest;
import com.sdk.store.entities.User;
import com.sdk.store.services.security.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/API/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
		return ResponseEntity.ok(authenticationService.signup(signUpRequest));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
		return ResponseEntity.ok(authenticationService.signin(signInRequest)); 
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest)); 
	}
}
