package com.sdk.store.services.security.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

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
	
	private String jwtSigningKey = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24))//1000*60*24 proposties fileye alınacak
				.signWith(getSiginKey(), SignatureAlgorithm.HS256  )
				.compact();
	}
	
	public String generateRefreshToken(Map<String, Object>extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+604800000))//604800000 proposties fileye alınacak
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
	
	private Key getSiginKey() {
		byte[] key=Decoders.BASE64.decode(jwtSigningKey);   // "_" olmayacaksifre_key proposties fileye alınacak
		return Keys.hmacShaKeyFor(key);
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