package com.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String username) {
		super("could not found user by this username: " + username);
	}

	public UserNotFoundException(int id) {
		super("could not found user by this id: " + id);
	}
}
