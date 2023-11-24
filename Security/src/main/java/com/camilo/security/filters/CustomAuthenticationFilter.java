package com.camilo.security.filters;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final String JWT_SECRET;
	private final long JWT_TOKEN_EXPIRATION_TIME;
	private final long JIT_REFRESH_TOKEN_EXPIRATION_TIME;
	
	private final AuthenticationManager authenticationManager;
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager, String jWT_SECRET, long jWT_TOKEN_EXPIRATION_TIME,
			long jWT_REFRESH_TOKEN_EXPIRATION_TIME) {
		JWT_SECRET = jWT_SECRET;
		JWT_TOKEN_EXPIRATION_TIME = jWT_TOKEN_EXPIRATION_TIME;
		JIT_REFRESH_TOKEN_EXPIRATION_TIME = jWT_REFRESH_TOKEN_EXPIRATION_TIME;
		this.authenticationManager = authenticationManager;
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
		String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user =  (User) authResult.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
		String access_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRATION_TIME))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
		String refresh_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JIT_REFRESH_TOKEN_EXPIRATION_TIME))
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
		Map<String, String> tokens = new HashMap<String, String>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.unsuccessfulAuthentication(request, response, failed);
	}

}
