package com.frauddetection.app.network;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class TransactionData {
    
    @SerializedName("transaction_id")
    private String transactionId;
    
    @SerializedName("amount")
    private double amount;
    
    @SerializedName("currency")
    private String currency;
    
    @SerializedName("merchant_name")
    private String merchantName;
    
    @SerializedName("merchant_category")
    private String merchantCategory;
    
    @SerializedName("location")
    private LocationData location;
    
    @SerializedName("timestamp")
    private Date timestamp;
    
    @SerializedName("card_number")
    private String cardNumber;
    
    @SerializedName("card_type")
    private String cardType;
    
    @SerializedName("user_id")
    private String userId;
    
    @SerializedName("device_info")
    private DeviceInfo deviceInfo;
    
    @SerializedName("transaction_features")
    private TransactionFeatures features;
    
    public TransactionData() {
        // Default constructor for JSON deserialization
    }
    
    public TransactionData(String transactionId, double amount, String currency, 
                         String merchantName, String merchantCategory, 
                         LocationData location, Date timestamp) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.merchantName = merchantName;
        this.merchantCategory = merchantCategory;
        this.location = location;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getMerchantName() { return merchantName; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }
    
    public String getMerchantCategory() { return merchantCategory; }
    public void setMerchantCategory(String merchantCategory) { this.merchantCategory = merchantCategory; }
    
    public LocationData getLocation() { return location; }
    public void setLocation(LocationData location) { this.location = location; }
    
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    
    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public DeviceInfo getDeviceInfo() { return deviceInfo; }
    public void setDeviceInfo(DeviceInfo deviceInfo) { this.deviceInfo = deviceInfo; }
    
    public TransactionFeatures getFeatures() { return features; }
    public void setFeatures(TransactionFeatures features) { this.features = features; }
    
    public static class LocationData {
        @SerializedName("latitude")
        private double latitude;
        
        @SerializedName("longitude")
        private double longitude;
        
        @SerializedName("city")
        private String city;
        
        @SerializedName("country")
        private String country;
        
        public LocationData() {}
        
        public LocationData(double latitude, double longitude, String city, String country) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.city = city;
            this.country = country;
        }
        
        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
        
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }
    
    public static class DeviceInfo {
        @SerializedName("device_id")
        private String deviceId;
        
        @SerializedName("device_type")
        private String deviceType;
        
        @SerializedName("os_version")
        private String osVersion;
        
        @SerializedName("app_version")
        private String appVersion;
        
        public DeviceInfo() {}
        
        public DeviceInfo(String deviceId, String deviceType, String osVersion, String appVersion) {
            this.deviceId = deviceId;
            this.deviceType = deviceType;
            this.osVersion = osVersion;
            this.appVersion = appVersion;
        }
        
        public String getDeviceId() { return deviceId; }
        public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
        
        public String getDeviceType() { return deviceType; }
        public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
        
        public String getOsVersion() { return osVersion; }
        public void setOsVersion(String osVersion) { this.osVersion = osVersion; }
        
        public String getAppVersion() { return appVersion; }
        public void setAppVersion(String appVersion) { this.appVersion = appVersion; }
    }
    
    public static class TransactionFeatures {
        @SerializedName("hour_of_day")
        private int hourOfDay;
        
        @SerializedName("day_of_week")
        private int dayOfWeek;
        
        @SerializedName("is_weekend")
        private boolean isWeekend;
        
        @SerializedName("distance_from_home")
        private double distanceFromHome;
        
        @SerializedName("amount_category")
        private String amountCategory;
        
        @SerializedName("merchant_risk_score")
        private double merchantRiskScore;
        
        public TransactionFeatures() {}
        
        public TransactionFeatures(int hourOfDay, int dayOfWeek, boolean isWeekend, 
                                double distanceFromHome, String amountCategory, double merchantRiskScore) {
            this.hourOfDay = hourOfDay;
            this.dayOfWeek = dayOfWeek;
            this.isWeekend = isWeekend;
            this.distanceFromHome = distanceFromHome;
            this.amountCategory = amountCategory;
            this.merchantRiskScore = merchantRiskScore;
        }
        
        public int getHourOfDay() { return hourOfDay; }
        public void setHourOfDay(int hourOfDay) { this.hourOfDay = hourOfDay; }
        
        public int getDayOfWeek() { return dayOfWeek; }
        public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }
        
        public boolean isWeekend() { return isWeekend; }
        public void setWeekend(boolean weekend) { isWeekend = weekend; }
        
        public double getDistanceFromHome() { return distanceFromHome; }
        public void setDistanceFromHome(double distanceFromHome) { this.distanceFromHome = distanceFromHome; }
        
        public String getAmountCategory() { return amountCategory; }
        public void setAmountCategory(String amountCategory) { this.amountCategory = amountCategory; }
        
        public double getMerchantRiskScore() { return merchantRiskScore; }
        public void setMerchantRiskScore(double merchantRiskScore) { this.merchantRiskScore = merchantRiskScore; }
    }
} 