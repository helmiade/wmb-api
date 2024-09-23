package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.constant.ERole;
import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterCustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.Role;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.mapper.CustomerMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.CustomerResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.UserCredentialMapper;
import com.enigmacamp.warung_makan_bahari_api.repository.CustomerRepository;
import com.enigmacamp.warung_makan_bahari_api.service.RoleService;
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

    @Mock
    private UserCredentialMapper userCredentialMapper;

    @Mock
    private RoleService roleService;


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
        RegisterCustomerRequest customerRequest = RegisterCustomerRequest.builder()
                .name("soleh")
                .phoneNumber("0872728")
                .isMember(true)
                .username("helmiad166")
                .password("Helmiade1604@")
                .build();

//        when(roleService.getOrSave(Role.builder().name(ERole.ROLE_CUSTOMER).build())).thenReturn(Role);
        Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_CUSTOMER).build());

        UserCredential userCredential= userCredentialMapper.mapToUserCredential(customerRequest, role);

        // Inisialisasi Customer object dari CustomerRequest

        Customer customer = CustomerMapper.customerMapper(request);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);

        // Act (aksi)
        CustomerResponse customerResponse = customerService.createNew(customerRequest, userCredential);

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
        RegisterCustomerRequest customerRequest = RegisterCustomerRequest.builder()
                .name("soleh")
                .phoneNumber("0872728")
                .isMember(true)
                .username("helmiad166")
                .password("Helmiade1604@")
                .build();

//        when(roleService.getOrSave(Role.builder().name(ERole.ROLE_CUSTOMER).build())).thenReturn(Role);
        Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_CUSTOMER).build());

        UserCredential userCredential= userCredentialMapper.mapToUserCredential(customerRequest, role);
        Customer customer = CustomerMapper.customerMapper(request);

        when(customerRepository.saveAndFlush(any(Customer.class))).thenThrow(new DataIntegrityViolationException("phone number already exists"));
        ResponseStatusException responseStatusException= assertThrows(ResponseStatusException.class, () -> customerService.createNew(customerRequest, userCredential));
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
    void update_WhenCustomerFound_ShouldSaveExistingCustomer() {
        // Arrange
        // Prepare request
        CustomerRequest request = new CustomerRequest();
        request.setName("soleh");
        request.setPhoneNumber("0872728");
        request.setId("123");

        // Convert request to customer
        Customer customerFromRequest = CustomerMapper.customerMapper(request);

        // Prepare existing customer (ditemukan berdasarkan ID)
        Customer existingCustomer = new Customer();
        existingCustomer.setId(request.getId());
        existingCustomer.setName("existingName");
        existingCustomer.setPhoneNumber("0000000");

        // Mock repository behavior to find existing customer
        when(customerRepository.findById(request.getId())).thenReturn(Optional.of(existingCustomer));

        // Mock repository save behavior to ensure the correct values are set
        when(customerRepository.saveAndFlush(any(Customer.class))).thenAnswer(invocation -> {
            Customer customerToSave = invocation.getArgument(0);
            // Ensure existingCustomer is updated with values from request
            existingCustomer.setName(customerToSave.getName());
            existingCustomer.setPhoneNumber(customerToSave.getPhoneNumber());
            return existingCustomer;
        });

        // Act
        CustomerResponse customerResponse = customerService.update(request);

        // Assert
        assertNotNull(customerResponse);
        assertEquals(request.getName(), customerResponse.getName());
        assertEquals(request.getPhoneNumber(), customerResponse.getPhoneNumber());
        verify(customerRepository, times(1)).saveAndFlush(existingCustomer);
    }




    @Test
    void deleteById() {
        Customer request= Customer.builder()
                .name("helmi")
                .id("123")
                .build();
        when(customerRepository.findById(request.getId())).thenReturn(Optional.of(request));
        doNothing().when(customerRepository).delete(request);
        customerService.deleteById(request.getId());

        // Assert
        // Verifying findById is called once
        verify(customerRepository, times(1)).findById(request.getId());

        // Verifying delete is called once with the correct customer object
        verify(customerRepository, times(1)).delete(request);

    }
}