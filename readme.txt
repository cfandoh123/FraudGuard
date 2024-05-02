Programming Assignment 7: Fraud Detection

/* *****************************************************************************
 *  Describe how you implemented the Clustering constructor
 **************************************************************************** */
First, I created an edge-weighted graph with m vertices, where m was the length
of the Points array. I then added all the edges to this graph by
computing the Euclidean distance between each pair of points and used that as
the edge weight. With this completed graph, I used Kruskal's algorithm to
compute the minimum spanning tree (MST). The MST helps to determine which edges
to remove based on weight, as it contains the minimum set of edges required to
connect all vertices.
To order the edges in terms of weight, I inserted all the edges from the MST
into a BST and iterated through it. From this sorted BST, I added
the (m-k) shortest edges into a new edge-weighted graph. This new graph
represents the final clustering, where the vertices are connected by the (m-k)
shortest edges, effectively forming k clusters.
With this new graph, we have all the clusters being connected, and we used the
CC data structure to help represent this. The CC data structure identifies the
connected components (clusters) in the graph, which helps to determine the
cluster each point belongs to.

/* *****************************************************************************
 *  Describe how you implemented the WeakLearner constructor
 **************************************************************************** */
I designed the WeakLearner constructor to find the most optimal combination of
dimension, sign, and value for a weak learner model, using training data in the
form of input features, labels, and weights.
The constructor iterates over all dimensions (features) and possible sign values
(0 or 1). For each combination of dimension and sign, it sorts the data points
based on the values of that dimension. It then calculates the weighted sum of
correctly predicted labels using the first value as a threshold. It updates
this sum incrementally by adjusting for data points for which it finds a better
prediction.


/* *****************************************************************************
 *  Consider the large_training.txt and large_test.txt datasets.
 *  Run the boosting algorithm with different values of k and T (iterations),
 *  and calculate the test data set accuracy and plot them below.
 *
 *  (Note: if you implemented the constructor of WeakLearner in O(kn^2) time
 *  you should use the small_training.txt and small_test.txt datasets instead,
 *  otherwise this will take too long)
 **************************************************************************** */

      k          T         test accuracy       time (seconds)
   --------------------------------------------------------------------------
      5          80         0.86875            0.349
      5          100        0.86625            0.406
      5          180        0.86875            0.625
      8          100        0.94750            0.552
      8          180        0.94750            0.880
      10         100        0.95875            0.679
      10         180        0.96250            1.041
      16         100        0.96125            0.803
      16         180        0.97000            1.343
      20         80         0.96375            0.782
      20         180        0.97000            1.609
      32         80         0.98000            1.080
      32         100        0.97250            1.340
      32         180        0.97375            2.279
      50         80         0.96250            1.530
      50         180        0.97750            3.313
      64         80         0.96125            1.876
      64         100        0.97125            2.297
      80         80         0.95875            2.349
      80         180        0.97750            5.304


/* *****************************************************************************
 *  Find the values of k and T that maximize the test data set accuracy,
 *  while running under 10 second. Write them down (as well as the accuracy)
 *  and explain:
 *   1. Your strategy to find the optimal k, T.
 *   2. Why a small value of T leads to low test accuracy.
 *   3. Why a k that is too small or too big leads to low test accuracy.
 **************************************************************************** */
Optimal Values:
k = 32
T = 80
Test Accuracy = 0.98000

Strategy to find optimal k and T:
I performed a grid search over a range of values for k (5 to  80) and T (80 to 180).
For each combination of k and T, I trained the model and evaluate its accuracy
on the test dataset. After that, I chose the values of k and T that result in
the highest test accuracy.


Why a small value of T leads to low test accuracy:
A small value of T means fewer iterations of the boosting algorithm,
resulting in fewer weak learners being trained and combined. With fewer weak
learners, the model may not be able to capture the complex patterns in the data,
leading to lower accuracy on the test set.


Why a k that is too small or too big leads to low test accuracy:
k represents the number of clusters used in the Clustering step. If k is too
small, the data points may be grouped into too few clusters, resulting in a loss
of important information or patterns. On the other hand, if k is too large,
each data point may be treated as a separate cluster, leading to overfitting
and poor generalization to the test set.


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
