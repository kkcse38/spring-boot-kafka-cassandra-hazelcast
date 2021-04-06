package com.java.poc.cassandra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author kanhaiya kumar
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1066068284184232347L;

	public StudentNotFoundException(String message) {
		super(message, null, true, false);
	}

}
