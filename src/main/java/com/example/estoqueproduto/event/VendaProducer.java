package com.example.estoqueproduto.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.estoqueproduto.config.RabbitMQConfig;

@Component
public class VendaProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarVenda(Long produtoId, Integer quantidade) {
        VendaEvent evento = new VendaEvent(produtoId, quantidade);
        rabbitTemplate.convertAndSend(RabbitMQConfig.FILA_VENDA_EVENTS, evento);
    }
}
