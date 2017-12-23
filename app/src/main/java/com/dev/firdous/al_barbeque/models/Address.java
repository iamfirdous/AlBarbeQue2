package com.dev.firdous.al_barbeque.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by firdous on 9/9/17.
 */

public class Address {

    private String addressId;
    private String uid;
    private String flatNo;
    private String address;
    private int pincode;
    private String landmark;
    private String addressType;
    private LatLng customerCoordinates;

    public Address() {
    }

    public Address(String addressId, String uid, String flatNo, String address, int pincode, String landmark, String addressType) {
        this.addressId = addressId;
        this.uid = uid;
        this.flatNo = flatNo;
        this.address = address;
        this.pincode = pincode;
        this.landmark = landmark;
        this.addressType = addressType;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public LatLng getCustomerCoordinates() {
        return customerCoordinates;
    }

    public void setCustomerCoordinates(LatLng customerCoordinates) {
        this.customerCoordinates = customerCoordinates;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
}
