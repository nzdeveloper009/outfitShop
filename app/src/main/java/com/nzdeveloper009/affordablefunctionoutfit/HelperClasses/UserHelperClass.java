package com.nzdeveloper009.affordablefunctionoutfit.HelperClasses;

public class UserHelperClass {
    String fullName, username, phoneNo, email, password, nicNo;
    String userType;

    public UserHelperClass() {
    }

    public UserHelperClass(String fullName, String username, String phoneNo, String email, String password, String nicNo, String userType) {
        this.fullName = fullName;
        this.username = username;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;
        this.nicNo = nicNo;
        this.userType = userType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNicNo() {
        return nicNo;
    }

    public void setNicNo(String nicNo) {
        this.nicNo = nicNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
