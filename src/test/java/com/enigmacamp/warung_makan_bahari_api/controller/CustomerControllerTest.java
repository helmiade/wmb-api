package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.constant.ERole;
import com.enigmacamp.warung_makan_bahari_api.dto.request.CustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterCustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.Order;
import com.enigmacamp.warung_makan_bahari_api.entity.Role;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.mapper.*;
import com.enigmacamp.warung_makan_bahari_api.repository.CustomerRepository;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.service.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



//@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
//    @InjectMocks
//    private CustomerController customerController;

    @MockBean
    private CommonResponseMapper commonResponseMapper;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private UserCredentialMapper userCredentialMapper;

    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private RoleService roleService;
    @MockBean
    private PagingRequestMapper pagingRequestMapper;
    @MockBean
    private PagingResponseMapper pagingResponseMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc= MockMvcBuilders.standaloneSetup(customerController).build();
//        objectMapper= new ObjectMapper();
//    }
    @WithMockUser
    @Test
    void createNewCustomer() throws Exception {
        //arrange
        RegisterCustomerRequest request = RegisterCustomerRequest.builder()
                .username("helmiii")
                .password("Helmi1604@")
                .name("helmi")
                .phoneNumber("088282")
                .build();

        Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_CUSTOMER).build());
        UserCredential userCredential=userCredentialMapper.mapToUserCredential(request, role);

        CustomerResponse customerResponse= CustomerResponse.builder()
                .name("helmi")
                .phoneNumber("088282")
                .build();

        when(customerService.createNew(request, userCredential)).thenReturn(customerResponse);

        //act
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("helmi"))
                .andExpect(jsonPath("$.phoneNumber").value("088282"));

        verify(customerService, times(1)).createNew(any(), any());
    }

    @WithMockUser
    @Test
    void getCustomerById() throws Exception {
        Customer customer= Customer.builder()
                .id("1")
                .name("helmi")
                .phoneNumber("088282")
                .build();
        CustomerResponse response= CustomerResponseMapper.customerResponseMapper(customer);
        when(customerService.getById(customer.getId())).thenReturn(response);

        mockMvc.perform(get("/api/v1/customers/{id}", customer.getId())
//                        .header("Authorization", "Bearer Authenticator")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().is2xxSuccessful());

        verify(customerService, times(1)).getById(customer.getId());

    }

    @WithMockUser
    @Test
    void getAllCustomers() throws Exception {
        Integer page = 0;
        Integer size = 10;

        Customer customer=Customer.builder()
                .id("1")
                .name("Helmi")
                .phoneNumber("088282")
                .build();
        PagingRequest request = pagingRequestMapper.pagingRequest(page, size);
        Pageable pageable= PageRequest.of(page, size);
        Page<Customer> customersPage= new PageImpl<>(Collections.singletonList(customer), pageable, 1);

        when(customerService.getAll(request)).thenReturn(customersPage);
        PagingResponse pagingResponse = pagingResponseMapper.pagingResponseToMapper(customersPage,page,size);
        CommonResponse<List<Customer>> response= commonResponseMapper.commonResponseToMap(customersPage,pagingResponse);
        when(commonResponseMapper.commonResponseToMap(customersPage,pagingResponse)).thenReturn(response);

//        ResponseEntity.status(HttpStatus.OK).body(response);

        mockMvc.perform(get("/api/v1/customers")
//                        .header("Authorization", "Bearer Authenticator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());






    }

    @WithMockUser
    @Test
    void updateCustomer() throws Exception {
        CustomerRequest request= CustomerRequest.builder()
                .name("helmi")
                .phoneNumber("0888")
                .build();

        CustomerResponse response=CustomerResponse.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();

        when(customerService.update(request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/customers")
//                        .header("Authorization", "Bearer Authenticator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
        verify(customerService, times(1)).update(request);

    }

    @WithMockUser
    @Test
    void deleteCustomerById() throws Exception {
        Customer request = new Customer();
        request.setName("soleh");
        request.setId("123");
        doNothing().when(customerRepository).deleteById(request.getId());

        mockMvc.perform(delete("/api/v1/customers/{id}", request.getId())  // Memanggil endpoint delete
                        .contentType(MediaType.APPLICATION_JSON))  // Mengatur Content-Type sebagai JSON
                .andExpect(status().isOk());  // Memastikan HTTP status 204 (No Content)

        verify(customerService, times(1)).deleteById(request.getId());

    }
}