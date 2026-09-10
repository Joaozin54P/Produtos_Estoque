package com.example.estoqueproduto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.estoqueproduto.model.Produto;
import com.example.estoqueproduto.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarEstoque() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto nao encontrado"));
    }

    // precisa estar dentro de uma transacao para o lock pessimista
    // ser mantido ate o final da operacao
    @Transactional
    public Produto venderProduto(Long id, Integer quantidade) {
        Produto produto = produtoRepository.buscarComLockParaVenda(id)
                .orElseThrow(() -> new RuntimeException("Produto nao encontrado"));

        if (produto.getQuantidade() < quantidade) {
            throw new RuntimeException("Estoque insuficiente para realizar a venda");
        }

        produto.setQuantidade(produto.getQuantidade() - quantidade);

        return produtoRepository.save(produto);
    }
}
