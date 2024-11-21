package com.example.TBDBackendLab1.service;

import com.example.TBDBackendLab1.persistence.entity.ClientEntity;
import com.example.TBDBackendLab1.persistence.entity.ClientQueryReport;
import com.example.TBDBackendLab1.persistence.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientEntity addClient(ClientEntity client) {return clientRepository.addClient(client);}
    public ClientEntity getById(Integer client_id) {return clientRepository.getById(client_id);}
    public ClientEntity getByEmail(String email) {return clientRepository.getByEmail(email);}

    public List<ClientQueryReport> getReports(){
        return clientRepository.getClientQueryReport();
    }
}
