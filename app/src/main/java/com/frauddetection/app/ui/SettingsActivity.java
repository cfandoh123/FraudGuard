package com.frauddetection.app.ui;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.frauddetection.app.R;

public class SettingsActivity extends AppCompatActivity {
    
    private Switch biometricSwitch;
    private Switch notificationSwitch;
    private Switch autoAnalysisSwitch;
    private Switch dataSharingSwitch;
    private TextView modelVersionText;
    private TextView lastUpdateText;
    private CardView securityCard;
    private CardView modelCard;
    private CardView privacyCard;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        initializeViews();
        setupClickListeners();
        loadSettings();
    }
    
    private void initializeViews() {
        biometricSwitch = findViewById(R.id.biometric_switch);
        notificationSwitch = findViewById(R.id.notification_switch);
        autoAnalysisSwitch = findViewById(R.id.auto_analysis_switch);
        dataSharingSwitch = findViewById(R.id.data_sharing_switch);
        modelVersionText = findViewById(R.id.model_version_text);
        lastUpdateText = findViewById(R.id.last_update_text);
        securityCard = findViewById(R.id.security_card);
        modelCard = findViewById(R.id.model_card);
        privacyCard = findViewById(R.id.privacy_card);
    }
    
    private void setupClickListeners() {
        biometricSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle biometric authentication setting
            saveBiometricSetting(isChecked);
        });
        
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle notification setting
            saveNotificationSetting(isChecked);
        });
        
        autoAnalysisSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle auto analysis setting
            saveAutoAnalysisSetting(isChecked);
        });
        
        dataSharingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle data sharing setting
            saveDataSharingSetting(isChecked);
        });
    }
    
    private void loadSettings() {
        // Load current settings from SharedPreferences
        biometricSwitch.setChecked(getBiometricSetting());
        notificationSwitch.setChecked(getNotificationSetting());
        autoAnalysisSwitch.setChecked(getAutoAnalysisSetting());
        dataSharingSwitch.setChecked(getDataSharingSetting());
        
        // Set model information
        modelVersionText.setText("Model Version: 1.2.0");
        lastUpdateText.setText("Last Update: " + getCurrentDate());
    }
    
    private void saveBiometricSetting(boolean enabled) {
        getSharedPreferences("settings", MODE_PRIVATE)
            .edit()
            .putBoolean("biometric_enabled", enabled)
            .apply();
    }
    
    private void saveNotificationSetting(boolean enabled) {
        getSharedPreferences("settings", MODE_PRIVATE)
            .edit()
            .putBoolean("notifications_enabled", enabled)
            .apply();
    }
    
    private void saveAutoAnalysisSetting(boolean enabled) {
        getSharedPreferences("settings", MODE_PRIVATE)
            .edit()
            .putBoolean("auto_analysis_enabled", enabled)
            .apply();
    }
    
    private void saveDataSharingSetting(boolean enabled) {
        getSharedPreferences("settings", MODE_PRIVATE)
            .edit()
            .putBoolean("data_sharing_enabled", enabled)
            .apply();
    }
    
    private boolean getBiometricSetting() {
        return getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("biometric_enabled", true);
    }
    
    private boolean getNotificationSetting() {
        return getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("notifications_enabled", true);
    }
    
    private boolean getAutoAnalysisSetting() {
        return getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("auto_analysis_enabled", true);
    }
    
    private boolean getDataSharingSetting() {
        return getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("data_sharing_enabled", false);
    }
    
    private String getCurrentDate() {
        return java.text.SimpleDateFormat.getDateInstance().format(new java.util.Date());
    }
} 