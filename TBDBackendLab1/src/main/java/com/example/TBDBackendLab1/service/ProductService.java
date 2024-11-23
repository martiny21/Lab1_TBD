package com.example.TBDBackendLab1.service;

import com.example.TBDBackendLab1.persistence.entity.ProductEntity;
import com.example.TBDBackendLab1.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductEntity addProduct(ProductEntity product){
        return productRepository.addProduct(product);
    }

    public ProductEntity getById(Integer product_id){
        return productRepository.getById(product_id);
    }

    public List<ProductEntity> getByCategoryId(Integer category_id){
        return productRepository.getByCategory(category_id);
    }
    public List<ProductEntity> getAll(){
        return productRepository.getAll_P();
    }
}
