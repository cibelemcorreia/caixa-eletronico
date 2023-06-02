package br.com.bb.services;

import br.com.bb.controller.error.BusinessException;
import br.com.bb.model.dto.ContaRequestDto;
import br.com.bb.model.dto.MovimentoRequestDto;
import br.com.bb.model.entity.Conta;
import br.com.bb.repository.ContaRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class ContaServiceTest {

	@Mock
	private ContaRepository contaRepository;

	@InjectMocks
	private ContaService contaService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCadastrarConta() {
		ContaRequestDto requestDto = new ContaRequestDto();
		requestDto.setNome("Luke Skywalker");
		requestDto.setCpfCnpj("00000000001");
		requestDto.setNumeroConta("123456");
		requestDto.setSaldo(BigDecimal.ZERO);

		Conta conta = new Conta();
		conta.setNome(requestDto.getNome());
		conta.setCpfCnpj(requestDto.getCpfCnpj());
		conta.setNumeroConta(requestDto.getNumeroConta());
		conta.setSaldo(requestDto.getSaldo());

		when(contaRepository.existsByCpfCnpj(anyString())).thenReturn(false);
		when(contaRepository.existsByCpfCnpjWithOrWithoutSeparators(anyString())).thenReturn(false);
		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta result = contaService.cadastrarConta(requestDto);

		assertNotNull(result);
		assertEquals(requestDto.getNome(), result.getNome());
		assertEquals(requestDto.getCpfCnpj(), result.getCpfCnpj());
		assertEquals(requestDto.getNumeroConta(), result.getNumeroConta());
		assertEquals(requestDto.getSaldo(), result.getSaldo());
		verify(contaRepository, times(1)).save(any(Conta.class));
	}

	@Test(expected = BusinessException.class)
	public void testCadastrarConta_DuplicateCpfCnpj() {
		ContaRequestDto requestDto = new ContaRequestDto();
		requestDto.setNome("Luke Skywalker");
		requestDto.setCpfCnpj("00000000001");
		requestDto.setNumeroConta("123456");
		requestDto.setSaldo(BigDecimal.ZERO);

		when(contaRepository.existsByCpfCnpj(anyString())).thenReturn(true);

		contaService.cadastrarConta(requestDto);
	}

	@Test
	public void testDepositarValor() {
		MovimentoRequestDto requestDto = new MovimentoRequestDto();
		requestDto.setCpfCnpj("00000000001");
		requestDto.setValor(BigDecimal.TEN);

		Conta conta = new Conta();
		conta.setSaldo(BigDecimal.ZERO);

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));
		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta result = contaService.realizarDeposito(requestDto);

		assertNotNull(result);
		assertEquals(BigDecimal.TEN, result.getSaldo());
		verify(contaRepository, times(1)).save(any(Conta.class));
	}

	@Test(expected = EntityNotFoundException.class)
	public void testDepositarValorContaNaoEncontrada() {
		MovimentoRequestDto requestDto = new MovimentoRequestDto();
		requestDto.setCpfCnpj("00000000001");
		requestDto.setValor(BigDecimal.TEN);

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.empty());

		contaService.realizarDeposito(requestDto);
	}

	@Test
	public void testSacarValor() {
		String cpfCnpj = "00000000001";
		String numeroConta = "123456";
		BigDecimal valor = BigDecimal.TEN;

		Conta conta = new Conta();
		conta.setSaldo(BigDecimal.TEN);

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));
		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta result = contaService.realizarSaque(cpfCnpj, numeroConta, valor);

		assertNotNull(result);
		assertEquals(BigDecimal.ZERO, result.getSaldo());
		verify(contaRepository, times(1)).save(any(Conta.class));
	}

	@Test(expected = EntityNotFoundException.class)
	public void testSacarContaNaoEncontrada() {
		String cpfCnpj = "00000000001";
		String numeroConta = "123456";
		BigDecimal valor = BigDecimal.TEN;

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.empty());

		contaService.realizarSaque(cpfCnpj, numeroConta, valor);
	}

	@Test(expected = BusinessException.class)
	public void testSacarSaldoIndisponivel() {
		String cpfCnpj = "00000000001";
		String numeroConta = "123456";
		BigDecimal valor = BigDecimal.TEN;

		Conta conta = new Conta();
		conta.setSaldo(BigDecimal.ONE);

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));
		contaService.realizarSaque(cpfCnpj, numeroConta, valor);
	}

	@Test
	public void testConsultarDados() {
		String cpfCnpj = "00000000001";
		String numeroConta = "123456";

		Conta conta = new Conta();
		conta.setNumeroConta(numeroConta);

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));

		Conta result = contaService.consultarDados(cpfCnpj, numeroConta);

		assertNotNull(result);
		assertEquals(conta, result);
	}

	@Test(expected = BusinessException.class)
	public void testConsultarDadosContaInvalida() {
		String cpfCnpj = "00000000001";
		String numeroConta = "654321";

		Conta conta = new Conta();
		conta.setNumeroConta("123456");

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));

		contaService.consultarDados(cpfCnpj, numeroConta);
	}

	@Test(expected = EntityNotFoundException.class)
	public void testConsultarDadosContaNaoEncontrada() {
		String cpfCnpj = "00000000001";
		String numeroConta = "123456";

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.empty());

		contaService.consultarDados(cpfCnpj, numeroConta);
	}

}