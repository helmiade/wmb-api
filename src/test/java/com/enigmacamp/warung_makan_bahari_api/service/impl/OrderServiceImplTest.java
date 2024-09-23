package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderDetailRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.OrderRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.TableRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CustomerResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.OrderResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.TableResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.*;
import com.enigmacamp.warung_makan_bahari_api.mapper.CustomerMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.TableMapper;
import com.enigmacamp.warung_makan_bahari_api.repository.OrderRepository;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import com.enigmacamp.warung_makan_bahari_api.service.OrderDetailService;
import com.enigmacamp.warung_makan_bahari_api.service.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderResponse orderResponse;
    @Mock
    private OrderDetailService orderDetailService;
    @Mock
    private MenuService menuService;
    @Mock
    private CustomerService customerService;
    @Mock
    private TableService tableService;

    @Mock
    private TableResponse tableResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewTransaction() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId("1");
        orderRequest.setTableId("123");

        CustomerResponse customerResponse = CustomerResponse.builder()
                .name("soleh")
                .build();
        Customer customer= CustomerMapper.customerMapper(customerResponse);
        customer.setId("1");
        when(customerService.getById(customer.getId())).thenReturn(customerResponse);

        TableResponse tableResponse = TableResponse.builder()
                .tableName("T01")
                .build();
        Table table = TableMapper.mapToTable(tableResponse);
        table.setId("123");

        when(tableService.findById(table.getId())).thenReturn(tableResponse);

        Order order = Order.builder()
                .customer(customer)
                .table(table)
                .transDate(LocalDateTime.now())
                .build();

        when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(order);

        OrderDetailRequest orderDetailRequest= new OrderDetailRequest();
        orderDetailRequest.setMenuId("1");
        orderDetailRequest.setQuantity(2);
        orderRequest.setOrderDetails(Collections.singletonList(orderDetailRequest));

        MenuResponse menuResponse= MenuResponse.builder()
                .id("1")
                .price(10000L)
                .build();
        Menu menu = Menu.builder()
                .id("1")
                .price(100000L)
                .build();

        when(menuService.getById("1")).thenReturn(menuResponse);
        List<OrderDetail> orderDetails= new ArrayList<>();
        OrderDetail orderDetail = OrderDetail.builder()
                .id("456")
                .quantity(2)
                .menu(menu)
                .price(1000L)
                .build();
        orderDetails.add(orderDetail);
        when(orderDetailService.createBulk(anyList())).thenReturn(orderDetails);
        OrderResponse response= orderService.createNewTransaction(orderRequest);
        assertNotNull(response);

    }

    @Test
    void getById_WhenOrderFound_ShouldReturnOrder() {
        Customer customer = Customer.builder()
                .id("1")
                .name("soleh")
                .phoneNumber("08888")
                .build();

        Table table = Table.builder()
                .tableName("T01")
                .id("1")
                .build();

        Menu menu = Menu.builder()
                .id("1")
                .price(100000L)
                .build();

        Order order = Order.builder()
                .id("1")
                .customer(customer)
                .table(table)
                .transDate(LocalDateTime.now())
                .build();

        List<OrderDetail> orderDetails = Collections.singletonList(OrderDetail.builder()
                .id("1")
                .quantity(2)
                .menu(menu)
                .price(1000L)
                .order(order)
                .build());

        order.setOrderDetails(orderDetails);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        OrderResponse response = orderService.getById(order.getId());

        verify(orderRepository, times(1)).findById(order.getId());
        assertNotNull(response);
        assertEquals(response.getOrderId(), order.getId());
    }

    @Test
    void getById_WhenOrderNotFound_ShouldThrowNotFoundException() {
        when(orderRepository.findById(anyString())).thenReturn(Optional.empty());
        ResponseStatusException responseStatusException= assertThrows(ResponseStatusException.class, () -> orderService.getById("123"));
        assertEquals("404 NOT_FOUND \"Id not found\"", responseStatusException.getMessage());
        verify(orderRepository, times(1)).findById(anyString());
    }


    @Test
    void getAll_WhenOrderFound_ShouldReturnPage() {
        Customer customer= Customer.builder()
                .id("1")
                .name("soleh")
                .phoneNumber("08888")
                .build();

        Table table= Table.builder()
                .tableName("T01")
                .id("1")
                .build();
        Order order = Order.builder()
                .id("1")
                .customer(customer)
                .table(table)
                .transDate(LocalDateTime.now())
                .build();

        List<OrderDetail> orderDetails = Collections.singletonList(OrderDetail.builder()
                .id("1")
                .quantity(2)
                .price(1000L)
                .order(order)
                .build());

        order.setOrderDetails(orderDetails);

        PagingRequest pagingRequest= PagingRequest.builder()
                .page(1)
                .size(5)
                .build();

        Pageable pageable= PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize());
        Page<Order> ordersPage= new PageImpl<>(Collections.singletonList(order), pageable, 1);
        when(orderRepository.findAll(pageable)).thenReturn(ordersPage);

        Page<Order> orders = orderService.getAll(pagingRequest);

        verify(orderRepository, times(1)).findAll(pageable);
        assertEquals(1, orders.getTotalElements());

    }

    @Test
    void getAll_WhenOrderNotFound_ShouldReturnEmpty() {
        PagingRequest pagingRequest= PagingRequest.builder()
                .page(1)
                .size(5)
                .build();
        Pageable pageable= PageRequest.of(pagingRequest.getPage()-1, pagingRequest.getSize());
        Page<Order> ordersPage= new PageImpl<>(List.of(), pageable, 0);
        when(orderRepository.findAll(pageable)).thenReturn(ordersPage);
        Page<Order> orders = orderService.getAll(pagingRequest);
        assertNotNull(orders.getContent());
        assertTrue(orders.getContent().isEmpty());

    }






}