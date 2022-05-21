package com.leovegas.exception;

import org.springframework.http.HttpStatus;

public class WalletException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	private HttpStatus httpStatus;

	public WalletException() {
		super();
	}

	public WalletException(String msg, HttpStatus status) {
		super();
		this.message = msg;
		this.httpStatus = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
