package com.camilo.productsmicroservice.exceptions;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotFoundException() {
	    super("Could not find product");
	}
}
