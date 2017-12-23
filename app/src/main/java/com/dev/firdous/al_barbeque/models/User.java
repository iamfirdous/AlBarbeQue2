package com.dev.firdous.al_barbeque.models;

import java.util.List;

/**
 * Created by firdous on 9/9/17.
 */

public class User {

    private String username;
    private String uid;
    private String phoneNumber;
    private String emailId;

    public User() {
    }

    public User(String username, String uid, String phoneNumber, String emailId) {
        this.username = username;
        this.uid = uid;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}
