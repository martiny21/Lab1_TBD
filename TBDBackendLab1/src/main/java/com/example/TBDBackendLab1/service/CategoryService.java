package com.example.TBDBackendLab1.service;

import com.example.TBDBackendLab1.persistence.entity.CategoryEntity;
import com.example.TBDBackendLab1.persistence.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryEntity addCategory(CategoryEntity category){
        return categoryRepository.addCategory(category);
    }

    public CategoryEntity getById(Integer category_id){
        return categoryRepository.getById(category_id);
    }
}
