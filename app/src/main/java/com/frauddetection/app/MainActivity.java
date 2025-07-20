package com.frauddetection.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.frauddetection.app.ui.DashboardActivity;
import com.frauddetection.app.ui.TransactionActivity;
import com.frauddetection.app.ui.SettingsActivity;
// Remove or comment out the following line if androidx.biometric is not used or not added as a dependency
// import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    
    private CardView dashboardCard;
    private CardView transactionCard;
    private CardView settingsCard;
    private TextView welcomeText;
    private Executor executor;
    // private BiometricPrompt biometricPrompt;
    // private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        // setupBiometricAuth();
        setupClickListeners();
    }
    
    private void initializeViews() {
        dashboardCard = findViewById(R.id.dashboard_card);
        transactionCard = findViewById(R.id.transaction_card);
        settingsCard = findViewById(R.id.settings_card);
        welcomeText = findViewById(R.id.welcome_text);
        
        // Set welcome message
        welcomeText.setText("Welcome to FraudGuard\nSecure Transaction Monitoring");
    }
    
    /*
    private void setupBiometricAuth() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // Handle authentication error
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // Authentication succeeded
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // Authentication failed
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Cancel")
                .build();
    }
    */
    
    private void setupClickListeners() {
        dashboardCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        
        transactionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                startActivity(intent);
            }
        });
        
        settingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
} 