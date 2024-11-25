package com.example.TBDBackendLab1.service;

import com.example.TBDBackendLab1.persistence.entity.OrderEntity;
import com.example.TBDBackendLab1.persistence.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public OrderEntity addOrder(OrderEntity order){
        return orderRepository.addOrder(order);
    }

    public OrderEntity getById(Integer order_id) {
        return orderRepository.getById(order_id);
    }

    public List<OrderEntity> getByClientId(Integer client_id){
        return orderRepository.getByClientId(client_id);
    }

    public boolean updateOrder(String status, Integer order_id){return orderRepository.updateOrderState(order_id,status);}

    public boolean deleteOrder(Integer order_id){return orderRepository.deleteOrder(order_id);}

    }

