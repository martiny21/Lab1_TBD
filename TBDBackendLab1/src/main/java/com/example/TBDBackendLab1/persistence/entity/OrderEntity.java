package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    private Integer order_id;
    private LocalDate order_date;
    private String estate;
    private Integer client_id;
    private Double total;
}
