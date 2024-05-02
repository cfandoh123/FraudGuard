import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdOut;

public class WeakLearner {
    private int valueP;     // value predictor
    private int dimensionP;     // dimension predictor
    private int signP;     // sign predictor
    private int dist;      // number of locations

    // train the weak learner
    public WeakLearner(int[][] input, double[] weights, int[] labels) {
        // check if any of the input arguments is null
        if (input == null || weights == null || labels == null)
            throw new IllegalArgumentException("arguments cannot be null");
        int n = input.length;
        dist = input[0].length;

        // check if the lengths of weights and labels arrays match the number
        // of input samples
        if (weights.length != n || labels.length != n)
            throw new IllegalArgumentException(
                    "lengths of arguments are incompatible");
        // Check if all weight values are positive
        for (double i : weights) {
            if (i < 0) throw new IllegalArgumentException(
                    "weights should be positive");
        }
        // check if all label values are either 0 or 1
        for (int i : labels)
            if (i < 0 || i > 1)
                throw new IllegalArgumentException("labels are incorrect");


        double highest = Double.NEGATIVE_INFINITY;
        // iterate over all dimensions
        for (int i = 0; i < dist; i++) {
            Integer[] values = new Integer[n];
            // extract values for the current dimension
            for (int j = 0; j < n; j++) {
                values[j] = input[j][i];
            }

            // sort the values and get the indexes
            int[] indexes = Merge.indexSort(values);


            // iterate over both possible sign values (0 and 1)
            for (int j = 0; j < 2; j++) {
                double sum = 0;
                // check prediction for the first value and compare to the label
                for (int h = 0; h < n; h++) {
                    int check = input[h][i];
                    if (isPredict(j, values[indexes[0]], check, labels[h]))
                        sum += weights[h];
                }


                // update the predictors if the sum is greater than the current highest
                if (sum > highest) {
                    dimensionP = i;
                    signP = j;
                    valueP = values[indexes[0]];

                    highest = sum;

                }


                for (int k = 1; k < n; k++) {

                    int value1 = values[indexes[k]];
                    int value2 = values[indexes[k - 1]];
                    int label;
                    double weight;
                    int result1 = predictor(j, value1, value1);
                    int result2 = predictor(j, value2, value1);


                    // if the predictions for value1 and value2 are the same,
                    // skip this iteration
                    if (result1 == result2) {
                        continue;
                    }
                    int checker = values[indexes[k]];
                    int counter = k;
                    // update the sum for all values equal to value1
                    while (checker == value1) {
                        label = labels[indexes[counter]];
                        weight = weights[indexes[counter]];
                        result1 = predictor(j, value1, checker);
                        if (result1 == label) {
                            sum += weight;
                        }

                        else {
                            sum -= weight;
                        }
                        counter++;
                        if (counter == n)
                            break;
                        checker = values[indexes[counter]];
                    }


                    // update the predictors if the sum is greater than the
                    // current highest
                    if (sum > highest) {
                        dimensionP = i;
                        signP = j;
                        valueP = values[indexes[k]];
                        highest = sum;
                    }
                }


            }
        }
    }

    // private method to check if prediction matches label
    private boolean isPredict(int s, int v, int check, int label) {
        int result = predictor(s, v, check);
        return (label == result);
    }

    // private method for predicting based on values
    private int predictor(int s, int v, int check) {
        int result;
        if (s == 0) {
            if (check <= v)
                result = 0;
            else result = 1;
        }
        else {
            if (check <= v)
                result = 1;
            else result = 0;
        }
        return result;
    }

    // return the prediction of the learner for a new sample
    public int predict(int[] sample) {
        // check if the sample is null
        if (sample == null)
            throw new IllegalArgumentException("sample cannot be null");
        // check if the sample length matches the input dimension
        if (sample.length != dist)
            throw new IllegalArgumentException(
                    "sample length is incompatible with input dimension");
        int check = sample[dimensionP];
        return predictor(signP, valueP, check);
    }

    // return the dimension the learner uses to separate the data
    public int dimensionPredictor() {
        return dimensionP;
    }

    // return the value the learner uses to separate the data
    public int valuePredictor() {
        return valueP;
    }

    // return the sign the learner uses to separate the data
    public int signPredictor() {
        return signP;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In datafile = new In(args[0]);

        int n = datafile.readInt();
        int k = datafile.readInt();

        int[][] input = new int[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                input[i][j] = datafile.readInt();
            }
        }

        int[] labels = new int[n];
        for (int i = 0; i < n; i++) {
            labels[i] = datafile.readInt();
        }

        double[] weights = new double[n];
        for (int i = 0; i < n; i++) {
            weights[i] = datafile.readDouble();
        }

        WeakLearner weakLearner = new WeakLearner(input, weights, labels);
        StdOut.printf("vp = %d, dp = %d, sp = %d\n",
                      weakLearner.valuePredictor(),
                      weakLearner.dimensionPredictor(), weakLearner.signPredictor());

        int[] predictionSample = new int[k];
        for (int i = 0; i < k; i++) predictionSample[i] = i;

        int prediction = weakLearner.predict(predictionSample);
        System.out.println(prediction);
    }
}
