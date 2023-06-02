package br.com.bb.error;

import br.com.bb.controller.error.BusinessException;
import br.com.bb.controller.error.ErrorInfo;
import br.com.bb.controller.error.ErrorResponse;
import br.com.bb.controller.error.GlobalExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {
	@Mock
	private BusinessException businessException;

	@Mock
	private MethodArgumentNotValidException methodArgumentNotValidException;
	@Mock
	private BindingResult bindingResult;
	@Mock
	private FieldError fieldError;
	@Mock
	private EntityNotFoundException entityNotFoundException;
	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this); }

	@Test
	public void testHandleBusinessException() {
		String errorCode = "422";
		String errorMessage = "Unprocessable Entity";

		when(businessException.getMessage()).thenReturn(errorMessage);
		ErrorResponse errorResponse = globalExceptionHandler.handleBusinessException(businessException);
		assertNotNull(errorResponse);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage()); }

//	@Test
//	public void testHandleValidationException() {
//		String errorCode = "Erro de validação";
//		String errorMessage = "Erro de validação no campo field";
//		List<String> errorMessages = new ArrayList<>();
//		errorMessages.add(errorMessage);
//		when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
//		when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
//		when(fieldError.getDefaultMessage()).thenReturn(null);
//
//		when(fieldError.getField()).thenReturn("field");
//
//		ResponseEntity<ErrorInfo> responseEntity = globalExceptionHandler.handleValidationException(methodArgumentNotValidException);
//		assertNotNull(responseEntity);
//		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
//		ErrorInfo errorInfo = responseEntity.getBody();
//		assertNotNull(errorInfo);
//		assertEquals(errorCode, errorInfo.getMessage());
//		assertEquals(errorMessages, errorInfo.getErrors()); }

	@Test
	public void testHandleEntityNotFoundException() {
		String errorCode = "404"; String errorMessage = "Entity Not Found";
		when(entityNotFoundException.getMessage()).thenReturn(errorMessage);
		ErrorResponse errorResponse = globalExceptionHandler.handleEntityNotFoundException(entityNotFoundException);
		assertNotNull(errorResponse);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage()); }
}
