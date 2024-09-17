package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderDetailRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.*;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderRepository;
import com.enigmacamp.warung_makan_bahari_api.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final MenuService menuService;
    private final CustomerService customerService;
    private final TableService tableService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public OrderResponse createNewTransaction(OrderRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        Table table= tableService.findById(request.getTableId());
        //1. buat objek order
        Order order = Order.builder()
                .customer(customer)
                .table(table)
                .transDate(LocalDateTime.now())
//                .orderDetails(orderDetails)
                .build();

        orderRepository.saveAndFlush(order);


        //4. buat objek order details
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(OrderDetailRequest orderDetailRequest:request.getOrderDetails()){
            MenuResponse menuResponse = menuService.getById(orderDetailRequest.getMenuId());
            Menu menu = Menu.builder()
                    .id(menuResponse.getId())
                    .name(menuResponse.getMenuName())
                    .price(menuResponse.getPrice())
                    .build();
            OrderDetail orderDetail = OrderDetail.builder()
                    .menu(menu)
                    .price(menu.getPrice())
                    .order(order)
                    .quantity(orderDetailRequest.getQuantity())
                    .build();
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);

        //set date
//        order.setTransDate(LocalDateTime.now());
        orderDetailService.createBulk(orderDetails);

        return mapToOrderResponse(order);
    }

    private static OrderResponse mapToOrderResponse(Order order) {
        List<OrderDetailResponse> orderDetailResponses= order.getOrderDetails().stream().map(orderDetail -> {
            return OrderDetailResponse.builder()
                    .orderDetailId(orderDetail.getId())
                    .orderId(orderDetail.getOrder().getId())
                    .menuId(orderDetail.getMenu().getId())
                    .price(orderDetail.getPrice())
                    .quantity(orderDetail.getQuantity())
                    .build();
        }).collect(toList());

        return OrderResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomer().getId())
                .tableName(order.getTable().getTableName())
                .orderDetails(orderDetailResponses)
                .transDate(order.getTransDate())
                .build();
    }

    @Override
    public OrderResponse getById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found"));
        return mapToOrderResponse(order);
    }


//    public List<OrderResponse> getAll() {
//        List<Order> orders = orderRepository.findAll();
//
//        List<OrderResponse> orderResponses = new ArrayList<>();
//        for(Order order:orders){
//            OrderResponse orderResponse = mapToOrderResponse(order);
//            orderResponses.add(orderResponse);
//        }
//        //cara kedua
////        List<OrderResponse> orderResponseList=orders.stream().map(order -> mapToOrderResponse(order)).collect(Collectors.toList());
//        List<OrderResponse> orderResponseList=orders.stream().map(OrderServiceImpl::mapToOrderResponse).toList();
//
//
//
//        return orderResponseList;
//    }
    @Override
    public Page<Order> getAll(PagingRequest request) {
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize());
        return orderRepository.findAll(pageable);
    }
}
