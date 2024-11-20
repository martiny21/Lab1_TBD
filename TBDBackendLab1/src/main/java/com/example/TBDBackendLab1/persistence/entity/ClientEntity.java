package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    private Long idClient;
    private String name;
    private String direction;
    private String email;
    private String number;

}
