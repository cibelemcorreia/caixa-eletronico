package br.com.bb.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ContaResponseDto {

	private Long id;

	private String nome;

	private String cpfCnpj;

	private String numeroConta;

	private BigDecimal saldo;

	public ContaResponseDto(Long id, String nome, String cpfCnpj, String numeroConta, BigDecimal saldo) {
		this.id = id;
		this.nome = nome;
		this.cpfCnpj = cpfCnpj;
		this.numeroConta = numeroConta;
		this.saldo = saldo;
	}

}
