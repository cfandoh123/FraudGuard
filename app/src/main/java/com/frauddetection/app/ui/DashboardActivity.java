package com.frauddetection.app.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.frauddetection.app.R;
import com.frauddetection.app.ml.BoostingAlgorithm;
import com.frauddetection.app.ml.DataSet;
import com.frauddetection.app.network.FraudDetectionService;
import com.frauddetection.app.network.TransactionData;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    
    private TextView totalTransactionsText;
    private TextView fraudDetectedText;
    private TextView suspiciousActivityText;
    private TextView safeTransactionsText;
    private TextView fraudRateText;
    private TextView accuracyRateText;
    private PieChart fraudPieChart;
    private LineChart trendLineChart;
    private FraudDetectionService fraudDetectionService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        
        initializeViews();
        setupCharts();
        loadDashboardData();
    }
    
    private void initializeViews() {
        totalTransactionsText = findViewById(R.id.total_transactions_text);
        fraudDetectedText = findViewById(R.id.fraud_detected_text);
        suspiciousActivityText = findViewById(R.id.suspicious_activity_text);
        safeTransactionsText = findViewById(R.id.safe_transactions_text);
        fraudRateText = findViewById(R.id.fraud_rate_text);
        accuracyRateText = findViewById(R.id.accuracy_rate_text);
        fraudPieChart = findViewById(R.id.fraud_pie_chart);
        trendLineChart = findViewById(R.id.trend_line_chart);
        
        // Initialize network service
        fraudDetectionService = new FraudDetectionService();
    }
    
    private void setupCharts() {
        // Setup Pie Chart for fraud distribution
        setupPieChart();
        
        // Setup Line Chart for trends
        setupLineChart();
    }
    
    private void setupPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(15f, "Fraud"));
        entries.add(new PieEntry(25f, "Suspicious"));
        entries.add(new PieEntry(60f, "Safe"));
        
        PieDataSet dataSet = new PieDataSet(entries, "Transaction Status");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);
        
        PieData data = new PieData(dataSet);
        fraudPieChart.setData(data);
        fraudPieChart.getDescription().setEnabled(false);
        fraudPieChart.setCenterText("Transaction\nDistribution");
        fraudPieChart.setCenterTextSize(16f);
        fraudPieChart.animateY(1000);
        fraudPieChart.invalidate();
    }
    
    private void setupLineChart() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 5));
        entries.add(new Entry(1, 8));
        entries.add(new Entry(2, 12));
        entries.add(new Entry(3, 15));
        entries.add(new Entry(4, 10));
        entries.add(new Entry(5, 18));
        entries.add(new Entry(6, 20));
        
        LineDataSet dataSet = new LineDataSet(entries, "Fraud Cases");
        dataSet.setColor(getResources().getColor(R.color.error_color));
        dataSet.setCircleColor(getResources().getColor(R.color.error_color));
        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawValues(false);
        
        LineData lineData = new LineData(dataSet);
        trendLineChart.setData(lineData);
        trendLineChart.getDescription().setEnabled(false);
        trendLineChart.getXAxis().setDrawGridLines(false);
        trendLineChart.getAxisLeft().setDrawGridLines(false);
        trendLineChart.getAxisRight().setEnabled(false);
        trendLineChart.animateX(1000);
        trendLineChart.invalidate();
    }
    
    private void loadDashboardData() {
        // Load data from local ML model and network
        updateStatistics();
        loadTransactionHistory();
    }
    
    private void updateStatistics() {
        // Update UI with current statistics
        totalTransactionsText.setText("1,247");
        fraudDetectedText.setText("15");
        suspiciousActivityText.setText("25");
        safeTransactionsText.setText("1,207");
        fraudRateText.setText("1.2%");
        accuracyRateText.setText("98.7%");
    }
    
    private void loadTransactionHistory() {
        // Load recent transaction history from network
        fraudDetectionService.getTransactionHistory(new FraudDetectionService.Callback<List<TransactionData>>() {
            @Override
            public void onSuccess(List<TransactionData> transactions) {
                // Update charts with real data
                updateChartsWithRealData(transactions);
            }
            
            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }
    
    private void updateChartsWithRealData(List<TransactionData> transactions) {
        // Update charts with real transaction data
        // This would involve processing the transactions and updating the charts
    }
} 