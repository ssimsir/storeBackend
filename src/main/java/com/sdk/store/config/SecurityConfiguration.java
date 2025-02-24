package com.sdk.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sdk.store.entities.Roles;
import com.sdk.store.services.security.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final UserService userService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			//.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll())
			.authorizeHttpRequests(request -> request.requestMatchers("/store/**", "/web/**", "/assets/**", "/API/v1/auth/**").permitAll())
			//.authorizeHttpRequests(request -> request.requestMatchers("/API/v1/products/**").permitAll())
			.authorizeHttpRequests(request -> request.requestMatchers("/API/v1/products/**").hasAnyAuthority(Roles.USER.getRole(), Roles.ADMIN.getRole(), Roles.PREMIUM_USER.getRole()))
			.authorizeHttpRequests(request -> request.anyRequest().authenticated())
					//.requestMatchers(HttpMethod.GET,"/posts").permitAll()				 					 												
			.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);		
		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {	
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userService.userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
		
	}
}
