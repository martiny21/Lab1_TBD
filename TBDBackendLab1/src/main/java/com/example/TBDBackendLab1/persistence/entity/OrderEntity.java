package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    private Long idOrder;
    private Date orderDate;
    private String state;
    private Long idClient;
    private Double total;
}
