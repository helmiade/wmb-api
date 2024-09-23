package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.entity.Order;
import com.enigmacamp.warung_makan_bahari_api.mapper.CommonResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.PagingRequestMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.PagingResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@SecurityRequirement(name = "Bearer Authenticator")
@PreAuthorize("hasRole('ADMIN')")
public class OrderController {
    private final CommonResponseMapper commonResponseMapper;
    private final PagingRequestMapper pagingRequestMapper;
    private final PagingResponseMapper pagingResponseMapper;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CommonResponse<OrderResponse>> createOrder(@RequestBody OrderRequest request) {
        OrderResponse orderResponse=orderService.createNewTransaction(request);
        CommonResponse<OrderResponse> commonResponse=commonResponseMapper.commonResponseToMap(orderResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<OrderResponse>> getOrderById(@PathVariable String id) {
        OrderResponse orderResponse=orderService.getById(id);
        CommonResponse<OrderResponse> commonResponse=commonResponseMapper.commonResponseToMap(orderResponse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commonResponse);
    }
    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "5") Integer size) {
        PagingRequest request = pagingRequestMapper.pagingRequest(page, size);
        Page<Order> order = orderService.getAll(request);
        PagingResponse pagingResponse = pagingResponseMapper.pagingResponseToMapper(order, page, size);
        CommonResponse<List<Order>> response = commonResponseMapper.commonResponseToMap(order, pagingResponse);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);

    }
}
