package com.RoleSecure.RoleSecure.Config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.RoleSecure.RoleSecure.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long expirationTime;

//	public String generateToken(User user) {
//		return Jwts.builder().setSubject(user.getUsername()) // Use user details to generate claims
//				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
//	}

	
	public String generateToken(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
	    String roles = populateAuthorities(authorities);

		String jwt=Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+86400000))
				.claim("email",auth.getName())
				.claim("authorities", roles)
				.signWith(key)
				.compact();
		return jwt;
		
	}

	public String getEmailFromJwtToken(String jwt) {
		jwt = jwt.substring(7);

		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email = String.valueOf(claims.get("email"));

		return email;
	}

	public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> auths = new HashSet<>();

		for (GrantedAuthority authority : collection) {
			auths.add(authority.getAuthority());
		}
		return String.join(",", auths);
	}

}
