package br.com.bb.error;

import br.com.bb.controller.error.ErrorResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ErrorResponseTest {

	@Test
	public void testErrorResponse() {
		String errorCode = "422";
		String errorMessage = "Unprocessable Entity";

		ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMessage);

		assertNotNull(errorResponse);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage());
	}

	@Test
	public void testErrorResponseCodeEMessageNulos() {
		String errorCode = null;
		String errorMessage = null;

		ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMessage);

		assertNotNull(errorResponse);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage());
	}

	@Test
	public void testErrorResponseCodeEMessageVazios() {
		String errorCode = "";
		String errorMessage = "";

		ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMessage);

		assertNotNull(errorResponse);
		assertEquals(errorCode, errorResponse.getCode());
		assertEquals(errorMessage, errorResponse.getMessage());
	}
}
