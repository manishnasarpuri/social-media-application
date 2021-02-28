package com.org.social.media.exception;

public class UserNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	public UserNotFoundException(String s) {
		super(s);
	}
	public UserNotFoundException(Throwable t) {
		super(t);
	}
}
