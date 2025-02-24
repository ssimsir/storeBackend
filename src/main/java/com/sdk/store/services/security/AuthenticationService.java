package com.sdk.store.services.security;

import com.sdk.store.dto.security.JwtAuthenticationResponse;
import com.sdk.store.dto.security.RefreshTokenRequest;
import com.sdk.store.dto.security.SignInRequest;
import com.sdk.store.dto.security.SignUpRequest;
import com.sdk.store.entities.User;

public interface AuthenticationService {

	
	 User signup(SignUpRequest signUpRequest);
	 JwtAuthenticationResponse signin(SignInRequest signInRequest);
	 JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
