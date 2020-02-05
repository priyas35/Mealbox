package com.mealbox.exception;

public class VendorNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public VendorNotFoundException(String message) {
		super(message);
	}
}
