package com.example.estoqueproduto.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.estoqueproduto.model.Produto;

@SpringBootTest
public class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Test
    void testarConcorrenciaComLock() throws Exception {

        // cria um produto novo so para esse teste, com 10 unidades
        Produto produto = produtoService.criarProduto(new Produto("Produto Teste Concorrencia", 10));
        Long produtoId = produto.getId();

        System.out.println("====================================");
        System.out.println("Estoque INICIAL na base: " + produto.getQuantidade());
        System.out.println("====================================");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // duas "vendas" disparadas ao mesmo tempo, cada uma tentando
        // descontar 7 unidades. Se nao tivesse o lock, as duas poderiam
        // ler "10 em estoque" ao mesmo tempo e as duas conseguiriam vender,
        // o que deixaria o estoque negativo
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                produtoService.venderProduto(produtoId, 4);
                return "Venda 1 concluida";
            } catch (Exception e) {
                return "Venda 1 falhou: " + e.getMessage();
            }
        }, executor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                produtoService.venderProduto(produtoId, 4);
                return "Venda 2 concluida";
            } catch (Exception e) {
                return "Venda 2 falhou: " + e.getMessage();
            }
        }, executor);

        System.out.println(future1.get());
        System.out.println(future2.get());

        executor.shutdown();

        Produto produtoFinal = produtoService.buscarPorId(produtoId);

        System.out.println("====================================");
        System.out.println("Estoque FINAL na base: " + produtoFinal.getQuantidade());
        System.out.println("====================================");
    }
}
