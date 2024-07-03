package br.com.fiap.techchallenge.infra.persistence;

import br.com.fiap.techchallenge.infra.persistence.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoEntityRepository extends JpaRepository<ProdutoEntity, Long> {

    Optional<List<ProdutoEntity>> findAllByCategoriaEntityId(Integer idCategoria);

    Optional<List<ProdutoEntity>> findByNomeOrDescricaoOrPreco(String nome, String descricao, BigDecimal preco);

}