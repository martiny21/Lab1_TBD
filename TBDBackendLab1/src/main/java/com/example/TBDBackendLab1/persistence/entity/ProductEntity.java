package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    private Integer product_id;
    private String product_name;
    private String product_desc;
    private Double price;
    private Integer stock;
    private String state;
    private Integer category_id;
}
