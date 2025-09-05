package com.busramestan.springboot.service.impl;

import com.busramestan.springboot.entity.Product;
import com.busramestan.springboot.repository.ProductRepository;
import com.busramestan.springboot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product dbPrdocut = getProductById(id);

        dbPrdocut.setName(product.getName());
        dbPrdocut.setDescription(product.getDescription());
        dbPrdocut.setPrice(product.getPrice());

        return productRepository.save(dbPrdocut);
    }

    @Override
    public void deleteProduct(Long id) {
        Product dbProduct = getProductById(id);
        if (dbProduct != null){
            productRepository.delete(dbProduct);
        }
    }
}
