package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.OrderDetailEntity;
import java.util.List;

public interface OrderDetailRepository {

    OrderDetailEntity addOrderDetail(OrderDetailEntity order);
    OrderDetailEntity getById(Integer id);
    List<OrderDetailEntity> getByIdOrder(Integer id);
    List<OrderDetailEntity> getByIdProduct(Integer id);
}
