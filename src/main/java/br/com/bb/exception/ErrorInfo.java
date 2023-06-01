package br.com.bb.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorInfo {
	private String code;
	private String message;

	public ErrorInfo(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
