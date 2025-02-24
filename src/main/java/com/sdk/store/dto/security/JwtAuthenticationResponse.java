package com.sdk.store.dto.security;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
	private String token;
	private String refreshToken;
	private Long userId;
	private String email;
	private String firstName;
	private String lastName;
	private String userName;
	
	
}
