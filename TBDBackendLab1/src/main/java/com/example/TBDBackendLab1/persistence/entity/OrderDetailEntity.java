package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity {

    private Integer id_detalle;
    private Integer id_orden;
    private Integer id_producto;
    private Integer cantidad;
    private Double precio_unitario;
}
