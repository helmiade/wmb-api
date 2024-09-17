package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.OrderDetail;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderDetailResponseMapper {
    public static OrderDetailResponse mapToOrderResponse(OrderDetail orderDetail) {
        return   OrderDetailResponse.builder()
                .orderDetailId(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .menuId(orderDetail.getMenu().getId())
                .price(orderDetail.getPrice())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}
