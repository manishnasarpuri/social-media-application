package com.org.social.media.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.org.social.media.domain.error.ErrorResponse;
import com.org.social.media.util.ResposeUtil;

@ControllerAdvice
public class GenericExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<Object> handleException(GenericException exception){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResposeUtil.createResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), 
				"Internal Server Error"));
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleException(MethodArgumentNotValidException exception){
		List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
		List<String> errors=new ArrayList<String>(); 
		allErrors.forEach(error->errors.add(error.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResposeUtil.createResponse(HttpStatus.BAD_REQUEST.name(), 
				errors));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(UserNotFoundException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResposeUtil.createResponse(HttpStatus.NOT_FOUND.name(), exception.getMessage()));
	}
}
