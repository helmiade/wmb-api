package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.repository.CustomerRepository;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createNew(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("phone number already exists");
        }

    }

    @Override
    public Customer getById(String id) {
        return findByIdOrThrowError(id);
    }

    @Override
    public List<Customer> getAll(String param) {
//        return (name==null&&phoneNumber==null)? customerRepository.findAll():customerRepository.findByNameIsContainingIgnoreCaseOrPhoneNumberEquals(name,phoneNumber);
    if (param == null || param.isEmpty()) {
        return customerRepository.findAll();
    }
    return customerRepository.findByPhoneNumberIgnoreCase(param);
//        return customerRepository.findByNameIsContainingIgnoreCaseOrPhoneNumberEquals(name, phoneNumber);
    }


    @Override
    public Customer update(Customer customer) {
        findByIdOrThrowError(customer.getId());
        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(String id) {
        Customer customer =findByIdOrThrowError(id);
        customerRepository.delete(customer);
    }

    private Customer findByIdOrThrowError(String id) {
        Optional<Customer> customer= customerRepository.findById(id);
        return customer.orElseThrow(() -> new RuntimeException("customer not found"));
    }
}
