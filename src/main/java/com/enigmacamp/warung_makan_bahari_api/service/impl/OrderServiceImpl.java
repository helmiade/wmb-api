package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.entity.Order;
import com.enigmacamp.warung_makan_bahari_api.entity.OrderDetail;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuRepository;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderDetailRepository;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderRepository;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import com.enigmacamp.warung_makan_bahari_api.service.OrderDetailService;
import com.enigmacamp.warung_makan_bahari_api.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final MenuService menuService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Order createNewTransaction(Order order) {
        //1. create bulk order detail
        orderDetailService.createBulk(order.getOrderDetails());

        //set date
        order.setTransDate(LocalDateTime.now());
        orderRepository.saveAndFlush(order);

        //set order detail
        for(OrderDetail orderDetail : order.getOrderDetails()) {
            Menu menu=menuService.getById(orderDetail.getMenu().getId());
            orderDetail.setOrder(order);
            orderDetail.setPrice(menu.getPrice());
        }

        return order;
//        return orderRepository.saveAndFlush(order);

    }

    @Override
    public Order findById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }
}
