package com.frauddetection.app.network;

import com.google.gson.annotations.SerializedName;

public class HealthCheck {
    
    @SerializedName("status")
    private String status;
    
    @SerializedName("timestamp")
    private String timestamp;
    
    @SerializedName("version")
    private String version;
    
    @SerializedName("uptime")
    private long uptime;
    
    @SerializedName("model_status")
    private String modelStatus;
    
    @SerializedName("database_status")
    private String databaseStatus;
    
    @SerializedName("response_time")
    private long responseTime;
    
    public HealthCheck() {
        // Default constructor for JSON deserialization
    }
    
    public HealthCheck(String status, String timestamp, String version) {
        this.status = status;
        this.timestamp = timestamp;
        this.version = version;
    }
    
    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    
    public long getUptime() { return uptime; }
    public void setUptime(long uptime) { this.uptime = uptime; }
    
    public String getModelStatus() { return modelStatus; }
    public void setModelStatus(String modelStatus) { this.modelStatus = modelStatus; }
    
    public String getDatabaseStatus() { return databaseStatus; }
    public void setDatabaseStatus(String databaseStatus) { this.databaseStatus = databaseStatus; }
    
    public long getResponseTime() { return responseTime; }
    public void setResponseTime(long responseTime) { this.responseTime = responseTime; }
} 