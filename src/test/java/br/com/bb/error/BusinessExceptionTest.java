package br.com.bb.error;

import br.com.bb.controller.error.BusinessException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class BusinessExceptionTest {

	@Test
	public void testBusinessException() {
		String errorMessage = "Error message";

		BusinessException exception = new BusinessException(errorMessage);

		assertNotNull(exception);
		assertEquals(errorMessage, exception.getMessage());
	}
}
