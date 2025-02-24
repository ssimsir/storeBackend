package com.sdk.store.services.security;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sdk.store.entities.User;


public interface UserService {

	public UserDetailsService userDetailsService();

	public List<User> getAllUsers();

	public User saveOneUser(User newUser);
	
	public User getOneUserById(Long userId);
	
	public User getOneUserByUserName(String userName);
	public void deleteOneUser(Long userId);

	public User updateOneUser(Long userId, User newUser);



	
	
	

}
