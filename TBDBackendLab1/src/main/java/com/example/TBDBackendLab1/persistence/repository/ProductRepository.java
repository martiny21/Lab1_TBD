package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.ProductEntity;

import java.util.List;

public interface ProductRepository {

    ProductEntity addProduct(ProductEntity product);
    ProductEntity getById(Integer product_id);
    List<ProductEntity> getByCategory(Integer category_id);
    List<ProductEntity> getAll_P();

}
