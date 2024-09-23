package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;

@Component
public class CustomerRequestMapper {
    public static CustomerRequest customerRequestMapper(Customer customer) {
        return CustomerRequest.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}
