package br.com.bb.services;

import br.com.bb.controller.error.BusinessException;
import br.com.bb.model.dto.ContaRequestDto;
import br.com.bb.model.dto.MovimentoRequestDto;
import br.com.bb.model.entity.Conta;
import br.com.bb.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
		String cpfCnpj = normalizeCpfCnpj(requestDto.getCpfCnpj());
		verifyCpfCnpjNotDuplicated(cpfCnpj);
		Conta conta = new Conta( requestDto.getNome(), cpfCnpj, requestDto.getNumeroConta(),
				requestDto.getSaldo() );

		return repository.save(conta);
	}
	private void verifyCpfCnpjNotDuplicated(String cpfCnpj) {
		if (repository.existsByCpfCnpj(cpfCnpj) ||
				repository.existsByCpfCnpjWithOrWithoutSeparators(cpfCnpj)) {
		throw new BusinessException("CPF/CNPJ já cadastrado em nossa base de dados"); }
	}

	private String normalizeCpfCnpj(String cpfCnpj){
		return cpfCnpj.replaceAll("[./-]", "");
	}

	public Conta editarConta(Long id, ContaRequestDto requestDto) {
		String cpfCnpj = normalizeCpfCnpj(requestDto.getCpfCnpj());

		verifyCpfCnpjNotDuplicated(cpfCnpj, id);

		Conta conta = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
		conta.setNome(requestDto.getNome());
		conta.setCpfCnpj(cpfCnpj);

		return repository.save(conta);
	}

	private void verifyCpfCnpjNotDuplicated(String cpfCnpj, Long id) {
		Optional<Conta> optional = repository.findByCpfCnpj(cpfCnpj);
		if (optional.isPresent()) {
			Conta existingConta = optional.get();
			if (!existingConta.getId().equals(id)) {
				throw new BusinessException("CPF/CNPJ já cadastrado em nossa base de dados");
			}
		}
	}

	@Transactional
	public Conta depositar(@Valid @RequestBody MovimentoRequestDto requestDto) {
		Optional<Conta> optionalConta = repository.findByCpfCnpj(requestDto.getCpfCnpj());
		if(!optionalConta.isPresent()){
			throw new EntityNotFoundException("Conta não encontrada");
		}

		BigDecimal valor = requestDto.getValor();

		if (valor.compareTo(BigDecimal.ZERO) <= 0.00) {
			throw new BusinessException("Valor inválido!");
		}

		Conta conta = optionalConta.get();
		BigDecimal novoSaldo = conta.getSaldo().add(valor);
		conta.setSaldo(novoSaldo);

		return repository.save(conta);
	}

	@Transactional
	public Conta sacar(@Valid String cpfCnpj, String numeroConta, BigDecimal valor) {
		Conta conta = repository.findByCpfCnpj(cpfCnpj)
				.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

		if (valor.compareTo(BigDecimal.ZERO) <= 0.00) {
			throw new BusinessException("Valor inválido!");
		}

		BigDecimal saldoAtual = conta.getSaldo();
		if (valor.compareTo(saldoAtual) > 0) {
			throw new BusinessException("Valor indisponível para saque!");
		}

		BigDecimal novoSaldo = saldoAtual.subtract(valor);
		conta.setSaldo(novoSaldo);

		return repository.save(conta);
	}

	public Conta consultarDados(String cpfCnpj, String numeroConta) {

		Optional<Conta> optional = repository.findByCpfCnpj(cpfCnpj);

		if (optional.isPresent()) {
			Conta conta = optional.get();
			if (conta.getNumeroConta().equals(numeroConta)) {
				return conta;
			} else {
				throw new BusinessException("Número da conta inválido");
			}
		} else {
			throw new EntityNotFoundException("Conta não encontrada");
		}
	}

	public BigDecimal consultarSaldo(String cpfCnpj, String numeroConta) {
		Conta conta = repository.findByCpfCnpj(cpfCnpj)
				.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada."));
		return conta.getSaldo();
	}


}