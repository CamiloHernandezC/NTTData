package com.camilo.api.gateway.utils;

import com.camilo.api.gateway.exceptions.JwtTokenMalformedException;
import com.camilo.api.gateway.exceptions.JwtTokenMissingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	public Claims getClaims(final String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			return body;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}

	public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		} catch (SignatureException ex) {
			throw new JwtTokenMalformedException(ex.getMessage());
		} catch (MalformedJwtException ex) {
			throw new JwtTokenMalformedException(ex.getMessage());
		} catch (ExpiredJwtException ex) {
			throw new JwtTokenMalformedException(ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenMalformedException(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenMissingException("JWT claims string is empty.");
		}
	}

}