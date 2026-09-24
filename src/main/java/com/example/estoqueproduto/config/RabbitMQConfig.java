package com.example.estoqueproduto.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // mesmo nome e mesmos parametros da fila declarada no ProdutoCrudRabbitmq
    // (durable = true). Se um dos lados declarar diferente, o RabbitMQ recusa
    // com PRECONDITION_FAILED
    public static final String FILA_PRODUTO_EVENTS = "produto-events";

    // Estoque -> Produto (venda realizada)
    public static final String FILA_VENDA_EVENTS = "venda-events";

    @Bean
    public Queue filaProdutoEvents() {
        return new Queue(FILA_PRODUTO_EVENTS, true);
    }

    @Bean
    public Queue filaVendaEvents() {
        return new Queue(FILA_VENDA_EVENTS, true);
    }

    // converte a mensagem JSON que chega da fila para o objeto ProdutoEvent
    // (e vice-versa), igual ao conversor do ProdutoCrudRabbitmq
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
