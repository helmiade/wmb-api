package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer createNew(Customer customer);
    Customer getById(String id);
    Page<Customer> getAll(PagingRequest request);
    Customer update(Customer customer);
    void deleteById(String id);
}
