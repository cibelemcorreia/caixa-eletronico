package br.com.bb.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Getter
@Setter
public class MovimentoRequestDto {

	@NotBlank(message = "O CPF ou CNPJ é obrigatório")
	private String cpfCnpj;

	@NotBlank(message = "O número da conta é obrigatório")
	private String numeroConta;

	@DecimalMin(value = "0.01", message = "Valor inválido")
	private BigDecimal valor;
}
