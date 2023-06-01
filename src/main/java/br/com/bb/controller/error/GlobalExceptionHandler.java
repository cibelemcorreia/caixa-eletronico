package br.com.bb.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {BusinessException.class})
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ErrorResponse handleBusinessException(BusinessException ex){

		return new ErrorResponse("422", ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<ErrorInfo> handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<String> errorMessages = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String errorMessage = fieldError.getDefaultMessage();
			if (errorMessage == null || errorMessage.isEmpty()) {
				errorMessage = "Erro de validação no campo " + fieldError.getField();
			}
			errorMessages.add(errorMessage);
		}

		ErrorInfo errorResponse = new ErrorInfo("Erro de validação", errorMessages);
		return ResponseEntity.unprocessableEntity().body(errorResponse);
	}

	@ExceptionHandler(value = {EntityNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleEntityNotFoundException(EntityNotFoundException ex){

		return new ErrorResponse("404", ex.getMessage());
	}


}
