package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity {

    private Integer detail_id;
    private Integer order_id;
    private Integer product_id;
    private Integer amount;
    private Double unit_price;
}
