package com.example.TBDBackendLab1.service;

import com.example.TBDBackendLab1.persistence.entity.OrderDetailEntity;
import com.example.TBDBackendLab1.persistence.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    public OrderDetailEntity addOrderDetail(OrderDetailEntity detail){
        return orderDetailRepository.addOrderDetail(detail);
    }

    public OrderDetailEntity getById(Integer detail_id){
        return orderDetailRepository.getById(detail_id);
    }

    public List<OrderDetailEntity> getByOrderId(Integer order_id){
        return orderDetailRepository.getByOrderId(order_id);
    }

    public List<OrderDetailEntity> getByProductId(Integer product_id){
        return orderDetailRepository.getByProductId(product_id);
    }
}
