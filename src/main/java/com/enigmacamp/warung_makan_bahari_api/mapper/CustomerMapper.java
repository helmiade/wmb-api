package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterCustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CustomerMapper {
    private final UserService userService;
    public Customer customerMapper(RegisterCustomerRequest registerCustomerRequest, UserCredential userCredential) {
        return Customer.builder()
                .isMember(registerCustomerRequest.getIsMember())
                .name(registerCustomerRequest.getName())
                .phoneNumber(registerCustomerRequest.getPhoneNumber())
                .userCredential(userCredential)
                .build();
    }
}
