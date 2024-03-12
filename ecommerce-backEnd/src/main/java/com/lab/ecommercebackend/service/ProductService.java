package com.lab.ecommercebackend.service;

import com.lab.ecommercebackend.dao.CrudDao;
import com.lab.ecommercebackend.model.Product;
import com.lab.ecommercebackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements CrudDao<Product, Long> {

    //private final EmailService emailService;

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void getByID(Long id) throws Exception {

        productRepository.findById(id).orElseThrow(() -> new Exception("Id not found!"));
    }

    public Page<Product> getPageable(Pageable pageable) {
        return null;
    }

    @Override
    public void create(Product product) {

        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void update(Long id, Product product) {

    }
}
