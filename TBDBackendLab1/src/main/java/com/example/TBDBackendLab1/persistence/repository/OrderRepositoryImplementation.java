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
        String sql = "INSERT INTO order_info (order_date, estate, client_id, total)" +
                "VALUES ( :order_date, :estate, :client_id, :total)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Integer generatedId = (Integer) con.createQuery(sql, true)
                    .addParameter("order_date", order.getOrder_date())
                    .addParameter("estate", order.getEstate())
                    .addParameter("client_id", order.getClient_id())
                    .addParameter("total", order.getTotal())
                    .executeUpdate()
                    .getKey();

            order.setOrder_id(generatedId);
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
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
        String sql = "SELECT order_id, order_date, estate, client_id, total FROM order_info WHERE client_id = :client_id";
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
    public boolean updateOrderEstate(Integer order_id, String estate) {
        String sql = "UPDATE order_info " +
                "SET estate = :estate " +
                "WHERE order_id = :order_id";
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("estate", estate)
                    .addParameter("order_id", order_id)
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
