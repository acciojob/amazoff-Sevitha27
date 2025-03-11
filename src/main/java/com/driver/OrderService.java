package com.driver;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order){
        if (order == null || order.getId() == null || order.getId().isEmpty()) {
            System.out.println("Invalid order: Order ID cannot be null or empty.");
            return;
        }

        if (orderRepository.orderExists(order.getId())) {
            System.out.println("Order with ID " + order.getId() + " already exists.");
            return;
        }
        orderRepository.saveOrder(order);
        System.out.println("Order added successfully: " + order.getId());
    }

    public void addPartner(String partnerId){

        if (partnerId == null || partnerId.isEmpty()) {
            System.out.println("Invalid partner: Partner ID cannot be null or empty.");
            return;
        }

        if (orderRepository.partnerExists(partnerId)) {
            System.out.println("Partner with ID " + partnerId + " already exists.");
            return;
        }

        orderRepository.savePartner(partnerId);
        System.out.println("Partner added successfully: " + partnerId);
    }

    public void createOrderPartnerPair(String orderId, String partnerId){
        if (!orderRepository.orderExists(orderId)) {
            System.out.println("Order ID " + orderId + " not found.");
            return;
        }
        if (!orderRepository.partnerExists(partnerId)) {
            System.out.println("Partner ID " + partnerId + " not found.");
            return;
        }
        if (orderRepository.isOrderAssigned(orderId)) {
            System.out.println("Order ID " + orderId + " is already assigned to a partner.");
            return;
        }
        orderRepository.saveOrderPartnerMap(orderId, partnerId);
    }

    public Order getOrderById(String orderId){
        Order order = orderRepository.findOrderById(orderId);
        if (order == null) {
            System.out.println("Order with ID " + orderId + " not found.");
            return null;
        }
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId){
        DeliveryPartner partner = orderRepository.findPartnerById(partnerId);
        if (partner == null) {
            System.out.println("Partner with ID " + partnerId + " not found.");
            return null;
        }
        return partner;
    }

    public Integer getOrderCountByPartnerId(String partnerId){
        if (!orderRepository.partnerExists(partnerId)) {
            System.out.println("Partner ID " + partnerId + " not found.");
            return 0;
        }
        return orderRepository.findOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        if (!orderRepository.partnerExists(partnerId)) {
            System.out.println("Partner ID " + partnerId + " not found.");
            return null;
        }
        return orderRepository.findOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders(){
        List<String> orders = orderRepository.findAllOrders();

        if (orders == null) {
            System.out.println("Warning: Order repository returned null. Returning an empty list.");
            return new ArrayList<>();
        }
        return orders;
    }

    public void deletePartner(String partnerId){
        if (!orderRepository.partnerExists(partnerId)) {
            System.out.println("Warning: Partner with ID " + partnerId + " does not exist. Skipping deletion.");
            return;
        }
        orderRepository.deletePartner(partnerId);
    }

    public void deleteOrder(String orderId){
        if (!orderRepository.orderExists(orderId)) {
            System.out.println("Warning: Order with ID " + orderId + " does not exist. Skipping deletion.");
            return;
        }
        orderRepository.deleteOrder(orderId);
    }

    public Integer getCountOfUnassignedOrders(){
        return orderRepository.findCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        if (!orderRepository.partnerExists(partnerId)) {
            System.out.println("Warning: Partner with ID " + partnerId + " not found. Returning 0.");
            return 0;
        }
        return orderRepository.findOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        if (!orderRepository.partnerExists(partnerId)) {
            System.out.println("Warning: Partner with ID " + partnerId + " not found.");
            return null;
        }
        return orderRepository.findLastDeliveryTimeByPartnerId(partnerId);
    }
}