package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.ClientEntity;
import com.example.TBDBackendLab1.persistence.entity.OrderDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class OrderDetailRepositoryImplementation implements OrderDetailRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public OrderDetailEntity addOrderDetail(OrderDetailEntity detail) {
        String sql = "SET app.client_id = (SELECT client_id FROM orders WHERE order_id = :order_id); " +
                "INSERT INTO order_detail (order_id, product_id, amount, unit_price)" +
                "VALUES (:order_id, :product_id, :amount, :unit_price)";

        try (org.sql2o.Connection con = sql2o.open()) {
            Integer generatedId = (Integer) con.createQuery(sql, true)
                    .addParameter("order_id", detail.getOrder_id())
                    .addParameter("product_id", detail.getProduct_id())
                    .addParameter("amount", detail.getAmount())
                    .addParameter("unit_price", detail.getUnit_price())
                    .executeUpdate()
                    .getKey();

            detail.setDetail_id(generatedId);
            return detail;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Manejar la excepción si es necesario
        }
    }

    @Override
    public OrderDetailEntity getById(Integer detail_id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM order_detail WHERE detail_id=:detail_id")
                    .addParameter("detail_id",detail_id)
                    .executeAndFetchFirst(OrderDetailEntity.class);
        }
    }

    @Override
    public List<OrderDetailEntity> getByOrderId(Integer order_id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM order_detail WHERE order_id=:order_id")
                    .addParameter("order_id",order_id)
                    .executeAndFetch(OrderDetailEntity.class);
        }
    }

    @Override
    public List<OrderDetailEntity> getByProductId(Integer product_id) {
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM order_detail WHERE product_id=:product_id")
                    .addParameter("product_id",product_id)
                    .executeAndFetch(OrderDetailEntity.class);
        }
    }

}
