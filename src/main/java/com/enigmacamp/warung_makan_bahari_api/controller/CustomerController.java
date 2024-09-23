package com.enigmacamp.warung_makan_bahari_api.controller;


import com.enigmacamp.warung_makan_bahari_api.constant.ERole;
import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterCustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.Role;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.mapper.CommonResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.PagingRequestMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.PagingResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.UserCredentialMapper;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@SecurityRequirement(name = "Bearer Authenticator")
@RequiredArgsConstructor
public class CustomerController {

    private final CommonResponseMapper commonResponseMapper;
    private final CustomerService customerService;
    private final UserCredentialMapper userCredentialMapper;
    private final RoleService roleService;
    private final PagingRequestMapper pagingRequestMapper;
    private final PagingResponseMapper pagingResponseMapper;

//    post/ create new customer
    @PostMapping
    public CustomerResponse createNewCustomer(@RequestBody RegisterCustomerRequest request) {
        Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_CUSTOMER).build());
        UserCredential userCredential=userCredentialMapper.mapToUserCredential(request, role);
        return customerService.createNew(request, userCredential);
    }

    //get by id
    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable String id) {
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

        CommonResponse<List<Customer>> response= commonResponseMapper.commonResponseToMap(customers,pagingResponse);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);

    }

    //update
    @PutMapping
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest request) {
        return customerService.update(request);
    }

    //delete by id
    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id) {
        customerService.deleteById(id);
    }

}
