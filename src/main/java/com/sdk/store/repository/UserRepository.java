package com.sdk.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdk.store.entities.Roles;
import com.sdk.store.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	Optional<User> findByUserName(String userName);
	
	
	User  findByRoles(Roles roles);
}
