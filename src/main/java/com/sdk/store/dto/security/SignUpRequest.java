package com.sdk.store.dto.security;

import lombok.Data;

@Data
public class SignUpRequest {	
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
