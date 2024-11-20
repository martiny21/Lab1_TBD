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
        String sql = "INSERT INTO cliente (nombre, direccion, email, telefono, contrasena, es_admin)" +
                "VALUES (:nombre, :direccion, :email, :telefono, :contrasena, :es_admin)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Integer generatedId = (Integer) con.createQuery(sql, true)
                    .addParameter("nombre", client.getNombre())
                    .addParameter("direccion", client.getDireccion())
                    .addParameter("email", client.getEmail())
                    .addParameter("telefono", client.getTelefono())
                    .addParameter("contrasena", client.getContrasena())
                    .addParameter("es_admin", client.isEs_admin())
                    .executeUpdate()
                    .getKey();

            client.setId_cliente(generatedId);
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
        }
    }

    @Override
    public ClientEntity getById(Integer id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM cliente WHERE id_cliente=:id_cliente")
                    .addParameter("id_cliente",id)
                    .executeAndFetchFirst(ClientEntity.class);
        }
    }

    @Override
    public ClientEntity getByEmail(String email) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM cliente WHERE email=:email")
                    .addParameter("email",email)
                    .executeAndFetchFirst(ClientEntity.class);
        }
    }
}
