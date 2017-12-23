package com.dev.firdous.al_barbeque.models;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by firdous on 9/9/17.
 */

public class Order {

    private String orderId;
    private String uid;
    private List<FoodItem> items;
    private DateTime dateTime;
    private float totalAmount;
    private int paymentMethod;
    private int orderStatus;

    public Order() {
        items = new ArrayList<>();
        dateTime = new DateTime();
    }

    public Order(String orderId, String uid, DateTime dateTime, int paymentMethod) {
        this.orderId = orderId;
        this.uid = uid;
        this.dateTime = dateTime;
        this.paymentMethod = paymentMethod;
        items = new ArrayList<>();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<FoodItem> getItems() {
        return items;
    }

    public void setItems(List<FoodItem> items) {
        this.items = items;
    }

    public void addItem(FoodItem item){
        if(item != null){
            items.add(item);
        }
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public float getTotalAmount() {
        for(FoodItem item : items){
            totalAmount = totalAmount + item.getPrice();
        }
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
