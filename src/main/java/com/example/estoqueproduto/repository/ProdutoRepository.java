package com.example.estoqueproduto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

import com.example.estoqueproduto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // busca o produto travando a linha no banco ate a transacao terminar,
    // assim duas vendas do mesmo produto ao mesmo tempo nao conseguem
    // ler a mesma quantidade e furar o estoque
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Produto p where p.id = :id")
    Optional<Produto> buscarComLockParaVenda(@Param("id") Long id);
}
