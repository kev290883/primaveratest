package com.example.helloworld.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.helloworld.model.Produto;
import com.example.helloworld.repository.ProdutoRepository;

@Component
public class RabbitMQConsumer {

    @Autowired
    private ProdutoRepository produtoRepository; // Serviço para salvar o produto no banco de dados

    @RabbitListener(queues = "productQueue")
    public void receive(Produto produto) {
        // Processa o produto e o salva no banco de dados
        produtoRepository.save(produto);
        System.out.println("Produto processado e salvo no banco: " + produto.getNome());
    }
}

