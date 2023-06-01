package br.com.bb.repository;

import br.com.bb.model.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

	@Query("SELECT c FROM Conta c WHERE REPLACE(REPLACE(REPLACE(c.cpfCnpj, '.', ''), '-', ''), '/', '') = :cpfCnpj")
	Optional<Conta> findByCpfCnpj(@Param("cpfCnpj") String cpfCnpj);

	@Query("SELECT c FROM Conta c WHERE REPLACE(REPLACE(REPLACE(c.cpfCnpj, '.', ''), '-', ''), '/', '') = :cpfCnpj AND REPLACE(c.numeroConta, '-', '') = :numeroConta")
	Optional<Conta> findByCpfCnpjAndNumeroConta(@Param("cpfCnpj") String cpfCnpj, @Param("numeroConta") String numeroConta);

	Optional<Conta> findById(Long id);

}
