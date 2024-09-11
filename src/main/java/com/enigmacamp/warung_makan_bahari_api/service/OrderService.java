package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Order;

import java.util.List;

public interface OrderService {
    OrderResponse createNewTransaction(OrderRequest request);
    OrderResponse getById(String id);
    List<OrderResponse> getAll();

}
