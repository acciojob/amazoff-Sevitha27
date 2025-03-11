package com.driver;

import java.util.*;


import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public boolean orderExists(String orderId) {
        return orderMap.containsKey(orderId);
    }

    public boolean partnerExists(String partnerId) {
        return partnerMap.containsKey(partnerId);
    }
    //---------------

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        partnerMap.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
            if (!orderMap.containsKey(orderId) || !partnerMap.containsKey(partnerId)) return; // Validate input

            partnerToOrderMap.computeIfAbsent(partnerId, k -> new HashSet<>()).add(orderId);
            orderToPartnerMap.put(orderId, partnerId);


            DeliveryPartner partner = partnerMap.get(partnerId);
            int updatedOrderCount = partnerToOrderMap.get(partnerId).size();
            partner.setNumberOfOrders(updatedOrderCount);
        }
    }
    public boolean isOrderAssigned(String orderId) {
        return orderToPartnerMap.containsKey(orderId);
    }


    public Order findOrderById(String orderId){
        // your code here
        //System.out.println("Checking orders in orderMap: " + orderMap);
        if (!orderMap.containsKey(orderId)) {
            System.out.println("Order not found: " + orderId); // Debugging log
            return null;
        }
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        return partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).size();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        return new ArrayList<>(partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()));
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        return new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        if (partnerToOrderMap.containsKey(partnerId)) {
            for (String orderId : partnerToOrderMap.get(partnerId)) {
                orderToPartnerMap.remove(orderId);
            }
        }
        partnerToOrderMap.remove(partnerId);
        partnerMap.remove(partnerId);
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        String partnerId = orderToPartnerMap.remove(orderId);
        if (partnerId != null) {
            partnerToOrderMap.get(partnerId).remove(orderId);
            partnerMap.get(partnerId).setNumberOfOrders(partnerToOrderMap.get(partnerId).size());
        }
        orderMap.remove(orderId);
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        //System.out.println(orderMap.size()+"   "+orderToPartnerMap.size());
        return orderMap.size() - orderToPartnerMap.size();
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        int time = convertTimeToMinutes(timeString);
        return (int) partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).stream()
                .map(orderMap::get)
                .filter(order -> order.getDeliveryTime() > time)
                .count();
    }
    private int convertTimeToMinutes(String time) {
        String[] parts = time.split(":");
        return (Integer.parseInt(parts[0]) * 60) + Integer.parseInt(parts[1]);
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        OptionalInt maxTime = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).stream()
                .mapToInt(orderId -> orderMap.get(orderId).getDeliveryTime())
                .max();

        return maxTime.isPresent() ? convertMinutesToTimeString(maxTime.getAsInt()) : "00:00";
    }
    private String convertMinutesToTimeString(int minutes) {
        return String.format("%02d:%02d", minutes / 60, minutes % 60);
    }

}