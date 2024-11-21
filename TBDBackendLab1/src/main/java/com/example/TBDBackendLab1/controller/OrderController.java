package com.example.TBDBackendLab1.controller;

import com.example.TBDBackendLab1.persistence.entity.OrderEntity;
import com.example.TBDBackendLab1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/")
    public OrderEntity addOrder(@RequestBody OrderEntity order){
        return orderService.addOrder(order);
    }

    @GetMapping("/getById/{id}")
    public OrderEntity getById(@PathVariable Integer id){
        return orderService.getById(id);
    }

    @GetMapping("/getByClientId/{id}")
    public List<OrderEntity> getByClientId(@PathVariable Integer id){
        return orderService.getByClientId(id);
    }

}
