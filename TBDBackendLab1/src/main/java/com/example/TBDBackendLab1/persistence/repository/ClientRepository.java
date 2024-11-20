package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.ClientEntity;

public interface ClientRepository {

    ClientEntity addClient(ClientEntity client);
    ClientEntity getById(Long id);
    ClientEntity getByEmail(String email);
}
