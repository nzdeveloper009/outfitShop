package com.nzdeveloper009.affordablefunctionoutfit.HelperClasses;

public class ModelProduct {
    String productAddress,productDescription,productColor,originalPrice,SecurityPrice,brandName,username,imgUri;

    public ModelProduct() {
    }

    public ModelProduct(String productAddress, String productDescription, String productColor, String originalPrice, String securityPrice, String brandName, String username, String imgUri) {
        this.productAddress = productAddress;
        this.productDescription = productDescription;
        this.productColor = productColor;
        this.originalPrice = originalPrice;
        SecurityPrice = securityPrice;
        this.brandName = brandName;
        this.username = username;
        this.imgUri = imgUri;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
