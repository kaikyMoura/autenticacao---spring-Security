package com.lab.ecommercebackend.controller;

import com.lab.ecommercebackend.dto.ProductDto;
import com.lab.ecommercebackend.model.Product;
import com.lab.ecommercebackend.repository.ProductRepository;
import com.lab.ecommercebackend.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    private final ProductRepository productRepository;

    private final ProductService produtoService;

    @Autowired
    public ProductControllerTest(ProductRepository productRepository, ProductService produtoService) {
        this.productRepository = productRepository;
        this.produtoService = produtoService;
    }

    @Test
    public void testCriarProduto() {
        when(productRepository.save(new Product())).thenReturn(new Product());

        var product = new ProductDto();

        product.setName("fadfas");
        product.setDescription("fasfsaffgagsagsagfsafafgasggaggasg");
        product.setPrice(1200.00);

        produtoService.create(new Product(product));

        System.out.println(product);
    }
}
