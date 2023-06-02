package br.com.bb.controller;

import br.com.bb.controller.controller.ContaController;
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
	public void testCadastrarContaComSucesso() {
		ContaRequestDto requestDto = new ContaRequestDto();
		Conta conta = new Conta();
		when(contaService.cadastrarConta(requestDto)).thenReturn(conta);

		ResponseEntity<ContaResponseDto> responseEntity = contaController.cadastrarConta(requestDto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		verify(contaService, times(1)).cadastrarConta(requestDto);
	}

	@Test
	public void testEditarContaComSucesso() {
		Long id = 1L;
		ContaRequestDto requestDto = new ContaRequestDto();
		Conta conta = new Conta();
		when(contaService.editarConta(id, requestDto)).thenReturn(conta);

		ResponseEntity<ContaResponseDto> responseEntity = contaController.editarConta(id, requestDto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		verify(contaService, times(1)).editarConta(id, requestDto); }

	@Test
	public void testDepositarValorComSucesso() {
		MovimentoRequestDto requestDto = new MovimentoRequestDto();
		Conta conta = new Conta();
		when(contaService.realizarDeposito(requestDto)).thenReturn(conta);

		ResponseEntity<String> responseEntity = contaController.realizarDeposito(requestDto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Depósito realizado com sucesso!", responseEntity.getBody());
		verify(contaService, times(1)).realizarDeposito(requestDto); }
	@Test
	public void testSacarValorComSucesso() {
		MovimentoRequestDto requestDto = new MovimentoRequestDto();
		Conta conta = new Conta();
		when(contaService.realizarSaque(requestDto.getCpfCnpj(), requestDto.getNumeroConta(),
				requestDto.getValor())).thenReturn(conta);

		ResponseEntity<String> responseEntity = contaController.realizarSaque(requestDto);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Saque realizado com sucesso!", responseEntity.getBody());

		verify(contaService, times(1)).realizarSaque(requestDto.getCpfCnpj(),
				requestDto.getNumeroConta(), requestDto.getValor()); }

	@Test
	public void testConsultarDadosComSucesso() {
		String cpfCnpj = "00000000001";
		String numeroConta = "123456";
		Conta conta = new Conta();
		conta.setId(1L); conta.setNome("Luke Skywalker");
		conta.setCpfCnpj(cpfCnpj); conta.setNumeroConta(numeroConta);
		conta.setSaldo(new BigDecimal("1000.00"));
		when(contaService.consultarDados(cpfCnpj, numeroConta)).thenReturn(conta);

		ResponseEntity<ContaResponseDto> responseEntity = contaController.consultarDados(cpfCnpj, numeroConta);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ContaResponseDto responseDto = responseEntity.getBody();
		assertEquals(1L, responseDto.getId().longValue());
		assertEquals("Luke Skywalker", responseDto.getNome());
		assertEquals(cpfCnpj, responseDto.getCpfCnpj());
		assertEquals(numeroConta, responseDto.getNumeroConta());
		assertEquals(new BigDecimal("1000.00"), responseDto.getSaldo());

		verify(contaService, times(1)).consultarDados(cpfCnpj, numeroConta); }

	@Test
	public void testConsultarSaldoComSucesso() {
		String cpfCnpj = "00000000001";
		String numeroConta = "123456";
		BigDecimal saldo = new BigDecimal("1000.00");
		when(contaService.consultarSaldo(cpfCnpj, numeroConta)).thenReturn(saldo);

		ResponseEntity<ConsultasResponseDto> responseEntity = contaController.consultarSaldo(cpfCnpj, numeroConta);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ConsultasResponseDto responseDto = responseEntity.getBody();
		assertEquals("R$ 1000.00", responseDto.getSaldo());

		verify(contaService, times(1)).consultarSaldo(cpfCnpj, numeroConta);
	}

}
