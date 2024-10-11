package com.example.helloworld.messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.helloworld.model.Produto;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private String exchange = "productExchange";  // Definido na configuração
    private String routingKey = "product.create";

    public void send(Produto produto) {
        amqpTemplate.convertAndSend(exchange, routingKey, produto);
        System.out.println("Produto enviado para RabbitMQ: " + produto.getNome());
    }
}
