package br.com.zaqueucavalcante.ecommercespringjava.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.DatabaseException;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		Instant timestamp = Instant.now();
		Integer status = HttpStatus.NOT_FOUND.value();
		String error = "Resource not found.";
		String message = e.getMessage();
		String path = request.getRequestURI();
		StandardError standardError = new StandardError(timestamp, status, error, message, path);
		return ResponseEntity.status(status).body(standardError);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseError(DatabaseException e, HttpServletRequest request) {
		Instant timestamp = Instant.now();
		Integer status = HttpStatus.BAD_REQUEST.value();
		String error = "Database error.";
		String message = e.getMessage();
		String path = request.getRequestURI();
		StandardError standardError = new StandardError(timestamp, status, error, message, path);
		return ResponseEntity.status(status).body(standardError);
	}
}
