package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.OrderDetailEntity;
import java.util.List;

public interface OrderDetailRepository {

    OrderDetailEntity addOrderDetail(OrderDetailEntity detail);
    OrderDetailEntity getById(Integer detail_id);
    List<OrderDetailEntity> getByOrderId(Integer order_id);
    List<OrderDetailEntity> getByProductId(Integer product_id);
}
