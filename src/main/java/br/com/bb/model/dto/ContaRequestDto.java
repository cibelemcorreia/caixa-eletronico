package br.com.bb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaRequestDto {
	private static final String NAME_VALIDATION = "^[\\p{L}]{2,}(?: [\\p{L}]+)*(?: [\\p{L}]{2,})?$";

	@NotBlank(message = "O campo nome é obrigatório")
	@Size(max = 100, message = "O campo nome tem limite de 100 caracteres")
	@Pattern(regexp = NAME_VALIDATION, message = "Formato inválido")
	private String nome;

	@NotBlank(message = "O CPF/CNPJ nome é obrigatório")
	private String cpfCnpj;

	@NotBlank(message = "O número da conta é obrigatório")
	private String numeroConta;

	@NotNull(message = "O saldo inicial é obrigatório")
	private BigDecimal saldo;
}
