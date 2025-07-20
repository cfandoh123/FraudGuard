package com.frauddetection.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.frauddetection.app.R;
import com.frauddetection.app.ml.BoostingAlgorithm;
import com.frauddetection.app.ml.DataSet;
import com.frauddetection.app.network.FraudDetectionService;
import com.frauddetection.app.network.TransactionData;
import com.frauddetection.app.network.FraudAnalysisResult;
import java.util.Date;
import java.util.Random;

public class TransactionActivity extends AppCompatActivity {
    
    private EditText amountEditText;
    private EditText merchantEditText;
    private EditText locationEditText;
    private Button analyzeButton;
    private TextView resultText;
    private TextView fraudScoreText;
    private TextView riskLevelText;
    private TextView confidenceText;
    private CardView resultCard;
    private FraudDetectionService fraudDetectionService;
    private BoostingAlgorithm mlModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        
        initializeViews();
        setupMLModel();
        setupNetworkService();
        setupClickListeners();
    }
    
    private void initializeViews() {
        amountEditText = findViewById(R.id.amount_edit_text);
        merchantEditText = findViewById(R.id.merchant_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
        analyzeButton = findViewById(R.id.analyze_button);
        resultText = findViewById(R.id.result_text);
        fraudScoreText = findViewById(R.id.fraud_score_text);
        riskLevelText = findViewById(R.id.risk_level_text);
        confidenceText = findViewById(R.id.confidence_text);
        resultCard = findViewById(R.id.result_card);
        
        // Initially hide result card
        resultCard.setVisibility(View.GONE);
    }
    
    private void setupMLModel() {
        try {
            // Initialize the ML model with training data
            // In a real app, this would load from assets or download from server
            DataSet trainingData = new DataSet("training_data.txt");
            mlModel = new BoostingAlgorithm(
                trainingData.getInput(),
                trainingData.getLabels(),
                trainingData.getLocations(),
                10  // number of clusters
            );
            
            // Train the model
            for (int i = 0; i < 50; i++) {
                mlModel.iterate();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to initialize ML model", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void setupNetworkService() {
        fraudDetectionService = new FraudDetectionService(this);
    }
    
    private void setupClickListeners() {
        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analyzeTransaction();
            }
        });
    }
    
    private void analyzeTransaction() {
        String amountStr = amountEditText.getText().toString();
        String merchant = merchantEditText.getText().toString();
        String location = locationEditText.getText().toString();
        
        if (amountStr.isEmpty() || merchant.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        
        try {
            double amount = Double.parseDouble(amountStr);
            
            // Create transaction data
            TransactionData transaction = createTransactionData(amount, merchant, location);
            
            // Show loading state
            analyzeButton.setEnabled(false);
            analyzeButton.setText("Analyzing...");
            
            // Analyze using both local ML model and remote server
            performLocalAnalysis(transaction);
            performRemoteAnalysis(transaction);
            
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
        }
    }
    
    private TransactionData createTransactionData(double amount, String merchant, String location) {
        // Create a sample transaction with features
        TransactionData transaction = new TransactionData();
        transaction.setTransactionId(generateTransactionId());
        transaction.setAmount(amount);
        transaction.setCurrency("USD");
        transaction.setMerchantName(merchant);
        transaction.setMerchantCategory("Retail");
        transaction.setTimestamp(new Date());
        
        // Set location data
        TransactionData.LocationData locationData = new TransactionData.LocationData();
        locationData.setCity(location);
        locationData.setCountry("US");
        locationData.setLatitude(40.7128); // Sample coordinates
        locationData.setLongitude(-74.0060);
        transaction.setLocation(locationData);
        
        // Set device info
        TransactionData.DeviceInfo deviceInfo = new TransactionData.DeviceInfo();
        deviceInfo.setDeviceId("android_device_001");
        deviceInfo.setDeviceType("Android");
        deviceInfo.setOsVersion("Android 12");
        deviceInfo.setAppVersion("1.0.0");
        transaction.setDeviceInfo(deviceInfo);
        
        // Set transaction features
        TransactionData.TransactionFeatures features = new TransactionData.TransactionFeatures();
        features.setHourOfDay(new Date().getHours());
        features.setDayOfWeek(new Date().getDay());
        features.setWeekend(new Date().getDay() == 0 || new Date().getDay() == 6);
        features.setDistanceFromHome(calculateDistanceFromHome(location));
        features.setAmountCategory(getAmountCategory(amount));
        features.setMerchantRiskScore(calculateMerchantRisk(merchant));
        transaction.setFeatures(features);
        
        return transaction;
    }
    
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
    
    private double calculateDistanceFromHome(String location) {
        // Simplified distance calculation
        return new Random().nextDouble() * 100;
    }
    
    private String getAmountCategory(double amount) {
        if (amount < 50) return "LOW";
        else if (amount < 200) return "MEDIUM";
        else return "HIGH";
    }
    
    private double calculateMerchantRisk(String merchant) {
        // Simplified merchant risk calculation
        if (merchant.toLowerCase().contains("online") || merchant.toLowerCase().contains("foreign")) {
            return 0.7;
        }
        return 0.3;
    }
    
    private void performLocalAnalysis(TransactionData transaction) {
        try {
            // Convert transaction to feature vector for ML model
            int[] features = convertTransactionToFeatures(transaction);
            
            // Get prediction from local ML model
            int prediction = mlModel.predict(features);
            
            // Update UI with local results
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateLocalResults(prediction);
                }
            });
            
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TransactionActivity.this, "Local analysis failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    
    private void performRemoteAnalysis(TransactionData transaction) {
        fraudDetectionService.analyzeTransaction(transaction, new FraudDetectionService.Callback<FraudAnalysisResult>() {
            @Override
            public void onSuccess(FraudAnalysisResult result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateRemoteResults(result);
                        showResults();
                    }
                });
            }
            
            @Override
            public void onError(String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TransactionActivity.this, "Remote analysis failed: " + error, Toast.LENGTH_SHORT).show();
                        showResults();
                    }
                });
            }
        });
    }
    
    private int[] convertTransactionToFeatures(TransactionData transaction) {
        // Convert transaction data to feature vector for ML model
        // This is a simplified conversion - in reality, you'd need more sophisticated feature engineering
        int[] features = new int[21]; // Based on the training data format
        
        // Map transaction properties to features
        features[0] = (int) (transaction.getAmount() / 100); // Amount category
        features[1] = transaction.getFeatures().getHourOfDay();
        features[2] = transaction.getFeatures().getDayOfWeek();
        features[3] = transaction.getFeatures().isWeekend() ? 1 : 0;
        features[4] = (int) (transaction.getFeatures().getDistanceFromHome() / 10);
        features[5] = (int) (transaction.getFeatures().getMerchantRiskScore() * 10);
        
        // Fill remaining features with zeros or calculated values
        for (int i = 6; i < features.length; i++) {
            features[i] = new Random().nextInt(10);
        }
        
        return features;
    }
    
    private void updateLocalResults(int prediction) {
        String result = prediction == 1 ? "FRAUDULENT" : "SAFE";
        resultText.setText("Local Analysis: " + result);
    }
    
    private void updateRemoteResults(FraudAnalysisResult result) {
        if (result != null) {
            fraudScoreText.setText("Fraud Score: " + String.format("%.2f", result.getFraudScore()));
            riskLevelText.setText("Risk Level: " + result.getRiskLevel());
            confidenceText.setText("Confidence: " + String.format("%.1f", result.getConfidence() * 100) + "%");
        }
    }
    
    private void showResults() {
        resultCard.setVisibility(View.VISIBLE);
        analyzeButton.setEnabled(true);
        analyzeButton.setText("Analyze Transaction");
    }
} 