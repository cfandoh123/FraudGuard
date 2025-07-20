package com.frauddetection.app.ml;

import java.util.ArrayList;

public class BoostingAlgorithm {
    private int[][] inputData;          // array for input data for training
    private int[] labelData;           // array for input labels
    private double[] weights;       // weights of inputs
    private Clustering cluster;     // a cluster object
    private int num;                // length of dimensions
    private ArrayList<WeakLearner> learners; // stores learners

    // create the clusters and initialize your data structures
    public BoostingAlgorithm(int[][] input, int[] labels, DataSet.Point2D[] locations, int k) {
        if (input == null || labels == null || locations == null)
            throw new IllegalArgumentException("null argument");
        int n = input.length;
        num = input[0].length;
        if (labels.length != n || locations.length != num)
            throw new IllegalArgumentException("wrong length");
        if (k < 0 || k > n)
            throw new IllegalArgumentException("wrong k");
        for (int label : labels) {
            if (label < 0 || label > 1)
                throw new IllegalArgumentException("wrong labelData");
        }

        initializeDataStructures(input, labels, locations, k);
    }

    // private method to initialize data structures
    private void initializeDataStructures(int[][] input, int[] labels, DataSet.Point2D[]
            locations, int k) {
        learners = new ArrayList<>();
        int n = input.length;
        cluster = new Clustering(locations, k);
        this.inputData = new int[n][k];
        weights = new double[n];
        this.labelData = labels.clone();
        for (int i = 0; i < n; i++) {
            this.inputData[i] = cluster.reduceDimensions(input[i]);
            weights[i] = (1.0 / n);
        }
    }

    // return the current weight of the ith point
    public double weightOf(int i) {
        return weights[i];
    }

    // apply one step of the boosting algorithm
    public void iterate() {
        WeakLearner learn = new WeakLearner(inputData, weights, labelData);
        learners.add(learn);
        updateWeights(learn);
    }

    // update weights based on learner prediction
    private void updateWeights(WeakLearner learner) {
        int n = weights.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            int result = learner.predict(inputData[i]);
            if (result != labelData[i])
                weights[i] *= 2;
            sum += weights[i];
        }
        for (int i = 0; i < n; i++)
            weights[i] /= sum;
    }

    // return the prediction of the learner for a new sample
    public int predict(int[] sample) {
        validateSample(sample);
        int[] test = cluster.reduceDimensions(sample);
        int count0 = 0;
        int count1 = 0;
        for (WeakLearner learner : learners) {
            int result = learner.predict(test);
            if (result == 0)
                count0++;
            else
                count1++;
        }
        if (count0 >= count1)
            return 0;
        return 1;
    }

    // validate sample inputData
    private void validateSample(int[] sample) {
        if (sample == null)
            throw new IllegalArgumentException("null argument");
        if (sample.length != num)
            throw new IllegalArgumentException("wrong sample length");
    }

    // calculate accuracy for a dataset
    private static double calculateAccuracy(BoostingAlgorithm model,
                                            int[][] input, int[] labels) {
        double accuracy = 0;
        for (int i = 0; i < input.length; i++) {
            if (model.predict(input[i]) == labels[i]) {
                accuracy += 1;
            }
        }
        return accuracy / input.length;
    }
} 