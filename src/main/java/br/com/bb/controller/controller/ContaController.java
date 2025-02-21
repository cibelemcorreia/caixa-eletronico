package br.com.bb.controller.controller;

import br.com.bb.model.dto.*;
import br.com.bb.model.entity.Conta;
import br.com.bb.services.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/atm")
@Validated
public class ContaController {

	@Autowired
	ContaService service;

	@PostMapping("/conta")
	public ResponseEntity<ContaResponseDto> cadastrarConta(@Valid @RequestBody ContaRequestDto requestDto) {
		Conta conta = service.cadastrarConta(requestDto);
		ContaResponseDto response = new ContaResponseDto(conta.getId(),
				conta.getNome(), conta.getCpfCnpj(),
				conta.getNumeroConta(), conta.getSaldo());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/conta/{id}")
	public ResponseEntity<ContaResponseDto> editarConta(@Valid @PathVariable Long id, @Valid @RequestBody ContaRequestDto requestDto) {
		Conta conta = service.editarConta(id, requestDto);
		ContaResponseDto response = new ContaResponseDto(conta.getId(), conta.getNome(),
				conta.getCpfCnpj(), conta.getNumeroConta(), conta.getSaldo());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/conta/deposito")
	public ResponseEntity<String> realizarDeposito(@Valid @RequestBody MovimentoRequestDto requestDto) {
		Conta conta = service.realizarDeposito(requestDto);
		return ResponseEntity.status(HttpStatus.OK).body("Depósito realizado com sucesso!");
	}

	@PostMapping("/conta/saque")
	public ResponseEntity<String> realizarSaque(@Valid @RequestBody MovimentoRequestDto requestDto) {
		Conta conta = service.realizarSaque(requestDto.getCpfCnpj(), requestDto.getNumeroConta(), requestDto.getValor());
		return ResponseEntity.status(HttpStatus.OK).body("Saque realizado com sucesso!");
	}

	@GetMapping("/conta/{cpfCnpj}/{numeroConta}")
	public ResponseEntity<ContaResponseDto> consultarDados(@PathVariable String cpfCnpj, @PathVariable String numeroConta) {
		Conta conta = service.consultarDados(cpfCnpj, numeroConta);
		ContaResponseDto response = new ContaResponseDto(conta.getId(), conta.getNome(),
				conta.getCpfCnpj(), conta.getNumeroConta(), conta.getSaldo());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/conta/saldo/{cpfCnpj}/{numeroConta}")
	public ResponseEntity<ConsultasResponseDto> consultarSaldo(@PathVariable String cpfCnpj, @PathVariable String numeroConta) {
		BigDecimal saldo = service.consultarSaldo(cpfCnpj, numeroConta);
		String saldoFormatado = "R$ " + saldo.toString();
		ConsultasResponseDto responseDto = new ConsultasResponseDto(saldoFormatado);
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

}
