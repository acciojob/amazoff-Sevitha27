//package com.driver.test;
//
//import com.driver.Application;
//import com.driver.DeliveryPartner;
//import com.driver.Order;
//import com.driver.OrderController;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer;
//
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.HashSet;
//import java.util.List;
//
//@SpringBootTest(classes = Application.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class TestCases {
//
//}

package com.driver.test;

import com.driver.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCases {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Test 1: Add Order (Already Given)
    @Test
    public void testAddOrder() {
        Order order = new Order("123", "12:30");
        orderService.addOrder(order);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    // Test 2: Add Partner
    @Test
    public void testAddPartner() {
        String partnerId = "P1";
        orderService.addPartner(partnerId);
        verify(orderRepository, times(1)).savePartner(partnerId);
    }

    // Test 3: Assign Order to Partner
    @Test
    public void testAssignOrderToPartner() {
        String orderId = "123";
        String partnerId = "P1";
        when(orderRepository.orderExists(orderId)).thenReturn(true);
        when(orderRepository.partnerExists(partnerId)).thenReturn(true);

        orderService.createOrderPartnerPair(orderId, partnerId);

        verify(orderRepository, times(1)).saveOrderPartnerMap(orderId, partnerId);
    }

    //  Test 4: Get Order by ID
    @Test
    public void testGetOrderById() {
        Order order = new Order("123", "12:30");
        when(orderRepository.findOrderById("123")).thenReturn(order);

        Order result = orderService.getOrderById("123");

        assertEquals(order, result);
        verify(orderRepository, times(1)).findOrderById("123");
    }

    // Test 5: Get Partner by ID
    @Test
    public void testGetPartnerById() {
        DeliveryPartner partner = new DeliveryPartner("P1");
        when(orderRepository.findPartnerById("P1")).thenReturn(partner);

        DeliveryPartner result = orderService.getPartnerById("P1");

        assertEquals(partner, result);
        verify(orderRepository, times(1)).findPartnerById("P1");
    }

    //  Test 6: Get Order Count for a Partner
    @Test
    public void testGetOrderCountForPartner() {
        String partnerId = "P1";
        when(orderRepository.findOrderCountByPartnerId(partnerId)).thenReturn(0); // Handle case when no orders

        int result = orderService.getOrderCountByPartnerId(partnerId);

        assertEquals(0, result); // Check for default value
        verify(orderRepository, times(1)).findOrderCountByPartnerId(partnerId);
    }

    //  Test 7: Get Orders Assigned to Partner
    @Test
    public void testGetOrdersByPartner() {
        List<String> orders = Arrays.asList("123", "456");
        when(orderRepository.findOrdersByPartnerId("P1")).thenReturn(orders);

        List<String> result = orderService.getOrdersByPartnerId("P1");

        assertEquals(orders, result);
        verify(orderRepository, times(1)).findOrdersByPartnerId("P1");
    }

    //  Test 8: Get All Orders
    @Test
    public void testGetAllOrders() {
        List<String> orders = Arrays.asList("123", "456", "789");
        when(orderRepository.findAllOrders()).thenReturn(orders);

        List<String> result = orderService.getAllOrders();

        assertEquals(orders, result);
        verify(orderRepository, times(1)).findAllOrders();
    }

    // Test 9: Get Count of Unassigned Orders
    @Test
    public void testGetUnassignedOrderCount() {
        when(orderRepository.findCountOfUnassignedOrders()).thenReturn(2);

        int result = orderService.getCountOfUnassignedOrders();

        assertEquals(2, result);
        verify(orderRepository, times(1)).findCountOfUnassignedOrders();
    }


    // Test 10: Get Orders Left After Given Time
    @Test
    public void testGetOrdersLeftAfterTime() {
        when(orderRepository.findOrdersLeftAfterGivenTimeByPartnerId("12:30", "P1")).thenReturn(3);

        int result = orderService.getOrdersLeftAfterGivenTimeByPartnerId("12:30", "P1");

        assertEquals(3, result);
        verify(orderRepository, times(1)).findOrdersLeftAfterGivenTimeByPartnerId("12:30", "P1");
    }

    // Test 11: Get Last Delivery Time for a Partner
    @Test
    public void testGetLastDeliveryTimeForPartner() {
        when(orderRepository.findLastDeliveryTimeByPartnerId("P1")).thenReturn("18:45");

        String result = orderService.getLastDeliveryTimeByPartnerId("P1");

        assertEquals("18:45", result);
        verify(orderRepository, times(1)).findLastDeliveryTimeByPartnerId("P1");
    }

    // Test 12: Delete Partner
    @Test
    public void testDeletePartner() {
        String partnerId = "P1";
        orderService.deletePartner(partnerId);
        verify(orderRepository, times(1)).deletePartner(partnerId);
    }

    // Test 13: Delete Order
    @Test
    public void testDeleteOrder() {
        String orderId = "123";
        orderService.deleteOrder(orderId);
        verify(orderRepository, times(1)).deleteOrder(orderId);
    }

}
