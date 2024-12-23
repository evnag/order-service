package com.module3.order_service.service;

import com.module3.order_service.exception.OrderServiceException;
import com.module3.order_service.model.Order;
import com.module3.order_service.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderServiceException("Order with id: " + id + " not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public ResponseEntity<Void> delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderServiceException("Order with id: " + id + " not found"));
        orderRepository.delete(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderServiceException("Order with id: " + id + " not found"));
    }
}
