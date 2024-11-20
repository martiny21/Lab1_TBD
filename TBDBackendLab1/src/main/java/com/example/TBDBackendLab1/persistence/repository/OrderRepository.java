package com.example.TBDBackendLab1.persistence.repository;

import com.example.TBDBackendLab1.persistence.entity.OrderEntity;

import java.util.List;

public interface OrderRepository {

    OrderEntity addOrder(OrderEntity order);
    OrderEntity getById(Long id);
    List<OrderEntity> getByIdClient(Long idClient);
}