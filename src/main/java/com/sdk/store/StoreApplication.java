package com.sdk.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sdk.store.entities.Roles;
import com.sdk.store.entities.User;
import com.sdk.store.repository.UserRepository;

@SpringBootApplication
public class StoreApplication implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRoles(Roles.ADMIN);		
		if (null == adminAccount) {
			
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setRoles(Roles.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}

}
