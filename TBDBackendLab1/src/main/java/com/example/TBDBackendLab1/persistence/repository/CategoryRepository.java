package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.CategoryEntity;

public interface CategoryRepository {

    CategoryEntity addCategory(CategoryEntity category);
    CategoryEntity getById(Integer categoryId);
}
