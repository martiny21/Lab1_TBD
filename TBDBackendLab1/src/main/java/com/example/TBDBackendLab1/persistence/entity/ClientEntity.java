package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    private Integer client_id;
    private String client_name;
    private String direction;
    private String email;
    private String client_number;
    private String client_password;
    private boolean is_admin;
}
