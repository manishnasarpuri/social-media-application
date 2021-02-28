package com.org.social.media.exception;

public class GenericException extends Exception{
	private static final long serialVersionUID = 1L;
	public GenericException(String s) {
		super(s);
	}
	public GenericException(Throwable t) {
		super(t);
	}
}
