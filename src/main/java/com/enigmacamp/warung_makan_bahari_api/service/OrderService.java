package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.entity.Order;

import java.util.List;

public interface OrderService {
    Order createNewTransaction(Order order);
    Order findById(String id);
    List<Order> findAll();

}
