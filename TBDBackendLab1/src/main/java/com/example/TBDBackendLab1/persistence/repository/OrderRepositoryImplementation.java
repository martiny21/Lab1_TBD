package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.OrderDetailEntity;
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
                    .addParameter("client_id", order.getOrder_id())
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
    public OrderEntity getById(Integer detail_id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM order_info WHERE detail_id=:detail_id")
                    .addParameter("detail_id",detail_id)
                    .executeAndFetchFirst(OrderEntity.class);
        }
    }

    @Override
    public List<OrderEntity> getByClientId(Integer client_id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM order_info WHERE client_id=:client_id")
                    .addParameter("client_id",client_id)
                    .executeAndFetchFirst(List.class);
        }
    }

}
