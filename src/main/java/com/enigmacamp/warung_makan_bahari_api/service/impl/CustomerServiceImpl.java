package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterCustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.mapper.CustomerMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.CustomerResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.repository.CustomerRepository;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createNew(RegisterCustomerRequest request, UserCredential userCredential) {
        Customer customer = CustomerMapper.customerMapper(request, userCredential);
        try {
            customerRepository.saveAndFlush(customer);
            return CustomerResponseMapper.customerResponseMapper(customer);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"phone number already exists");
        }

    }

    @Override
    public CustomerResponse getById(String id) {
        Customer customer=findByIdOrThrowError(id);
        return CustomerResponseMapper.customerResponseMapper(customer);
    }

    @Override
    public Page<Customer> getAll(PagingRequest request) {
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize());
        return customerRepository.findAll(pageable);
    }


    @Override
    public CustomerResponse update(CustomerRequest request) {

        Customer customer= CustomerMapper.customerMapper(request);
        Customer findId= findByIdOrThrowError(customer.getId());
        findId.setName(customer.getName());
        findId.setPhoneNumber(customer.getPhoneNumber());
        findId.setIsMember(customer.getIsMember());
        customerRepository.saveAndFlush(findId);
        return CustomerResponseMapper.customerResponseMapper(findId);
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
