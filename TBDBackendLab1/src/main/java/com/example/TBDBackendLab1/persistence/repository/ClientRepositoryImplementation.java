package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.ClientAlert;
import com.example.TBDBackendLab1.persistence.entity.ClientEntity;
import com.example.TBDBackendLab1.persistence.entity.ClientQueryReport;
import com.example.TBDBackendLab1.persistence.entity.Client_p;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Map;

@Repository
public class ClientRepositoryImplementation implements ClientRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public ClientEntity addClient(ClientEntity client) {
        String sql = "INSERT INTO client (client_name, direction, email, client_number, client_password, is_admin) " +
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
            throw e;
        }
    }


    @Override
    public ClientEntity getById(Integer client_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM client WHERE client_id=:client_id")
                    .addParameter("client_id", client_id)
                    .executeAndFetchFirst(ClientEntity.class);
        }
    }

    @Override
    public ClientEntity getByEmail(String email) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM client WHERE email=:email")
                    .addParameter("email", email)
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

    public List<ClientAlert> getClientAlerts() {
        String sql = "SELECT * FROM shop_alerts";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(ClientAlert.class);
        }
    }

    public List<Client_p> getClientIssueReport() {
        String sql = """
        SELECT
            c.client_id,
            c.client_name,
            COUNT(DISTINCT o.order_id) AS total_orders,
            COUNT(DISTINCT si.order_id) AS problematic_orders,
            CASE
                WHEN COUNT(DISTINCT o.order_id) = 0 THEN 0
                ELSE ROUND(
                    (COUNT(DISTINCT si.order_id)::NUMERIC / COUNT(DISTINCT o.order_id)) * 100, 2
                )
            END AS issue_percentage
        FROM
            client c
        LEFT JOIN order_info o ON o.client_id = c.client_id
        LEFT JOIN stock_issues si ON si.order_id = o.order_id
        GROUP BY c.client_id, c.client_name
        ORDER BY issue_percentage DESC;
    """;

        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(Client_p.class); // Devuelve una lista de objetos Client_p
        }
    }

}



