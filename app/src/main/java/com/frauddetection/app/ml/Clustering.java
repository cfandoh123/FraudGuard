package com.frauddetection.app.ml;

import java.util.ArrayList;
import java.util.List;

public class Clustering {
    private int[] clusterIds;       // cluster IDs for each point
    private int k;          // number of clusters
    private int m;          // number of data points

    // run the clustering algorithm and create the clusters
    public Clustering(DataSet.Point2D[] locations, int k) {
        if (locations == null)
            throw new IllegalArgumentException("location cannot be null");
        this.k = k;
        m = locations.length;
        if (k < 1 || k > m)
            throw new IllegalArgumentException(
                    "k should be greater than one or less than m");

        // Simple k-means clustering implementation
        performKMeansClustering(locations);
    }

    // Simple k-means clustering implementation
    private void performKMeansClustering(DataSet.Point2D[] locations) {
        clusterIds = new int[m];
        
        // Initialize cluster centers randomly
        DataSet.Point2D[] centers = new DataSet.Point2D[k];
        for (int i = 0; i < k; i++) {
            centers[i] = new DataSet.Point2D(
                Math.random() * 30, 
                Math.random() * 30
            );
        }
        
        // Perform k-means iterations
        for (int iteration = 0; iteration < 10; iteration++) {
            // Assign points to nearest center
            for (int i = 0; i < m; i++) {
                double minDistance = Double.MAX_VALUE;
                int bestCluster = 0;
                
                for (int j = 0; j < k; j++) {
                    double distance = locations[i].distanceTo(centers[j]);
                    if (distance < minDistance) {
                        minDistance = distance;
                        bestCluster = j;
                    }
                }
                clusterIds[i] = bestCluster;
            }
            
            // Update centers
            for (int j = 0; j < k; j++) {
                double sumX = 0, sumY = 0;
                int count = 0;
                
                for (int i = 0; i < m; i++) {
                    if (clusterIds[i] == j) {
                        sumX += locations[i].x();
                        sumY += locations[i].y();
                        count++;
                    }
                }
                
                if (count > 0) {
                    centers[j] = new DataSet.Point2D(sumX / count, sumY / count);
                }
            }
        }
    }

    // return the cluster of the ith point
    public int clusterOf(int i) {
        if (i < 0 || i >= m)
            throw new IllegalArgumentException("i is out of range");
        return clusterIds[i];
    }

    // use the clusters to reduce the dimensions of an input
    public int[] reduceDimensions(int[] input) {
        if (input == null || input.length != m)
            throw new IllegalArgumentException(
                    "input array length should be equal to number of locations");
        int[] reducedArr = new int[k];
        for (int i = 0; i < m; i++) {
            int id = clusterOf(i);
            reducedArr[id] += input[i];
        }
        return reducedArr;
    }
} 