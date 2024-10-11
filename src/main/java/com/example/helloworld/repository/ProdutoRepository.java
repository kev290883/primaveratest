package com.example.helloworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.helloworld.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
