package com.example.estoqueproduto.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.estoqueproduto.config.RabbitMQConfig;
import com.example.estoqueproduto.service.ProdutoService;

@Component
public class ProdutoEventListener {

    @Autowired
    private ProdutoService produtoService;

    // queues = nome da fila do RabbitMQ que este metodo escuta
    @RabbitListener(queues = RabbitMQConfig.FILA_PRODUTO_EVENTS)
    public void atualizarVenda(ProdutoEvent evento) {
        System.out.println("====================================");
        System.out.println("Estoque recebeu evento da fila: " + evento.getTipo());
        System.out.println("Produto: " + evento.getNome() + " (id " + evento.getProdutoId() + ")");
        System.out.println("====================================");

        produtoService.sincronizarProduto(evento);
    }
}
