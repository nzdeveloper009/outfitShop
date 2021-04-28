package com.nzdeveloper009.affordablefunctionoutfit.HelperClasses;

public class ModelOrderBuyer {
    String orderId,orderTime,orderStatus,orderCost,orderBy,orderTo,orderImgUri,orderBrandName,orderPhoneNo,orderNIC,orderAddress,cat;

    public ModelOrderBuyer() {
    }

    public ModelOrderBuyer(String orderId, String orderTime, String orderStatus, String orderCost, String orderBy, String orderTo, String orderImgUri, String orderBrandName, String orderPhoneNo, String orderNIC, String orderAddress,String cat) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.orderCost = orderCost;
        this.orderBy = orderBy;
        this.orderTo = orderTo;
        this.orderImgUri = orderImgUri;
        this.orderBrandName = orderBrandName;
        this.orderPhoneNo = orderPhoneNo;
        this.orderNIC = orderNIC;
        this.orderAddress = orderAddress;
        this.cat = cat;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderTo() {
        return orderTo;
    }

    public void setOrderTo(String orderTo) {
        this.orderTo = orderTo;
    }

    public String getOrderImgUri() {
        return orderImgUri;
    }

    public void setOrderImgUri(String orderImgUri) {
        this.orderImgUri = orderImgUri;
    }

    public String getOrderBrandName() {
        return orderBrandName;
    }

    public void setOrderBrandName(String orderBrandName) {
        this.orderBrandName = orderBrandName;
    }

    public String getOrderPhoneNo() {
        return orderPhoneNo;
    }

    public void setOrderPhoneNo(String orderPhoneNo) {
        this.orderPhoneNo = orderPhoneNo;
    }

    public String getOrderNIC() {
        return orderNIC;
    }

    public void setOrderNIC(String orderNIC) {
        this.orderNIC = orderNIC;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
