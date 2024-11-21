package com.example.TBDBackendLab1.controller;

import com.example.TBDBackendLab1.persistence.entity.OrderDetailEntity;
import com.example.TBDBackendLab1.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detail")
@CrossOrigin("*")
public class OrderDetailController {

    @Autowired
    OrderDetailService orderDetailService;

    @PostMapping("/")
    public OrderDetailEntity addOrderDetail(@RequestBody OrderDetailEntity detail){
        return orderDetailService.addOrderDetail(detail);
    }

    @GetMapping("/getById/{id}")
    public OrderDetailEntity getById(@PathVariable Integer id){
        return orderDetailService.getById(id);
    }

    @GetMapping("/getByOrderId/{id}")
    public List<OrderDetailEntity> getByOrderId(@PathVariable Integer id){
        return orderDetailService.getByOrderId(id);
    }

    @GetMapping("/getByProductId/{id}")
    public List<OrderDetailEntity> getByProductId(@PathVariable Integer id){
        return orderDetailService.getByProductId(id);
    }
}
