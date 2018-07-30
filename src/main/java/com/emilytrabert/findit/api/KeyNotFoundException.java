package com.emilytrabert.findit.api;

public class KeyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public KeyNotFoundException(String message, Throwable t) {
		super(message, t);
	}

}
