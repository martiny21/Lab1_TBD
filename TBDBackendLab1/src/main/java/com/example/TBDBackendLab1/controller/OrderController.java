package com.example.TBDBackendLab1.controller;

import com.example.TBDBackendLab1.persistence.entity.OrderEntity;
import com.example.TBDBackendLab1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<OrderEntity> getById(@PathVariable("id") Integer id) {
        OrderEntity order = orderService.getById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getByClientId/{id}")
    public ResponseEntity<List<OrderEntity>> getByClientId(@PathVariable Integer id){
        List<OrderEntity> orders = orderService.getByClientId(id);
        if(!orders.isEmpty()){
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/update/{id}/{status}")
    public boolean updateOrder(@PathVariable Integer id, @PathVariable String status){
        return orderService.updateOrder(status, id);

    }

    @PostMapping("/delete/{orderId}")
    public boolean deleteOrder(@PathVariable Integer orderId){
        return orderService.deleteOrder(orderId);
    }


}
