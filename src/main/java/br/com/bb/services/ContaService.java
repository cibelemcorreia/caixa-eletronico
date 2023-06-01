package br.com.bb.services;

import br.com.bb.exception.BusinessException;
import br.com.bb.model.dto.ContaRequestDto;
import br.com.bb.model.entity.Conta;
import br.com.bb.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ContaService {

	@Autowired
	ContaRepository repository;

	public Conta cadastrarConta(ContaRequestDto requestDto) {

		Optional<Conta> optional = repository.findByCpfCnpj(requestDto.getCpfCnpj());
		if (optional.isPresent()) {
			throw new BusinessException("CPF/CNPJ já cadastrado em nossa base de dados");
		}

		Conta conta = new Conta(
				requestDto.getNome(),
				requestDto.getCpfCnpj(),
				requestDto.getNumeroConta(),
				requestDto.getSaldo()
		);
		return repository.save(conta);
	}

	public Conta editarConta(Long contaId, ContaRequestDto requestDto) {
		Optional<Conta> optional = repository.findByCpfCnpj(requestDto.getCpfCnpj());
		if (optional.isPresent()) {
			throw new BusinessException("CPF/CNPJ já cadastrado em nossa base de dados");
		}

		Conta conta = repository.findById(contaId) .orElseThrow(() ->
				new EntityNotFoundException("Conta não encontrada."));
		conta.setNome(requestDto.getNome()); conta.setCpfCnpj(requestDto.getCpfCnpj());
		return repository.save(conta); }


	@Transactional
	public Conta depositar(@Valid String cpfCnpj, String numeroConta, BigDecimal valor) {
		Conta conta = repository.findByCpfCnpjAndNumeroConta(cpfCnpj, numeroConta)
				.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada."));

		if (valor.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("Valor inválido!");
		}

		BigDecimal novoSaldo = conta.getSaldo().add(valor);
		conta.setSaldo(novoSaldo);

		return repository.save(conta);
	}

	@Transactional
	public Conta sacar(@Valid String cpfCnpj, String numeroConta, BigDecimal valor) {
		Conta conta = repository.findByCpfCnpjAndNumeroConta(cpfCnpj, numeroConta)
				.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada."));

		if (valor.compareTo(BigDecimal.ZERO) < 0) {
			throw new BusinessException("Valor inválido!");
		}

		BigDecimal saldoAtual = conta.getSaldo();
		if (valor.compareTo(saldoAtual) > 0) {
			throw new BusinessException("Valor indisponível para saque!");
		} //PENSAR NESTA EXCEPTION

		BigDecimal novoSaldo = saldoAtual.subtract(valor);
		conta.setSaldo(novoSaldo);

		return repository.save(conta);
	}

	public Conta consultarConta(String cpfCnpj, String numeroConta) {
		Optional<Conta> optional = repository.findByCpfCnpjAndNumeroConta(cpfCnpj, numeroConta);

		if (optional.isPresent()) {
			Conta conta = optional.get();
			if (conta.getNumeroConta().equals(numeroConta)) {
				return conta;
			} else {
				throw new BusinessException("Número da conta inválido.");
			}
		} else {
			throw new EntityNotFoundException("Conta não encontrada.");
		}
	}

	public BigDecimal consultarSaldo(String cpfCnpj, String numeroConta) {
		Conta conta = repository.findByCpfCnpjAndNumeroConta(cpfCnpj, numeroConta)
				.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada."));

		return conta.getSaldo();
	}


}