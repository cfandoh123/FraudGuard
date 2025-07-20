package com.frauddetection.app.network;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FraudAnalysisResult {
    
    @SerializedName("transaction_id")
    private String transactionId;
    
    @SerializedName("fraud_score")
    private double fraudScore;
    
    @SerializedName("risk_level")
    private String riskLevel;
    
    @SerializedName("is_fraudulent")
    private boolean isFraudulent;
    
    @SerializedName("confidence")
    private double confidence;
    
    @SerializedName("risk_factors")
    private List<RiskFactor> riskFactors;
    
    @SerializedName("recommendation")
    private String recommendation;
    
    @SerializedName("analysis_timestamp")
    private String analysisTimestamp;
    
    @SerializedName("model_version")
    private String modelVersion;
    
    public FraudAnalysisResult() {
        // Default constructor for JSON deserialization
    }
    
    public FraudAnalysisResult(String transactionId, double fraudScore, String riskLevel, 
                             boolean isFraudulent, double confidence) {
        this.transactionId = transactionId;
        this.fraudScore = fraudScore;
        this.riskLevel = riskLevel;
        this.isFraudulent = isFraudulent;
        this.confidence = confidence;
    }
    
    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public double getFraudScore() { return fraudScore; }
    public void setFraudScore(double fraudScore) { this.fraudScore = fraudScore; }
    
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    
    public boolean isFraudulent() { return isFraudulent; }
    public void setFraudulent(boolean fraudulent) { isFraudulent = fraudulent; }
    
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }
    
    public List<RiskFactor> getRiskFactors() { return riskFactors; }
    public void setRiskFactors(List<RiskFactor> riskFactors) { this.riskFactors = riskFactors; }
    
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    
    public String getAnalysisTimestamp() { return analysisTimestamp; }
    public void setAnalysisTimestamp(String analysisTimestamp) { this.analysisTimestamp = analysisTimestamp; }
    
    public String getModelVersion() { return modelVersion; }
    public void setModelVersion(String modelVersion) { this.modelVersion = modelVersion; }
    
    public static class RiskFactor {
        @SerializedName("factor_name")
        private String factorName;
        
        @SerializedName("factor_description")
        private String factorDescription;
        
        @SerializedName("risk_score")
        private double riskScore;
        
        @SerializedName("factor_weight")
        private double factorWeight;
        
        public RiskFactor() {}
        
        public RiskFactor(String factorName, String factorDescription, double riskScore, double factorWeight) {
            this.factorName = factorName;
            this.factorDescription = factorDescription;
            this.riskScore = riskScore;
            this.factorWeight = factorWeight;
        }
        
        public String getFactorName() { return factorName; }
        public void setFactorName(String factorName) { this.factorName = factorName; }
        
        public String getFactorDescription() { return factorDescription; }
        public void setFactorDescription(String factorDescription) { this.factorDescription = factorDescription; }
        
        public double getRiskScore() { return riskScore; }
        public void setRiskScore(double riskScore) { this.riskScore = riskScore; }
        
        public double getFactorWeight() { return factorWeight; }
        public void setFactorWeight(double factorWeight) { this.factorWeight = factorWeight; }
    }
} 