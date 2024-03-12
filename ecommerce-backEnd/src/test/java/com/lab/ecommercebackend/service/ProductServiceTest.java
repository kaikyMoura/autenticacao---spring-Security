package com.lab.ecommercebackend.service;

import com.lab.ecommercebackend.dao.CrudDao;
import com.lab.ecommercebackend.model.Product;
import com.lab.ecommercebackend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
public class ProductServiceTest implements CrudDao<Product, Long> {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    @Override
    public void create(Product product) {
        productRepository.save(product);
    }

    @Test
    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void update(Long aLong, Product product) {

    }

    @Override
    public void getByID(Long aLong) throws Exception {
    }

    @Test
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Page<Product> getPageable(Pageable pageable) {
        return null;
    }
}
