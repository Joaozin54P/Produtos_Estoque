package com.example.estoqueproduto.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// JSON publicado na fila venda-events, lido pelo ProdutoCrudRabbitmq:
// {"produtoId":1,"quantidade":2}
// produtoId e o id do produto no ProdutoCrudRabbitmq
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendaEvent {

    private Long produtoId;
    private Integer quantidade;

    public VendaEvent() {
    }

    public VendaEvent(Long produtoId, Integer quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
