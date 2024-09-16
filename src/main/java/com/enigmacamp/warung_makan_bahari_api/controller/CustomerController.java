package com.enigmacamp.warung_makan_bahari_api.controller;


import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.mapper.CommonResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.PagingRequestMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.PagingResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CommonResponseMapper commonResponseMapper;
    private final CustomerService customerService;
    private final PagingRequestMapper pagingRequestMapper;
    private final PagingResponseMapper pagingResponseMapper;

    //post/ create new customer
    @PostMapping
    public Customer createNewCustomer(@RequestBody Customer customer) {
        return customerService.createNew(customer);
    }

    //get by id
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerService.getById(id);
    }

    //get all customer
    @GetMapping
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size ){
        PagingRequest request = pagingRequestMapper.pagingRequest(page, size);
        Page<Customer> customers=customerService.getAll(request);
        PagingResponse pagingResponse = pagingResponseMapper.pagingResponseToMapper(customers,page,size);

//        CommonResponse<List<Customer>> response= CommonResponse.<List<Customer>>builder()
//                .message("successfully get all customer")
//                .statusCode(HttpStatus.OK.value())
//                .data(customers.getContent())
//                .paging(pagingResponse)
//                .build();
        CommonResponse<List<Customer>> response= commonResponseMapper.commonResponseToMap(customers,pagingResponse);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);

    }

    //update
    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    //delete by id
    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id) {
        customerService.deleteById(id);
    }

}
