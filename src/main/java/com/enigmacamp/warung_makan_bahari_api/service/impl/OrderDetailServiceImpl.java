package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Order;
import com.enigmacamp.warung_makan_bahari_api.entity.OrderDetail;
import com.enigmacamp.warung_makan_bahari_api.mapper.OrderDetailResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderDetailRepository;
import com.enigmacamp.warung_makan_bahari_api.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailResponseMapper orderDetailResponseMapper;
    private final OrderDetailRepository orderDetailRepository;
    @Override
    public List<OrderDetail> createBulk(List<OrderDetail> orderDetails) {
        return orderDetailRepository.saveAllAndFlush(orderDetails);
    }

    @Override
    public OrderDetailResponse getById(String id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));
        return OrderDetailResponseMapper.mapToOrderResponse(orderDetail);
    }

    @Override
    public List<OrderDetailResponse> getByAll() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for(OrderDetail orderDetail:orderDetails){
            OrderDetailResponse orderDetailResponse = OrderDetailResponseMapper.mapToOrderResponse(orderDetail);
            orderDetailResponses.add(orderDetailResponse);
        }
        return orderDetailResponses;
    }
}
