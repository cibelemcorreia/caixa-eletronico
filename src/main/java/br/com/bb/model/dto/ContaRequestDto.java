package br.com.bb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaRequestDto {

	private static final String NAME_VALIDATION = "^[\\p{L}]{2,}(?: [\\p{L}]+){1,6}$";
	//private static final String REQUIRED_NAME_FIELD = "O campo nome é obrigatório.";
	//private static final String REQUIRED_CPFCNPJ_FIELD = "O CPF/CNPJ nome é obrigatório.";
	// private static final String REQUIRED_NUMCONTA_FIELD = "O número da conta é obrigatório.";

	@NotBlank(message = "O campo nome é obrigatório.")
	@Size(max = 100, message = "Máximo 100 caracteres")
	@Pattern(regexp = NAME_VALIDATION, message = "Formato inválido.")
	private String nome;

	@NotBlank(message = "O CPF/CNPJ nome é obrigatório.")
	private String cpfCnpj;

	@NotBlank(message = "O número da conta é obrigatório.")
	private String numeroConta; //String para dar mais flexibilidade p/ diferentes formatos

	private BigDecimal saldo;
}
