package com.example.estoqueproduto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estoqueproduto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
