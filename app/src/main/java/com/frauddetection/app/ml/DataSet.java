package com.frauddetection.app.ml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
    private int n, m;
    private Point2D[] locations;
    private int[][] input;
    private int[] labels;

    public DataSet(String filename) {
        try {
            // For Android, we'll create sample data instead of reading from file
            // In a real app, you'd load this from assets or download from server
            createSampleData();
        } catch (Exception e) {
            // Fallback to sample data if file reading fails
            createSampleData();
        }
    }
    
    private void createSampleData() {
        // Create sample training data
        n = 320; // number of samples
        m = 21;  // number of features
        
        // Create sample locations
        locations = new Point2D[m];
        for (int i = 0; i < m; i++) {
            locations[i] = new Point2D(Math.random() * 30, Math.random() * 30);
        }
        
        // Create sample input data
        input = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                input[i][j] = (int) (Math.random() * 2000);
            }
        }
        
        // Create sample labels (0 = safe, 1 = fraud)
        labels = new int[n];
        for (int i = 0; i < n; i++) {
            // 10% fraud rate
            labels[i] = Math.random() < 0.1 ? 1 : 0;
        }
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Point2D[] getLocations() {
        return locations.clone();
    }

    public int[][] getInput() {
        int[][] copy = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                copy[i][j] = input[i][j];
            }
        }
        return copy;
    }

    public int[] getLabels() {
        return labels.clone();
    }
    
    // Simple Point2D class for Android
    public static class Point2D {
        private double x, y;
        
        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        public double x() { return x; }
        public double y() { return y; }
        
        public double distanceTo(Point2D that) {
            double dx = this.x - that.x;
            double dy = this.y - that.y;
            return Math.sqrt(dx*dx + dy*dy);
        }
    }
} 