package com.example.estoqueproduto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estoqueproduto.model.Produto;
import com.example.estoqueproduto.model.VendaRequest;
import com.example.estoqueproduto.service.ProdutoService;

@RestController
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private ProdutoService produtoService;

    // realizar a venda de um produto, dando baixa no estoque
    @PostMapping("/{id}")
    public ResponseEntity<?> venderProduto(@PathVariable Long id, @RequestBody VendaRequest venda) {
        try {
            Produto produto = produtoService.venderProduto(id, venda.getQuantidade());
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
