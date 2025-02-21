package br.com.bb.error;

import br.com.bb.controller.error.BusinessException;
import br.com.bb.controller.error.ErrorResponse;
import br.com.bb.controller.error.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {
	@Mock
	private BusinessException businessException;
	@Mock
	private EntityNotFoundException entityNotFoundException;
	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;

	@BeforeEach
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

	@Test
	public void testHandleEntityNotFoundException() {
		String errorCode = "404"; String errorMessage = "Entity Not Found";
		when(entityNotFoundException.getMessage()).thenReturn(errorMessage);
		ErrorResponse errorResponse = globalExceptionHandler.handleEntityNotFoundException(entityNotFoundException);
		assertNotNull(errorResponse);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage()); }
}
