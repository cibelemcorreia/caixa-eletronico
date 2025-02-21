package br.com.bb.repository;

import br.com.bb.model.entity.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ContaRepositoryTest {

	@Mock
	private ContaRepository contaRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindByCpfCnpj() {
		String cpfCnpj = "00000000001";
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
		String cpfCnpj = "00000000001";
		when(contaRepository.existsByCpfCnpj(cpfCnpj)).thenReturn(true);
		boolean result = contaRepository.existsByCpfCnpj(cpfCnpj);
		assertTrue(result);
		verify(contaRepository, times(1)).existsByCpfCnpj(cpfCnpj); }

	@Test
	public void testExistsByCpfCnpjWithOrWithoutSeparators() {
		String cpfCnpj = "000.000.000-01";
		when(contaRepository.existsByCpfCnpjWithOrWithoutSeparators(cpfCnpj)).thenReturn(true);
		boolean result = contaRepository.existsByCpfCnpjWithOrWithoutSeparators(cpfCnpj);
		assertTrue(result);
		verify(contaRepository, times(1)).existsByCpfCnpjWithOrWithoutSeparators(cpfCnpj); }
}
