package com.leovegas.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WalletExceptionHandler {
	@ExceptionHandler(value = { WalletException.class })
	public ResponseEntity<Object> handleWalletException(WalletException e) {
		return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
	}

}
