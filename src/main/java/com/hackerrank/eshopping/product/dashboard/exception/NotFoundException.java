package com.hackerrank.eshopping.product.dashboard.exception;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(Throwable throwable) {
		super(throwable);
	}

	public NotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public NotFoundException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return super.getMessage();
	}
}
