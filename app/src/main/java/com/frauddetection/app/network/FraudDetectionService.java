package com.frauddetection.app.network;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FraudDetectionService {
    
    private static final String BASE_URL = "https://api.fraudguard.com/";
    private static final String TAG = "FraudDetectionService";
    
    private ApiService apiService;
    private OkHttpClient httpClient;
    private Context context;
    
    public interface Callback<T> {
        void onSuccess(T result);
        void onError(String error);
    }
    
    public FraudDetectionService() {
        setupRetrofit();
    }
    
    public FraudDetectionService(Context context) {
        this.context = context;
        setupRetrofit();
    }
    
    private void setupRetrofit() {
        // Create logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        // Create HTTP client with security features
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new SecurityInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        
        // Create Gson with custom date format
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .create();
        
        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        
        apiService = retrofit.create(ApiService.class);
    }
    
    public void analyzeTransaction(TransactionData transaction, Callback<FraudAnalysisResult> callback) {
        Call<FraudAnalysisResult> call = apiService.analyzeTransaction(transaction);
        call.enqueue(new retrofit2.Callback<FraudAnalysisResult>() {
            @Override
            public void onResponse(Call<FraudAnalysisResult> call, retrofit2.Response<FraudAnalysisResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Analysis failed: " + response.code());
                }
            }
            
            @Override
            public void onFailure(Call<FraudAnalysisResult> call, Throwable t) {
                Log.e(TAG, "Network error", t);
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    
    public void getTransactionHistory(Callback<List<TransactionData>> callback) {
        Call<List<TransactionData>> call = apiService.getTransactionHistory();
        call.enqueue(new retrofit2.Callback<List<TransactionData>>() {
            @Override
            public void onResponse(Call<List<TransactionData>> call, retrofit2.Response<List<TransactionData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load history: " + response.code());
                }
            }
            
            @Override
            public void onFailure(Call<List<TransactionData>> call, Throwable t) {
                Log.e(TAG, "Network error", t);
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    
    public void uploadTransactionData(TransactionData transaction, Callback<Void> callback) {
        Call<Void> call = apiService.uploadTransaction(transaction);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Upload failed: " + response.code());
                }
            }
            
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Network error", t);
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    
    public void getFraudStatistics(Callback<FraudStatistics> callback) {
        Call<FraudStatistics> call = apiService.getFraudStatistics();
        call.enqueue(new retrofit2.Callback<FraudStatistics>() {
            @Override
            public void onResponse(Call<FraudStatistics> call, retrofit2.Response<FraudStatistics> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load statistics: " + response.code());
                }
            }
            
            @Override
            public void onFailure(Call<FraudStatistics> call, Throwable t) {
                Log.e(TAG, "Network error", t);
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    
    // Security interceptor for adding authentication headers
    private static class SecurityInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            
            // Add security headers
            Request request = original.newBuilder()
                    .header("Authorization", "Bearer " + getAuthToken())
                    .header("X-API-Key", getApiKey())
                    .header("Content-Type", "application/json")
                    .method(original.method(), original.body())
                    .build();
            
            return chain.proceed(request);
        }
        
        private String getAuthToken() {
            // In a real app, this would be retrieved from secure storage
            return "your-auth-token";
        }
        
        private String getApiKey() {
            // In a real app, this would be retrieved from secure storage
            return "your-api-key";
        }
    }
} 