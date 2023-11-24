package com.camilo.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@Data
public class JWTProperties {
 
	@Value("${jwt.secret}")
	private String JWT_SECRET;
	@Value("${jwt.token.expiration_time}")
	private long JWT_TOKEN_EXPIRATION_TIME;
	@Value("${jwt.refresh_token.expiration_time}")
	private long JWT_REFRESH_TOKEN_EXPIRATION_TIME;

}
