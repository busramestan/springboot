package com.busramestan.springboot.service.impl;

import com.busramestan.springboot.entity.Product;
import com.busramestan.springboot.repository.ProductRepository;
import com.busramestan.springboot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
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
    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product dbProduct = getProductById(id);

        dbProduct.setName(product.getName());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setPrice(product.getPrice());

        return productRepository.save(dbProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product dbProduct = getProductById(id);
        if (dbProduct != null){
            productRepository.delete(dbProduct);
        }
    }

    // Propagation örneği
    // Bu metod her çağrıldığında yeni bir transaction başlatır (REQUIRES_NEW).
    // Eğer ürün adı "error" ise bilinçli olarak hata fırlatılır ve sadece bu transaction rollback olur.
    // Böylece diğer işlemler etkilenmez
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createProductInNewTransaction(Product product) {
        // Bilerek hata olusturarak rollback'i gostermek icin
        if (product.getName().equals("error")) throw new RuntimeException("Bu ürün ismi girilemez!! ");
        productRepository.save(product);
    }
}
