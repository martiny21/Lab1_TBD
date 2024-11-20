package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.OrderDetailEntity;
import java.util.List;

public interface OrderDetailRepository {

    OrderDetailEntity addOrderDetail(OrderDetailEntity order);
    OrderDetailEntity getById(Long id);
    List<OrderDetailEntity> getByIdOrder(Long id);
    List<OrderDetailEntity> getByIdProduct(Long id);
}
