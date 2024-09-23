package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterCustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    private final UserService userService;
    public static Customer customerMapper(RegisterCustomerRequest registerCustomerRequest, UserCredential userCredential) {
        return Customer.builder()
                .isMember(registerCustomerRequest.getIsMember())
                .name(registerCustomerRequest.getName())
                .phoneNumber(registerCustomerRequest.getPhoneNumber())
                .userCredential(userCredential)
                .build();
    }
    public static Customer customerMapper(CustomerRequest customerRequest) {
        return Customer.builder()
                .id(customerRequest.getId())
                .name(customerRequest.getName())
                .phoneNumber(customerRequest.getPhoneNumber())
                .build();
    }
    public static Customer customerMapper(CustomerResponse customerResponse) {
        return Customer.builder()
                .name(customerResponse.getName())
                .phoneNumber(customerResponse.getPhoneNumber())
                .build();
    }


}
