package com.example.TBDBackendLab1.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    private Long idClient;
    private String clientName;
    private String direction;
    private String email;
    private String clientPassword;
    private String clientNumber;
    private Boolean isAdmin;

}
