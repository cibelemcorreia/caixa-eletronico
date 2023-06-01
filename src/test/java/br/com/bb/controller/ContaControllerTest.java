package br.com.bb.controller;

import br.com.bb.model.dto.*;
import br.com.bb.model.entity.Conta;
import br.com.bb.services.ContaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContaControllerTest {

	@Mock
	private ContaService contaService;

	@InjectMocks
	private ContaController contaController;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void cadastrarContaComSucesso() {
		ContaRequestDto requestDto = new ContaRequestDto();
		Conta conta = new Conta();
		when(contaService.cadastrarConta(requestDto)).thenReturn(conta);

		ResponseEntity<ContaResponseDto> responseEntity = contaController.cadastrarConta(requestDto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ContaResponseDto responseDto = responseEntity.getBody();

		verify(contaService, times(1)).cadastrarConta(requestDto);
	}

	@Test
	public void editarContaComSucesso() {
		Long id = 1L;
		ContaRequestDto requestDto = new ContaRequestDto();
		Conta conta = new Conta();
		when(contaService.editarConta(id, requestDto)).thenReturn(conta);

		ResponseEntity<ContaResponseDto> responseEntity = contaController.editarConta(id, requestDto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ContaResponseDto responseDto = responseEntity.getBody();

		verify(contaService, times(1)).editarConta(id, requestDto); }

	@Test
	public void depositarComSucesso() {
		// Mocking input data
		MovimentoRequestDto requestDto = new MovimentoRequestDto();
		Conta conta = new Conta();
		when(contaService.depositar(requestDto.getCpfCnpj(), requestDto.getNumeroConta(), requestDto.getValor())).thenReturn(conta);

		ResponseEntity<String> responseEntity = contaController.realizarDeposito(requestDto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Depósito realizado com sucesso!", responseEntity.getBody());

		verify(contaService, times(1)).depositar(requestDto.getCpfCnpj(), requestDto.getNumeroConta(), requestDto.getValor()); }

	@Test
	public void sacar_ValidRequest_ReturnsSuccessMessage() {
		MovimentoRequestDto requestDto = new MovimentoRequestDto();
		Conta conta = new Conta();
		when(contaService.sacar(requestDto.getCpfCnpj(), requestDto.getNumeroConta(),
				requestDto.getValor())).thenReturn(conta);

		ResponseEntity<String> responseEntity = contaController.sacar(requestDto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Saque realizado com sucesso!", responseEntity.getBody());

		verify(contaService, times(1)).sacar(requestDto.getCpfCnpj(),
				requestDto.getNumeroConta(), requestDto.getValor()); }

	@Test
	public void consultarDadosContaComSucesso() {
		ConsultasRequestDto requestDto = new ConsultasRequestDto();
		Conta conta = new Conta();
		when(contaService.consultarConta(requestDto.getCpfCnpj(), requestDto.getNumeroConta())).thenReturn(conta);

		ResponseEntity<ContaResponseDto> responseEntity = contaController.consultarConta(requestDto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); ContaResponseDto responseDto = responseEntity.getBody();

		verify(contaService, times(1)).consultarConta(requestDto.getCpfCnpj(), requestDto.getNumeroConta()); }

	@Test
	public void consultarSaldoComSucesso() {
		ConsultasRequestDto requestDto = new ConsultasRequestDto();
		BigDecimal saldo = new BigDecimal("1000.00");
		when(contaService.consultarSaldo(requestDto.getCpfCnpj(), requestDto.getNumeroConta())).thenReturn(saldo);

		ResponseEntity<ConsultasResponseDto> responseEntity = contaController.consultarSaldo(requestDto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ConsultasResponseDto responseDto = responseEntity.getBody();

		verify(contaService, times(1)).consultarSaldo(requestDto.getCpfCnpj(),
				requestDto.getNumeroConta()); }

}
