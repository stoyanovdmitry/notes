package com.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoAccessException extends RuntimeException {

	public NoAccessException() {
		super("Forbidden");
	}
}
