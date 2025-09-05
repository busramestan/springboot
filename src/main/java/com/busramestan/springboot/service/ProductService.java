package com.busramestan.springboot.service;

import com.busramestan.springboot.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    void createProductInNewTransaction(Product product);

}
