package com.example.TBDBackendLab1.controller;

import com.example.TBDBackendLab1.persistence.entity.CategoryEntity;
import com.example.TBDBackendLab1.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/")
    public CategoryEntity addCategory(@RequestBody CategoryEntity category){
        return categoryService.addCategory(category);
    }

    @GetMapping("/getById/{id}")
    public CategoryEntity getById(@PathVariable Integer id){
        return categoryService.getById(id);
    }
}
