package br.com.bb.controller;

import br.com.bb.model.dto.*;
import br.com.bb.model.entity.Conta;
import br.com.bb.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
		Conta conta = service.depositar(requestDto.getCpfCnpj(), requestDto.getNumeroConta(), requestDto.getValor());
		return ResponseEntity.status(HttpStatus.OK).body("Depósito realizado com sucesso!");
	}

	@PostMapping("/conta/saque")
	public ResponseEntity<String> sacar(@Valid @RequestBody MovimentoRequestDto requestDto) {
		Conta conta = service.sacar(requestDto.getCpfCnpj(), requestDto.getNumeroConta(), requestDto.getValor());
		return ResponseEntity.status(HttpStatus.OK).body("Saque realizado com sucesso!");
	}

	@GetMapping("/conta")
	public ResponseEntity<ContaResponseDto> consultarConta(@Valid @RequestBody ConsultasRequestDto requestDto) {
		Conta conta = service.consultarConta(requestDto.getCpfCnpj(), requestDto.getNumeroConta());
		ContaResponseDto response = new ContaResponseDto(conta.getId(), conta.getNome(),
				conta.getCpfCnpj(), conta.getNumeroConta(), conta.getSaldo());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/conta/saldo")
	public ResponseEntity<ConsultasResponseDto> consultarSaldo(@Valid @RequestBody ConsultasRequestDto requestDto) {
		BigDecimal saldo = service.consultarSaldo(requestDto.getCpfCnpj(), requestDto.getNumeroConta());
		String saldoFormatado = "R$ " + saldo.toString();
		ConsultasResponseDto responseDto = new ConsultasResponseDto(saldoFormatado);
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

}
