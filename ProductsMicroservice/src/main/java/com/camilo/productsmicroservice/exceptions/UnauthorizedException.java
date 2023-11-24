package com.camilo.productsmicroservice.exceptions;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String reason) {
	    super("Your are not autorized to use this service " + reason);
	}
}
