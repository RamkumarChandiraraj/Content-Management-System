package com.example.cms.utility;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.cms.userexception.BlogNotFoundByIdException;
import com.example.cms.userexception.BlogPostAlreadyExistByTitleException;
import com.example.cms.userexception.BlogPostNotFoundByIdException;
import com.example.cms.userexception.BlogTitleAlreadyExistException;
import com.example.cms.userexception.IllegalAccessRequestException;
import com.example.cms.userexception.InvalidPostStateException;
import com.example.cms.userexception.PanelNotFoundByIDException;
import com.example.cms.userexception.TimeDateNotVaidException;
import com.example.cms.userexception.TitleEmptyException;
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
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerBlogTitleAlreadyExist(BlogTitleAlreadyExistException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Title Already Exist");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerBlogNotFoundById(BlogNotFoundByIdException ex)
	{
		return errorResponse(HttpStatus.NOT_FOUND,ex.getMessage(),"Blog not found by the given Id");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerTitleEmppty(TitleEmptyException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Title is Empty");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerIllegalAccessRequest(IllegalAccessRequestException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Faild to add Contributor");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerPanelNotFoundByID(PanelNotFoundByIDException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Panel not found by the id");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerBlogPostAlreadyExistByTitle(BlogPostAlreadyExistByTitleException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"BlogPost Already Exists by the Title");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerBlogPostNotFoundById(BlogPostNotFoundByIdException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"BlogPost not found by the id");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerInvalidPostState(InvalidPostStateException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Posttype is in the Draft");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerIllegalArgument(IllegalArgumentException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Pass the existing postId and give correct publishRequest");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlerTimeDateNotVaid(TimeDateNotVaidException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Invalid Date and Time");
	}
}
