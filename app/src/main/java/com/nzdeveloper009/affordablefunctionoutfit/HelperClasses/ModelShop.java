package com.nzdeveloper009.affordablefunctionoutfit.HelperClasses;

public class ModelShop {
    String username,email,phoneNo;

    public ModelShop() {
    }

    public ModelShop(String username, String email, String phoneNo) {
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
