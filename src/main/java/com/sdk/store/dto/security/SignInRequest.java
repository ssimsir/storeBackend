package com.sdk.store.dto.security;

import lombok.Data;

@Data
public class SignInRequest {

	private String email;
	private String password;
	
}
