package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    private Integer id_cliente;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String contrasena;
    private boolean es_admin;
}
