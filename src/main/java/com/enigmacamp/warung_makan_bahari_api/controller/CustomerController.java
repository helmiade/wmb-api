package com.enigmacamp.warung_makan_bahari_api.controller;


import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

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
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .size(size)
                .build();

        Page<Customer> customers=customerService.getAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(customers.getTotalElements())
                .totalPages(customers.getTotalPages())
                .build();

        CommonResponse<List<Customer>> response= CommonResponse.<List<Customer>>builder()
                .message("successfully get all customer")
                .statusCode(HttpStatus.OK.value())
                .data(customers.getContent())
                .paging(pagingResponse)
                .build();

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
