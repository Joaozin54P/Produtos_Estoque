package com.example.estoqueproduto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.estoqueproduto.event.ProdutoEvent;
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

    // aplica no estoque o evento vindo do ProdutoCrudRabbitmq
    // (criar, atualizar ou deletar o produto), achando-o pelo produtoId
    @Transactional
    public void sincronizarProduto(ProdutoEvent evento) {
        if (evento.getTipo() == null || evento.getProdutoId() == null) {
            System.out.println("Evento ignorado: sem tipo ou produtoId");
            return;
        }

        Optional<Produto> existente = produtoRepository.findByProdutoId(evento.getProdutoId());

        switch (evento.getTipo()) {
            case "CRIADO", "ATUALIZADO" -> {
                Produto produto = existente.orElseGet(Produto::new);
                produto.setProdutoId(evento.getProdutoId());
                produto.setNome(evento.getNome());

                if (evento.getEstoque() != null) {
                    produto.setQuantidade(evento.getEstoque());
                } else if (produto.getQuantidade() == null) {
                    produto.setQuantidade(0);
                }

                produtoRepository.save(produto);
            }
            case "DELETADO" -> existente.ifPresent(produtoRepository::delete);
            default -> System.out.println("Evento ignorado: tipo desconhecido " + evento.getTipo());
        }
    }
}
