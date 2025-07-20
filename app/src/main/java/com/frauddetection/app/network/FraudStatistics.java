package com.frauddetection.app.network;

import com.google.gson.annotations.SerializedName;

public class FraudStatistics {
    
    @SerializedName("total_transactions")
    private int totalTransactions;
    
    @SerializedName("fraudulent_transactions")
    private int fraudulentTransactions;
    
    @SerializedName("suspicious_transactions")
    private int suspiciousTransactions;
    
    @SerializedName("safe_transactions")
    private int safeTransactions;
    
    @SerializedName("fraud_rate")
    private double fraudRate;
    
    @SerializedName("accuracy_rate")
    private double accuracyRate;
    
    @SerializedName("false_positive_rate")
    private double falsePositiveRate;
    
    @SerializedName("false_negative_rate")
    private double falseNegativeRate;
    
    @SerializedName("average_fraud_score")
    private double averageFraudScore;
    
    @SerializedName("top_risk_factors")
    private String[] topRiskFactors;
    
    @SerializedName("model_performance")
    private ModelPerformance modelPerformance;
    
    public FraudStatistics() {
        // Default constructor for JSON deserialization
    }
    
    public FraudStatistics(int totalTransactions, int fraudulentTransactions, 
                         int suspiciousTransactions, int safeTransactions, 
                         double fraudRate, double accuracyRate) {
        this.totalTransactions = totalTransactions;
        this.fraudulentTransactions = fraudulentTransactions;
        this.suspiciousTransactions = suspiciousTransactions;
        this.safeTransactions = safeTransactions;
        this.fraudRate = fraudRate;
        this.accuracyRate = accuracyRate;
    }
    
    // Getters and Setters
    public int getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(int totalTransactions) { this.totalTransactions = totalTransactions; }
    
    public int getFraudulentTransactions() { return fraudulentTransactions; }
    public void setFraudulentTransactions(int fraudulentTransactions) { this.fraudulentTransactions = fraudulentTransactions; }
    
    public int getSuspiciousTransactions() { return suspiciousTransactions; }
    public void setSuspiciousTransactions(int suspiciousTransactions) { this.suspiciousTransactions = suspiciousTransactions; }
    
    public int getSafeTransactions() { return safeTransactions; }
    public void setSafeTransactions(int safeTransactions) { this.safeTransactions = safeTransactions; }
    
    public double getFraudRate() { return fraudRate; }
    public void setFraudRate(double fraudRate) { this.fraudRate = fraudRate; }
    
    public double getAccuracyRate() { return accuracyRate; }
    public void setAccuracyRate(double accuracyRate) { this.accuracyRate = accuracyRate; }
    
    public double getFalsePositiveRate() { return falsePositiveRate; }
    public void setFalsePositiveRate(double falsePositiveRate) { this.falsePositiveRate = falsePositiveRate; }
    
    public double getFalseNegativeRate() { return falseNegativeRate; }
    public void setFalseNegativeRate(double falseNegativeRate) { this.falseNegativeRate = falseNegativeRate; }
    
    public double getAverageFraudScore() { return averageFraudScore; }
    public void setAverageFraudScore(double averageFraudScore) { this.averageFraudScore = averageFraudScore; }
    
    public String[] getTopRiskFactors() { return topRiskFactors; }
    public void setTopRiskFactors(String[] topRiskFactors) { this.topRiskFactors = topRiskFactors; }
    
    public ModelPerformance getModelPerformance() { return modelPerformance; }
    public void setModelPerformance(ModelPerformance modelPerformance) { this.modelPerformance = modelPerformance; }
    
    public static class ModelPerformance {
        @SerializedName("precision")
        private double precision;
        
        @SerializedName("recall")
        private double recall;
        
        @SerializedName("f1_score")
        private double f1Score;
        
        @SerializedName("auc_score")
        private double aucScore;
        
        @SerializedName("training_accuracy")
        private double trainingAccuracy;
        
        @SerializedName("validation_accuracy")
        private double validationAccuracy;
        
        public ModelPerformance() {}
        
        public ModelPerformance(double precision, double recall, double f1Score, double aucScore) {
            this.precision = precision;
            this.recall = recall;
            this.f1Score = f1Score;
            this.aucScore = aucScore;
        }
        
        public double getPrecision() { return precision; }
        public void setPrecision(double precision) { this.precision = precision; }
        
        public double getRecall() { return recall; }
        public void setRecall(double recall) { this.recall = recall; }
        
        public double getF1Score() { return f1Score; }
        public void setF1Score(double f1Score) { this.f1Score = f1Score; }
        
        public double getAucScore() { return aucScore; }
        public void setAucScore(double aucScore) { this.aucScore = aucScore; }
        
        public double getTrainingAccuracy() { return trainingAccuracy; }
        public void setTrainingAccuracy(double trainingAccuracy) { this.trainingAccuracy = trainingAccuracy; }
        
        public double getValidationAccuracy() { return validationAccuracy; }
        public void setValidationAccuracy(double validationAccuracy) { this.validationAccuracy = validationAccuracy; }
    }
} 