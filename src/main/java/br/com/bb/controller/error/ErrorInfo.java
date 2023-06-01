package br.com.bb.controller.error;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorInfo {
	private String message;
	private List<String> errors;

	public ErrorInfo(String message, List<String> errors) {
		this.message = message;
		this.errors = errors;
	}
}
