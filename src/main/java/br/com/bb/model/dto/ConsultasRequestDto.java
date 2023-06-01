package br.com.bb.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ConsultasRequestDto {

	@NotBlank(message = "O CPF ou CNPJ é obrigatório.")
	private String cpfCnpj;

	@NotNull(message = "O número da conta é obrigatório.")
	private String numeroConta;

}