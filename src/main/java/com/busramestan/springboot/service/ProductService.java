package com.busramestan.springboot.service;

import com.busramestan.springboot.dto.request.ProductRequest;
import com.busramestan.springboot.dto.response.ProductResponse;
import com.busramestan.springboot.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    ProductResponse createProductInNewTransaction(ProductRequest request);

    Optional<ProductResponse> testReadCommitted(Long id);

    Optional<ProductResponse> testRepeatableRead(Long id);


}
