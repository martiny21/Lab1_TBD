package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    private Integer id_orden;
    private LocalDate fecha_orden;
    private String estado;
    private Integer id_cliente;
    private Double total;
}
