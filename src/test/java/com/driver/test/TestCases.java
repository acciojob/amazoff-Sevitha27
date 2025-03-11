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

import com.driver.Application;
import com.driver.OrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCases {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        // Setup logic if required
    }

    //1
    @Test
    public void testAddOrder() throws Exception {
        String requestBody = "{\"id\":\"123\", \"deliveryTime\":\"12:30\"}";

        mockMvc.perform(post("/orders/add-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())  // <-- Prints response for debugging
                .andExpect(status().isCreated());
    }



    //2
    @Test
    public void testAddPartner() throws Exception {
        mockMvc.perform(post("/orders/add-partner/p3"))
                .andExpect(status().isCreated());
    }

    //3
    @Test
    public void testAssignOrderToPartner() throws Exception {
        mockMvc.perform(put("/orders/add-order-partner-pair?orderId=o3&partnerId=p3"))
                .andExpect(status().isCreated());
    }

    //4
    @Test
    public void testGetOrderById() throws Exception {
        mockMvc.perform(get("/orders/get-order-by-id/o1"))
                .andExpect(status().isCreated());
    }

    //5
    @Test
    public void testGetPartnerById() throws Exception {
        mockMvc.perform(get("/orders/get-partner-by-id/p3"))
                .andExpect(status().isCreated());
    }

    //6
    @Test
    public void testGetOrderCountForPartner() throws Exception {
        mockMvc.perform(get("/orders/get-order-count-by-partner-id/p3"))
                .andExpect(status().isCreated());
    }

    //7
    @Test
    public void testGetOrdersByPartner() throws Exception {
        mockMvc.perform(get("/orders/get-orders-by-partner-id/p3"))
                .andExpect(status().isCreated());
    }

    //8
    @Test
    public void testGetAllOrders() throws Exception {
        mockMvc.perform(get("/orders/get-all-orders"))
                .andExpect(status().isCreated());
    }

    //9
    @Test
    public void testGetUnassignedOrderCount() throws Exception {
        mockMvc.perform(get("/orders/get-count-of-unassigned-orders"))
                .andExpect(status().isCreated());
    }

    //10
    @Test
    public void testGetOrdersLeftAfterTime() throws Exception {
        mockMvc.perform(get("/orders/get-count-of-orders-left-after-given-time/10:00/p2"))
                .andExpect(status().isCreated());
    }

    //11
    @Test
    public void testGetLastDeliveryTimeForPartner() throws Exception {
        mockMvc.perform(get("/orders/get-last-delivery-time/p3"))
                .andExpect(status().isCreated());
    }

    //12
    @Test
    public void testDeletePartner() throws Exception {
        mockMvc.perform(delete("/orders/delete-partner-by-id/p3"))
                .andExpect(status().isCreated());
    }

    //13
    @Test
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/delete-order-by-id/o3"))
                .andExpect(status().isCreated());
    }
}


