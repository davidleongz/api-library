package com.david.library.exception;

public class BookNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BookNotFoundException(final String exception) {

		super(exception);
	}

}
