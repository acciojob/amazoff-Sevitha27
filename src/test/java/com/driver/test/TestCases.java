//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(
        classes = {Application.class}
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCases {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderController orderController;

    public TestCases() {
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testAddOrder() throws Exception {
        String requestBody = "{\"id\":\"123\", \"deliveryTime\":\"12:30\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.post("/orders/add-order", new Object[0]).contentType(MediaType.APPLICATION_JSON).content(requestBody)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testAddPartner() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/orders/add-partner/p3", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testAssignOrderToPartner() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/orders/add-order-partner-pair?orderId=o3&partnerId=p3", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetOrderById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/get-order-by-id/o1", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetPartnerById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/get-partner-by-id/p3", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetOrderCountForPartner() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/get-order-count-by-partner-id/p3", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetOrdersByPartner() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/get-orders-by-partner-id/p3", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetAllOrders() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/get-all-orders", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetUnassignedOrderCount() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/get-count-of-unassigned-orders", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetOrdersLeftAfterTime() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/get-count-of-orders-left-after-given-time/10:00/p2", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetLastDeliveryTimeForPartner() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/get-last-delivery-time/p3", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testDeletePartner() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/orders/delete-partner-by-id/p3", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/orders/delete-order-by-id/o3", new Object[0])).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
