package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class OrderRepositoryImplementation implements OrderRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public OrderEntity addOrder(OrderEntity order) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) { // Inicia una transacción
            // Construye el comando SET app.client_id manualmente
            String setClientIdQuery = "SET app.client_id = " + order.getClient_id();
            con.createQuery(setClientIdQuery).executeUpdate();

            // Ahora ejecuta la consulta de inserción
            String sql = "INSERT INTO order_info (order_date, state, client_id, total) " +
                    "VALUES (:order_date, :state, :client_id, :total)";

            Integer generatedId = (Integer) con.createQuery(sql, true)
                    .addParameter("order_date", order.getOrder_date())
                    .addParameter("state", order.getState())
                    .addParameter("client_id", order.getClient_id())
                    .addParameter("total", order.getTotal())
                    .executeUpdate()
                    .getKey();

            // Establece el ID generado en la entidad Order
            order.setOrder_id(generatedId);

            con.commit(); // Confirma la transacción
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar la orden", e);
        }
    }



    @Override
    public OrderEntity getById(Integer order_id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM order_info WHERE order_id=:order_id")
                    .addParameter("order_id",order_id)
                    .executeAndFetchFirst(OrderEntity.class);
        }
    }

    @Override
    public List<OrderEntity> getByClientId(Integer clientId) {
        String sql = "SELECT order_id, order_date, state, client_id, total FROM order_info WHERE client_id = :client_id";
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("client_id", clientId)
                    .executeAndFetch(OrderEntity.class);
        }
    }
    @Override
    public boolean deleteOrder(Integer order_id){
        String sql = "DELETE FROM order_info WHERE order_id = :order_id";
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("order_id",order_id)
                    .executeUpdate();

        }
        return true;
    }
    @Override
    public boolean updateOrderState(Integer order_id, String state) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) { // Inicia una transacción

            // Obtén el client_id basado en el order_id
            String getClientIdQuery = "SELECT client_id FROM order_info WHERE order_id = :order_id";
            Integer clientId = con.createQuery(getClientIdQuery)
                    .addParameter("order_id", order_id)
                    .executeScalar(Integer.class);

            // Construye el comando SET app.client_id manualmente
            String setClientIdQuery = "SET app.client_id = " + clientId;
            con.createQuery(setClientIdQuery).executeUpdate();

            // Ahora ejecuta la consulta de actualización
            String sql = "UPDATE order_info " +
                    "SET state = :state " +
                    "WHERE order_id = :order_id";

            con.createQuery(sql)
                    .addParameter("state", state)
                    .addParameter("order_id", order_id)
                    .executeUpdate();

            con.commit(); // Confirma la transacción
            return true; // Si todo es exitoso, retorna true
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el estado de la orden", e);
        }
    }


}
