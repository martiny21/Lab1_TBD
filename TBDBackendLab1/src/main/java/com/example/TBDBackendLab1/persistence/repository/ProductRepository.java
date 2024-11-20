package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.ProductEntity;

import java.util.List;

public interface ProductRepository {

    ProductEntity addProduct(ProductEntity product);
    ProductEntity getById(Long id);
    List<ProductEntity> getByCategory(Long id);

}
