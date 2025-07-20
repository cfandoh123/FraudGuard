package com.frauddetection.app.network;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ApiService {
    
    @POST("api/v1/analyze")
    Call<FraudAnalysisResult> analyzeTransaction(@Body TransactionData transaction);
    
    @GET("api/v1/transactions")
    Call<List<TransactionData>> getTransactionHistory();
    
    @POST("api/v1/upload")
    Call<Void> uploadTransaction(@Body TransactionData transaction);
    
    @GET("api/v1/statistics")
    Call<FraudStatistics> getFraudStatistics();
    
    @GET("api/v1/health")
    Call<HealthCheck> checkHealth();
} 