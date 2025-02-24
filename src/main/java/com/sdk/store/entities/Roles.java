package com.sdk.store.entities;

public enum Roles {
	USER,
	PREMIUM_USER,
	ADMIN;
	
    public String getRole() {
        return this.name();
    }
}