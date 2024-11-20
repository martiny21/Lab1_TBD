package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class ClientRepositoryImplementation implements ClientRepository{

    @Autowired
    private Sql2o sql2o;
    @Override
    public ClientEntity addClient(ClientEntity client) {
        String sql = "INSERT INTO client (client_name, direction, email, client_password,client_number)" +
                "VALUES (:clientName, :direction, :email, :clientPassword, :clientNumber)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Long generatedId = (Long) con.createQuery(sql, true)
                    .addParameter("client_name", client.getClientName())
                    .addParameter("direction", client.getDirection())
                    .addParameter("email", client.getEmail())
                    .addParameter("client_password", client.getClientPassword())
                    .addParameter("client_number", client.getClientNumber())
                    .executeUpdate()
                    .getKey();

            client.setIdClient(generatedId);
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
        }
    }

    @Override
    public ClientEntity getById(Long id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM client WHERE id=:id_client")
                    .addParameter("id_client",id)
                    .executeAndFetchFirst(ClientEntity.class);
        }
    }

    @Override
    public ClientEntity getByEmail(String email) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM client WHERE email=:email")
                    .addParameter("email",email)
                    .executeAndFetchFirst(ClientEntity.class);
        }
    }
}
