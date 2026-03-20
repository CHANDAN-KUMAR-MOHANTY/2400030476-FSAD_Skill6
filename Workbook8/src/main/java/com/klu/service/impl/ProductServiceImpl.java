package com.klu.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.klu.model.Product;
import com.klu.repo.ProductRepository;
import com.klu.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByPriceRange(double min, double max) {
        return productRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Product> getSortedProducts() {
        return productRepository.sortByPrice();
    }

    @Override
    public List<Product> getExpensiveProducts(double price) {
        return productRepository.findExpensiveProducts(price);
    }
}
