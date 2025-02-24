package com.sdk.store.services.security.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sdk.store.services.security.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {
	
    @Value("${store.jwt.secret.key}")
    private String JWT_SECRET_KEY;
    
    @Value("${store.jwt.expires.token}")
    private Long JWT_EXPIRES_TOKEN;
    
    @Value("${store.jwt.expires.refresh.token}")
    private Long JWT_EXPIRES_REFRESH_TOKEN;
	
	private Key getSiginKey() {
		byte[] key=Decoders.BASE64.decode(JWT_SECRET_KEY);
		return Keys.hmacShaKeyFor(key);
	}
	
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + JWT_EXPIRES_TOKEN))
				.signWith(getSiginKey(), SignatureAlgorithm.HS256  )
				.compact();
	}
	
	public String generateRefreshToken(Map<String, Object>extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+JWT_EXPIRES_REFRESH_TOKEN))
				.signWith(getSiginKey(), SignatureAlgorithm.HS256  )
				.compact();
	}
	
	public String extractUserName(String token) {
		return exractClaim(token, Claims::getSubject);
	}
	
	private <T> T exractClaim(String token, Function<Claims, T> claimResolvers) {
		final Claims claims = exractAllClaims(token);
		return claimResolvers.apply(claims);
		
	}
	
	
	private Claims exractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return exractClaim(token, Claims::getExpiration).before(new Date());
	}

}