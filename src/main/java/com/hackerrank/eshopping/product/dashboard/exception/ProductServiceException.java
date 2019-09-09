package com.hackerrank.eshopping.product.dashboard.exception;

public class ProductServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	// private BadRequest badRequest;

	public ProductServiceException(Throwable throwable) {
		super(throwable);
	}

	public ProductServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ProductServiceException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return super.getMessage();
	}
}
