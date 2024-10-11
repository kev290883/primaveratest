package com.example.helloworld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.helloworld.messaging.RabbitMQSender;
import com.example.helloworld.model.Produto;
import com.example.helloworld.repository.ProdutoRepository;

@RestController
public class HelloWorldController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    // Endpoint para /hello
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World!";
    }

    // Novo endpoint para /welcome
    @GetMapping("/welcome")
    public String sayWelcome() {
        return "Welcome to Spring Boot!";
    }

    // Novo endpoint para listar todos os produtos
    @GetMapping("/produtos")
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    // Novo endpoint para obter um produto por ID
    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        return produtoRepository.findById(id)
            .map(produto -> ResponseEntity.ok().body(produto))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para criar um produto
    @PostMapping("/createProduct")
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        try {
          // 1. Envia o produto para a fila do RabbitMQ
          rabbitMQSender.send(produto);

          // 2. Retorna imediatamente ao cliente que o produto foi enviado para a fila
          return ResponseEntity.ok(produto);  
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
