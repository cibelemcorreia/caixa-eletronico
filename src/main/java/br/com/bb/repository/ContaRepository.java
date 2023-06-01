package br.com.bb.repository;

import br.com.bb.model.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
	Optional<Conta> findByCpfCnpj(String cpfCnpj);

	Optional<Conta> findByCpfCnpjAndNumeroConta(String cpfCnpj, String numeroConta);

	Optional<Conta> findById(Long id);

}
