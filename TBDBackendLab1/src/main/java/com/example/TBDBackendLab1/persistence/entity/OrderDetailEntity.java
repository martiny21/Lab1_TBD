package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity {

    private Long idDetail;
    private Long idOrder;
    private Long idProduct;
    private Double amount;
    private Double unitPrice;
}
