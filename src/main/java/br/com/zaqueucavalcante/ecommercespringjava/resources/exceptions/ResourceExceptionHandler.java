package br.com.zaqueucavalcante.ecommercespringjava.resources.exceptions;

import java.time.Instant;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		Instant timestamp = Instant.now();
		Integer status = HttpStatus.UNPROCESSABLE_ENTITY.value();
		String error = "Validation error.";
		String message = e.getMessage();
		String path = request.getRequestURI();
		ValidationError validationError = new ValidationError(timestamp, status, error, message, path);
		setFieldMessageList(e, validationError);
		return ResponseEntity.status(status).body(validationError);
	}
	
	private void setFieldMessageList(MethodArgumentNotValidException e, ValidationError validationError) {
		List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
		FieldMessage fieldMessage;
		String fieldName;
		String message;
		for (FieldError fieldError : fieldErrorList) {
			fieldName = fieldError.getField();
			message = fieldError.getDefaultMessage();
			fieldMessage = new FieldMessage(fieldName, message);
			validationError.addFieldMessage(fieldMessage);
		}
	}
}
