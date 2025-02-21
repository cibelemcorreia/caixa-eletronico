package br.com.bb.error;

import br.com.bb.controller.error.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
