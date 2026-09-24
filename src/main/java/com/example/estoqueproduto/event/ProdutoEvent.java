package com.example.estoqueproduto.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// mesmo JSON publicado pelo ProdutoCrudRabbitmq:
// {"tipo":"CRIADO","produtoId":1,"nome":"Teclado mecanico","estoque":15}
// tipo pode ser CRIADO, ATUALIZADO ou DELETADO
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoEvent {

    private String tipo;
    private Long produtoId;
    private String nome;
    private Integer estoque;

    public ProdutoEvent() {
    }

    public ProdutoEvent(String tipo, Long produtoId, String nome, Integer estoque) {
        this.tipo = tipo;
        this.produtoId = produtoId;
        this.nome = nome;
        this.estoque = estoque;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }
}
