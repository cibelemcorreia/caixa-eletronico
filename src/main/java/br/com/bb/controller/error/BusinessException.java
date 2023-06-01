package br.com.bb.controller.error;

public class BusinessException extends RuntimeException {
	public BusinessException(String message) {
		super(message);
	}
}