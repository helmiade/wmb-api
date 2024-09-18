package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.mapper.CustomerMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.CustomerResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createNew_WhenValidRequest_ShouldReturnCustomer() {
        // Arrange (persiapan data)
        CustomerRequest request = new CustomerRequest();
        request.setName("soleh");
        request.setPhoneNumber("0872728");

        // Inisialisasi Customer object dari CustomerRequest

        Customer customer = CustomerMapper.customerMapper(request);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);

        // Act (aksi)
        CustomerResponse customerResponse = customerService.createNew(request);

        // Assert (verifikasi)
        assertEquals(request.getName(), customerResponse.getName());
        assertEquals(request.getPhoneNumber(), customerResponse.getPhoneNumber());
    }

    @Test
    void createNew_WhenPhoneNumberAlreadyExist_ShouldThrowConflictException() {
        CustomerRequest request = new CustomerRequest();
        request.setName("soleh");
        request.setPhoneNumber("0872728");

        // Inisialisasi Customer object dari CustomerRequest
//        Customer customer = Customer.builder()
//                .name(request.getName())
//                .phoneNumber(request.getPhoneNumber())
//                .build();
        Customer customer = CustomerMapper.customerMapper(request);

        when(customerRepository.saveAndFlush(any(Customer.class))).thenThrow(new DataIntegrityViolationException("phone number already exists"));
        ResponseStatusException responseStatusException= assertThrows(ResponseStatusException.class, () -> customerService.createNew(request));
        assertEquals("409 CONFLICT \"phone number already exists\"", responseStatusException.getMessage());
        verify(customerRepository, times(1)).saveAndFlush(any(Customer.class));
    }



    @Test
    void getById() {
        Customer request = new Customer();
        request.setName("soleh");
        request.setId("123");
        when(customerRepository.findById(request.getId())).thenReturn(Optional.of(request));

        CustomerResponse foundCustomer = customerService.getById(request.getId());
        verify(customerRepository, times(1)).findById(request.getId());

    }

    @Test
    void getById_WhenCustomerNotFound_ShouldThrowNotFoundException() {
        when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
        ResponseStatusException responseStatusException= assertThrows(ResponseStatusException.class, () -> customerService.getById("123"));
        assertEquals("404 NOT_FOUND \"customer not found\"", responseStatusException.getMessage());
        verify(customerRepository, times(1)).findById(anyString());
    }


    @Test
    void getAll() {
        Customer request = new Customer();
        request.setName("soleh");
        request.setId("123");
        PagingRequest pagingRequest= PagingRequest.builder()
                .page(1)
                .size(5)
                .build();
        Pageable pageable= PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize());
        Page<Customer> customersPage= new PageImpl<>(Collections.singletonList(request), pageable, 1);
        when(customerRepository.findAll(pageable)).thenReturn(customersPage);
        Page<Customer> customers = customerService.getAll(pagingRequest);
        verify(customerRepository, times(1)).findAll(pageable);
        assertEquals(1, customers.getTotalElements());

    }

    @Test
    void update_WhenCustomerFound_ShouldReturnCustomer() {
        CustomerRequest request = new CustomerRequest();
        request.setName("soleh");
        request.setPhoneNumber("93030");
        Customer customer = CustomerMapper.customerMapper(request);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        CustomerResponse customerResponse= CustomerResponseMapper.customerResponseMapper(customer);
        assertEquals(request.getName(), customerResponse.getName());
    }


    @Test
    void deleteById() {
        Customer request = new Customer();
        request.setName("soleh");
        request.setId("123");
        when(customerRepository.findById(request.getId())).thenReturn(Optional.of(request));
        customerService.deleteById(request.getId());
        verify(customerRepository, times(1)).delete(request);

    }
}