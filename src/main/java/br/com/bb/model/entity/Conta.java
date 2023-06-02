package br.com.bb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "conta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	private String nome;
	@Column(name = "cpfCnpj")
	private String cpfCnpj;

	@Column(name = "numeroConta")
	private String numeroConta;

	@Column(name = "saldoInicial")
	private BigDecimal saldo;

	public Conta(String nome, String cpfCnpj, String numeroConta, BigDecimal saldo) {
		this.nome = nome;
		this.cpfCnpj = cpfCnpj;
		this.numeroConta = numeroConta;
		this.saldo = saldo;
	}
}