package com.enigmacamp.warung_makan_bahari_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;
    private String customerId;
    private String tableName;
    private LocalDateTime transDate;
    private List<OrderDetailResponse> orderDetails;
}
