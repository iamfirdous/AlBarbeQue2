package com.dev.firdous.al_barbeque.models;

/**
 * Created by firdous on 9/9/17.
 */

public class FoodItem {

    private String itemName;
    private String itemId;
    private String restaurantId;
    private float price;
    private float totalPrice;
    private boolean isAvailable;
    private String description;
    private String photoLocation;
    private String uid;
    private String orderId;
    private int quantity;
    public FoodItem() {
    }

    public FoodItem(String itemName, String itemId, String restaurantId, float price) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.restaurantId = restaurantId;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotalPrice() {
        totalPrice = price * quantity;
        return totalPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
