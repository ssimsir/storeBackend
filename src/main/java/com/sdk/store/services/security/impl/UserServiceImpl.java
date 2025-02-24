package com.sdk.store.services.security.impl;



import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sdk.store.entities.User;
import com.sdk.store.repository.UserRepository;
import com.sdk.store.services.security.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String userName) {
				return userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));}
		};
		
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User saveOneUser(User newUser) {
		return userRepository.save(newUser);
	}
	
	public User getOneUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}
	
	public User getOneUserByUserName(String userName) {
		return userRepository.findByEmail(userName).get();
	}

	public void deleteOneUser(Long userId) {
		userRepository.deleteById(userId);
	}

	public User updateOneUser(Long userId, User newUser) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User foundUser = user.get();
			foundUser.setEmail(newUser.getEmail());
			foundUser.setPassword(newUser.getPassword());
			userRepository.save(foundUser);
			return foundUser;
		} else
			return null;
	}



	
	
	

}
