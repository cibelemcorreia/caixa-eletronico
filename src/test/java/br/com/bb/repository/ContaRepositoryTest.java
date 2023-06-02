package br.com.bb.repository;

import br.com.bb.model.entity.Conta;
import br.com.bb.services.ContaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ContaRepositoryTest {

	@Mock
	private ContaRepository contaRepository;

	@InjectMocks
	private ContaService contaService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindByCpfCnpj() {
		String cpfCnpj = "12345678900";
		Conta conta = new Conta();
		when(contaRepository.findByCpfCnpj(cpfCnpj)).thenReturn(Optional.of(conta));
		Optional<Conta> result = contaRepository.findByCpfCnpj(cpfCnpj);
		assertTrue(result.isPresent()); assertEquals(conta, result.get());
		verify(contaRepository, times(1)).findByCpfCnpj(cpfCnpj); }

	@Test
	public void testFindById() {
		Long id = 1L;
		Conta conta = new Conta();
		when(contaRepository.findById(id)).thenReturn(Optional.of(conta));

		Optional<Conta> result = contaRepository.findById(id);

		assertTrue(result.isPresent());
		assertEquals(conta, result.get());
		verify(contaRepository, times(1)).findById(id); }

	@Test
	public void testExistsByCpfCnpj() {
		String cpfCnpj = "12345678900";
		when(contaRepository.existsByCpfCnpj(cpfCnpj)).thenReturn(true);
		boolean result = contaRepository.existsByCpfCnpj(cpfCnpj);
		assertTrue(result);
		verify(contaRepository, times(1)).existsByCpfCnpj(cpfCnpj); }

	@Test
	public void testExistsByCpfCnpjWithOrWithoutSeparators() {
		String cpfCnpj = "123.456.789-00";
		when(contaRepository.existsByCpfCnpjWithOrWithoutSeparators(cpfCnpj)).thenReturn(true);
		boolean result = contaRepository.existsByCpfCnpjWithOrWithoutSeparators(cpfCnpj);
		assertTrue(result);
		verify(contaRepository, times(1)).existsByCpfCnpjWithOrWithoutSeparators(cpfCnpj); }
}
