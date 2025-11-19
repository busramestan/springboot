package com.busramestan.springboot.service.impl;

import com.busramestan.springboot.dto.request.ProductRequest;
import com.busramestan.springboot.dto.response.ProductResponse;
import com.busramestan.springboot.entity.Product;
import com.busramestan.springboot.repository.ProductRepository;
import com.busramestan.springboot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = modelMapper.map(request, Product.class);
        Product savedProduct = productRepository.save(product);
        logger.info("Saved product :");
        return modelMapper.map(savedProduct,ProductResponse.class);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product =productRepository.findById(id).orElse(null);
        if (product == null) return null;
        return modelMapper.map(product,ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product,ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product dbProduct = productRepository.findById(id).orElse(null);
        if (dbProduct == null) return null;

        dbProduct.setName(request.getName());
        dbProduct.setDescription(request.getDescription());
        dbProduct.setPrice(request.getPrice());

        Product updatedProduct = productRepository.save(dbProduct);
        return modelMapper.map(updatedProduct,ProductResponse.class);

    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product dbProduct = productRepository.findById(id).orElse(null);
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
    public ProductResponse createProductInNewTransaction(ProductRequest request) {
        Product product = modelMapper.map(request,Product.class);
        // Bilerek hata olusturarak rollback'i gostermek icin
        if (product.getName().equals("error")) throw new RuntimeException("Bu ürün ismi girilemez!! ");
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductResponse.class);

    }

    // READ_COMMITTED isolation testi
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<ProductResponse> testReadCommitted(Long id) {
        return productRepository.findById(id).map(product -> modelMapper.map(product, ProductResponse.class));
    }

    // REPEATABLE_READ isolation testi
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Optional<ProductResponse> testRepeatableRead(Long id) {
        Product firstRead = productRepository.findById(id).orElse(null);
        Product secondRead = productRepository.findById(id).orElse(null);
        System.out.println("First Read: " + firstRead.getPrice());
        System.out.println("Second Read: " + secondRead.getPrice());
        return Optional.ofNullable(firstRead)
                .map(product -> modelMapper.map(product, ProductResponse.class));
    }
}
