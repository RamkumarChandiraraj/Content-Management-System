package com.example.cms.utility;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.cms.userexception.UserAlreadyExistByEmailException;
import com.example.cms.userexception.UserNotFoundByIdException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
class ApplicationExceptionHandler {

	private ErrorStructure<String> structure;

	private ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status,String message,String rootCause)
	{
		return new ResponseEntity<ErrorStructure<String>>(structure.setStatus(status.value())
				.setMessage(message)
				.setRootCause(rootCause),status);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerUserAlreadyExistByEmail(UserAlreadyExistByEmailException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"User Already Exists with the given email Id");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerUserNotFoundById(UserNotFoundByIdException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"User not found by the given Id");
	}
}
