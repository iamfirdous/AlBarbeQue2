package com.dev.firdous.al_barbeque.models;

/**
 * Created by firdous on 9/9/17.
 */

public class Restaurant {

    private String restaurantName;
    private String restaurantId;
    private String phoneNumber;
    private Address address;
    private boolean isOpen;
    private String photoLocation;

    public Restaurant() {
    }

    public Restaurant(String restaurantName, String restaurantId, String phoneNumber, Address address, String photoLocation) {
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.photoLocation = photoLocation;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }
}
