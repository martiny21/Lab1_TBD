package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.ClientEntity;
import com.example.TBDBackendLab1.persistence.entity.ClientQueryReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ClientRepositoryImplementation implements ClientRepository{

    @Autowired
    private Sql2o sql2o;
    @Override
    public ClientEntity addClient(ClientEntity client) {
        String sql = "INSERT INTO client (client_name, direction, email, client_number, client_password, is_admin)" +
                "VALUES (:client_name, :direction, :email, :client_number, :client_password, :is_admin)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Integer generatedId = (Integer) con.createQuery(sql, true)
                    .addParameter("client_name", client.getClient_name())
                    .addParameter("direction", client.getDirection())
                    .addParameter("email", client.getEmail())
                    .addParameter("client_number", client.getClient_number())
                    .addParameter("client_password", client.getClient_password())
                    .addParameter("is_admin", client.is_admin())
                    .executeUpdate()
                    .getKey();

            client.setClient_id(generatedId);
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
        }
    }

    @Override
    public ClientEntity getById(Integer client_id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM client WHERE client_id=:client_id")
                    .addParameter("client_id",client_id)
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

    public List<ClientQueryReport> getClientQueryReport() {
        String sql = "SELECT * FROM get_client_query_report()";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(ClientQueryReport.class);
        }
    }
}
