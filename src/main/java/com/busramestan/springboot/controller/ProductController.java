package com.busramestan.springboot.controller;

import com.busramestan.springboot.entity.Product;
import com.busramestan.springboot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping(path = "/save")
    public Product createProduct (@RequestBody Product product){
        return productService.createProduct(product);
    }

    @GetMapping(path = "/list")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping(path = "/by-id/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping(path = "update/{id}")
    public Product updateProduct(@PathVariable(name = "id") Long id, @RequestBody Product product) {
        return productService.updateProduct(id,product);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteProduct(@PathVariable(name = "id") Long id){
        productService.deleteProduct(id);
    }

    // Transaction test endpointi
    @PostMapping("/new-transaction")
    public String createWithNewTransaction(@RequestBody Product product) {
        try {
            productService.createProductInNewTransaction(product);
            return "Ürün ayrı bir transaction içinde başarıyla eklendi!";
        } catch (Exception e) {
            return "Hata oluştu: " + e.getMessage();
        }
    }
    // READ_COMMITTED testi endpointi
    @GetMapping("/read-committed/{id}")
    public Optional<Product> readCommittedTest(@PathVariable Long id) {
        return productService.testReadCommitted(id);
    }

    // REPEATABLE_READ testi endpointi
    @GetMapping("/repeatable-read/{id}")
    public Optional<Product> repeatableReadTest(@PathVariable Long id) {
        return productService.testRepeatableRead(id);
    }

}
