package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createNew(Customer customer);
    Customer getById(String id);
    List<Customer> getAll(String param);
    Customer update(Customer customer);
    void deleteById(String id);
}
