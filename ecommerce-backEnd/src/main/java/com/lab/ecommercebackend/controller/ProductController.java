package com.lab.ecommercebackend.controller;

import com.lab.ecommercebackend.model.Product;
import com.lab.ecommercebackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService produtoService;

    @Autowired
    public ProductController(ProductService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok().body(produtoService.getAll());
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Void> createProduct(@RequestBody Product product) {
        produtoService.create(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
