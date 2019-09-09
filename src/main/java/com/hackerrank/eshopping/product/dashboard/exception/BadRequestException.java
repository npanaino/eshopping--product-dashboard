package com.hackerrank.eshopping.product.dashboard.exception;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	// private BadRequest badRequest;

	public BadRequestException(Throwable throwable) {
		super(throwable);
	}

	public BadRequestException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public BadRequestException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return super.getMessage();
	}
}
