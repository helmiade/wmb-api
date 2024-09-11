package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Order;
import com.enigmacamp.warung_makan_bahari_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CommonResponse<OrderResponse>> createOrder(@RequestBody OrderRequest request) {
        OrderResponse orderResponse=orderService.createNewTransaction(request);
        CommonResponse<OrderResponse> commonResponse=CommonResponse.<OrderResponse>builder()
                .message("succesfull created new transaction")
                .statusCode(HttpStatus.CREATED.value())
                .data(orderResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<OrderResponse>> getOrderById(@PathVariable String id) {
        OrderResponse orderResponse=orderService.getById(id);
        CommonResponse<OrderResponse> commonResponse=CommonResponse.<OrderResponse>builder()
                .message("succesfull get transaction by id")
                .statusCode(HttpStatus.CREATED.value())
                .data(orderResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commonResponse);
//        return orderService.getById(id);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> orderResponse=orderService.getAll();
        CommonResponse<List<OrderResponse>> commonResponse=CommonResponse.<List<OrderResponse>>builder()
                .message("succesfull get all transaction")
                .statusCode(HttpStatus.CREATED.value())
                .data(orderResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commonResponse);
    }
}
