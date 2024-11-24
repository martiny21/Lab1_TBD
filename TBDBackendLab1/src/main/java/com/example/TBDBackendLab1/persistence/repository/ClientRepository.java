package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.ClientAlert;
import com.example.TBDBackendLab1.persistence.entity.ClientEntity;
import com.example.TBDBackendLab1.persistence.entity.ClientQueryReport;

import java.util.List;

public interface ClientRepository {

    ClientEntity addClient(ClientEntity client);
    ClientEntity getById(Integer client_id);
    ClientEntity getByEmail(String email);
    List<ClientQueryReport> getClientQueryReport();
    List<ClientAlert> getClientAlerts();
}
