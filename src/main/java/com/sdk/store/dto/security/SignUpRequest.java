package com.sdk.store.dto.security;

import com.sdk.store.entities.Roles;

import lombok.Data;

@Data
public class SignUpRequest {	
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Roles roles;
}
