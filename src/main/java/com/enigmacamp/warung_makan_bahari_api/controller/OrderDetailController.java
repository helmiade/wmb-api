package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderDetailResponse;
import com.enigmacamp.warung_makan_bahari_api.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<OrderDetailResponse>> findById(@PathVariable String id) {
    OrderDetailResponse orderDetailResponse = orderDetailService.getById(id);
    CommonResponse<OrderDetailResponse> responseCommonResponse = CommonResponse.<OrderDetailResponse>builder()
            .message("successfull get detail transaction by id")
            .statusCode(HttpStatus.CREATED.value())
            .data(orderDetailResponse)
            .build();

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseCommonResponse);
        //        return orderDetailService.getById(id);
    }
    @GetMapping
    public ResponseEntity<CommonResponse<List<OrderDetailResponse>>> findAll() {
        List<OrderDetailResponse> orderDetailResponse = orderDetailService.getByAll();
        CommonResponse<List<OrderDetailResponse>> responseCommonResponse = CommonResponse.<List<OrderDetailResponse>>builder()
                .message("successfull get all transaction detail")
                .statusCode(HttpStatus.CREATED.value())
                .data(orderDetailResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseCommonResponse);
    }
}
