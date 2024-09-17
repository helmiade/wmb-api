package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.repository.CustomerRepository;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Page<Customer> getAll(PagingRequest request) {
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize());
        return customerRepository.findAll(pageable);
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
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"customer not found"));
    }
}
