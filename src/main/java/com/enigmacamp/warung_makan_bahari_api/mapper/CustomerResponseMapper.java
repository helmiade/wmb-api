package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerResponseMapper {
    public static CustomerResponse customerResponseMapper(Customer customer) {
        return CustomerResponse.builder()
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .isMember(customer.getIsMember())
                .build();
    }
}
