package br.com.bb.error;

import br.com.bb.controller.error.ErrorInfo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ErrorInfoTest {

	@Test
	public void testErrorInfo() {
		String errorMessage = "Error message";
		List<String> errors = Arrays.asList("Error 1", "Error 2");

		ErrorInfo errorInfo = new ErrorInfo(errorMessage, errors);

		assertNotNull(errorInfo);
		assertEquals(errorMessage, errorInfo.getMessage());
		assertEquals(errors, errorInfo.getErrors());
	}
}
