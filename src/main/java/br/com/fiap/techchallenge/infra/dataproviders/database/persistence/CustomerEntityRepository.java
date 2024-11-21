package br.com.fiap.techchallenge.infra.dataproviders.database.persistence;

import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findByCpf(String cpf);

    Optional<List<CustomerEntity>> findByEmailOrCpf(String email, String cpf);
}
