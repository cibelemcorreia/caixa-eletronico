import br.com.bb.controller.error.BusinessException;
import br.com.bb.model.dto.ContaRequestDto;
import br.com.bb.model.dto.MovimentoRequestDto;
import br.com.bb.model.entity.Conta;
import br.com.bb.repository.ContaRepository;
import br.com.bb.services.ContaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
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
		requestDto.setNome("John Doe");
		requestDto.setCpfCnpj("12345678900");
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
		requestDto.setNome("John Doe");
		requestDto.setCpfCnpj("12345678900");
		requestDto.setNumeroConta("123456");
		requestDto.setSaldo(BigDecimal.ZERO);

		when(contaRepository.existsByCpfCnpj(anyString())).thenReturn(true);

		contaService.cadastrarConta(requestDto);
	}

	@Test
	public void testDepositar() {
		MovimentoRequestDto requestDto = new MovimentoRequestDto();
		requestDto.setCpfCnpj("12345678900");
		requestDto.setValor(BigDecimal.TEN);

		Conta conta = new Conta();
		conta.setSaldo(BigDecimal.ZERO);

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));
		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta result = contaService.depositar(requestDto);

		assertNotNull(result);
		assertEquals(BigDecimal.TEN, result.getSaldo());
		verify(contaRepository, times(1)).save(any(Conta.class));
	}

	@Test(expected = EntityNotFoundException.class)
	public void testDepositar_ContaNotFound() {
		MovimentoRequestDto requestDto = new MovimentoRequestDto();
		requestDto.setCpfCnpj("12345678900");
		requestDto.setValor(BigDecimal.TEN);

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.empty());

		contaService.depositar(requestDto);
	}

//	@Test(expected = BusinessException.class)
//	public void testDepositar_InvalidValue() {
//		MovimentoRequestDto requestDto = new MovimentoRequestDto();
//		requestDto.setCpfCnpj("12345678900");
//		requestDto.setValor(BigDecimal.ZERO);
//
//		contaService.depositar(requestDto);
//	}

	@Test
	public void testSacar() {
		String cpfCnpj = "12345678900";
		String numeroConta = "123456";
		BigDecimal valor = BigDecimal.TEN;

		Conta conta = new Conta();
		conta.setSaldo(BigDecimal.TEN);

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));
		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta result = contaService.sacar(cpfCnpj, numeroConta, valor);

		assertNotNull(result);
		assertEquals(BigDecimal.ZERO, result.getSaldo());
		verify(contaRepository, times(1)).save(any(Conta.class));
	}

	@Test(expected = EntityNotFoundException.class)
	public void testSacar_ContaNotFound() {
		String cpfCnpj = "12345678900";
		String numeroConta = "123456";
		BigDecimal valor = BigDecimal.TEN;

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.empty());

		contaService.sacar(cpfCnpj, numeroConta, valor);
	}

//	@Test(expected = BusinessException.class)
//	public void testSacar_InvalidValue() {
//		String cpfCnpj = "12345678900";
//		String numeroConta = "123456";
//		BigDecimal valor = BigDecimal.ZERO;
//		contaService.sacar(cpfCnpj, numeroConta, valor);
//	}

	@Test(expected = BusinessException.class)
	public void testSacar_InsufficientBalance() {
		String cpfCnpj = "12345678900";
		String numeroConta = "123456";
		BigDecimal valor = BigDecimal.TEN;

		Conta conta = new Conta();
		conta.setSaldo(BigDecimal.ONE);

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));
		contaService.sacar(cpfCnpj, numeroConta, valor);
	}

	@Test
	public void testConsultarDados() {
		String cpfCnpj = "12345678900";
		String numeroConta = "123456";

		Conta conta = new Conta();
		conta.setNumeroConta(numeroConta);

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));

		Conta result = contaService.consultarDados(cpfCnpj, numeroConta);

		assertNotNull(result);
		assertEquals(conta, result);
	}

	@Test(expected = BusinessException.class)
	public void testConsultarDados_InvalidNumeroConta() {
		String cpfCnpj = "12345678900";
		String numeroConta = "654321";

		Conta conta = new Conta();
		conta.setNumeroConta("123456");

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));

		contaService.consultarDados(cpfCnpj, numeroConta);
	}

	@Test(expected = EntityNotFoundException.class)
	public void testConsultarDados_ContaNotFound() {
		String cpfCnpj = "12345678900";
		String numeroConta = "123456";

		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.empty());

		contaService.consultarDados(cpfCnpj, numeroConta);
	}

//	@Test
//	public void testConsultarSaldo() {
//		String cpfCnpj = "12345678900";
//
//		Conta conta = new Conta();
//		conta.setSaldo(BigDecimal.TEN);
//
//		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.of(conta));
//
//		BigDecimal result = contaService.consultarSaldo(cpfCnpj);
//
//		assertNotNull(result);
//		assertEquals(BigDecimal.TEN, result);
//	}

//	@Test(expected = EntityNotFoundException.class)
//	public void testConsultarSaldo_ContaNotFound() {
//		String cpfCnpj = "12345678900";
//
//		when(contaRepository.findByCpfCnpj(anyString())).thenReturn(Optional.empty());
//
//		contaService.consultarSaldo(cpfCnpj);
//	}
}