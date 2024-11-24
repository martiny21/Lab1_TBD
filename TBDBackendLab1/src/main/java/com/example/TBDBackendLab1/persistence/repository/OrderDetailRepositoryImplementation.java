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
        try (org.sql2o.Connection con = sql2o.beginTransaction()) { // Inicia una transacción
            // Consulta para obtener el client_id correspondiente al order_id
            String getClientIdQuery = "SELECT client_id FROM order_info WHERE order_id = :order_id";
            Integer clientId = con.createQuery(getClientIdQuery)
                    .addParameter("order_id", detail.getOrder_id())
                    .executeScalar(Integer.class);

            // Asegúrate de que se encontró un client_id antes de continuar
            if (clientId == null) {
                throw new RuntimeException("No se encontró un client_id para el order_id proporcionado.");
            }

            // Establece la variable de sesión app.client_id
            String setClientIdQuery = "SET app.client_id = " + clientId;
            con.createQuery(setClientIdQuery).executeUpdate();

            // Inserta el detalle de la orden
            String insertQuery = "INSERT INTO order_detail (order_id, product_id, amount, unit_price) " +
                    "VALUES (:order_id, :product_id, :amount, :unit_price)";

            Integer generatedId = (Integer) con.createQuery(insertQuery, true)
                    .addParameter("order_id", detail.getOrder_id())
                    .addParameter("product_id", detail.getProduct_id())
                    .addParameter("amount", detail.getAmount())
                    .addParameter("unit_price", detail.getUnit_price())
                    .executeUpdate()
                    .getKey();

            // Establece el ID generado en la entidad OrderDetail
            detail.setDetail_id(generatedId);

            con.commit(); // Confirma la transacción
            return detail;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar el detalle de la orden", e);
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

    //public boolean returnsorder(Integer order_id) {}

}
