package com.example.TBDBackendLab1.controller;

import com.example.TBDBackendLab1.persistence.entity.ProductEntity;
import com.example.TBDBackendLab1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/")
    public ProductEntity addProduct(@RequestBody ProductEntity product){
        return productService.addProduct(product);
    }

    @GetMapping("/getById/{id}")
    public ProductEntity getById(@PathVariable Integer id){
        return productService.getById(id);
    }

    @GetMapping("/getByCategoryId/{id}")
    public List<ProductEntity> getByCategoryId(@PathVariable Integer id){
        return productService.getByCategoryId(id);
    }
}
