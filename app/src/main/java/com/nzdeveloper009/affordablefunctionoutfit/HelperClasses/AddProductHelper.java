package com.nzdeveloper009.affordablefunctionoutfit.HelperClasses;

import android.net.Uri;

public class AddProductHelper {

    String username,productCategory,productAddress,productDescription,productColor,originalPrice,SecurityPrice,brandName,timestamp;

    String imgUri;

    public AddProductHelper() {
    }



    public AddProductHelper(String username,String productCategory, String productAddress, String productDescription, String productColor, String originalPrice, String securityPrice, String brandName, String timestamp, String imgUri) {
        this.username = username;
        this.productCategory = productCategory;
        this.productAddress = productAddress;
        this.productDescription = productDescription;
        this.productColor = productColor;
        this.originalPrice = originalPrice;
        SecurityPrice = securityPrice;
        this.brandName = brandName;
        this.timestamp = timestamp;
        this.imgUri = imgUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductAddress() {
        return productAddress;
    }

    public void setProductAddress(String productAddress) {
        this.productAddress = productAddress;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getSecurityPrice() {
        return SecurityPrice;
    }

    public void setSecurityPrice(String securityPrice) {
        SecurityPrice = securityPrice;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
