package com.camilo.api.gateway.filters;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.camilo.api.gateway.exceptions.JwtTokenMalformedException;
import com.camilo.api.gateway.exceptions.JwtTokenMissingException;
import com.camilo.api.gateway.utils.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
@Slf4j
public class AuthenticationFilter implements GatewayFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

		final List<String> apiEndpoints = List.of("/register", "/login");
		final List<String> openSpecificEndpoints = List.of("products");

		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri)) 
				&& openSpecificEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().equals(uri));

		if (isApiSecured.test(request)) {
			if (!request.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				
				return response.setComplete();
			}

			final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

			try {
				jwtUtil.validateToken(token);
			} catch (JwtTokenMalformedException | JwtTokenMissingException e) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.BAD_REQUEST);
				log.error(e.getMessage());
				return response.setComplete();
			}

			Claims claims = jwtUtil.getClaims(token);
			exchange.getRequest().mutate().header("username", String.valueOf(claims.get("sub"))).build();
		}

		return chain.filter(exchange);
	}
}