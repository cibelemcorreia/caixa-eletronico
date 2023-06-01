package br.com.bb.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {BusinessException.class})
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ErrorInfo handleBusinessException(BusinessException ex){

		return new ErrorInfo("422", ex.getMessage());
	}

	@ExceptionHandler(value = {EntityNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo handleEntityNotFoundException(EntityNotFoundException ex){

		return new ErrorInfo("404", ex.getMessage());
	}

//	@ExceptionHandler(value = {HttpClientErrorException.class})
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ResponseBody
//	public ContaApiResponse handleClientErrorException(HttpClientErrorException ex){
//
//		return new ContaApiResponse("400", ex.getMessage());
//	}

}
