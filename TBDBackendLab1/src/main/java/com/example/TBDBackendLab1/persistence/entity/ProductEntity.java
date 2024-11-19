package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    private Long idProduct;
    private String name;
    private String desc;
    private Double price;
    private int stock;
    private String state;
    private Long idCategory;
}
