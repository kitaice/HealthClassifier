# HealthClassifier

HealthClassifier is a simple Android application that utilizes machine learning in reading and classifying input medical data. Given user input into the application interface, the program uses a decision tree to match and classify data to learned patterns. Although this is an overly-complex solution, it demonstrates the potential of rudimentary machine learning in the medical field. This concept can be expanded to large datasets with multiple potential classifications by changing small parts of the program.

The output of the program, based on input files train.csv and test.csv, looks something like this:

These A-values correspond to the features, in this order:
1 = Heart Rate
2 = Low Blood Pressure
3 = High Blood Pressure
4 = BMI
5 = Blood Sugar
6 = Blood Sugar (after eating)
7 = Blood Cholestrol
The values after represent the "significance" of each feature, essentially defining the highest node on the decision tree (in this case, A7) and so on.
```
A1 0.026075
A2 0.064904
A3 0.056008
A4 0.037618
A5 0.032405
A6 0.028895
A7 0.065199
```

This list shows the values classified by the decision tree on the training set. The final number is the percent accuracy.
```
print training accuracy
---------------------------------------------------------------
Unhealthy
Unhealthy
Healthy
Unhealthy
Healthy
Unhealthy
Healthy
Unhealthy
Unhealthy
Healthy
...
Unhealthy
Unhealthy
Unhealthy
Healthy
Unhealthy
0.93
```

This list shows the values classified by the decision tree on the training set. The final number is the percent accuracy.
```
print testing accuracy
---------------------------------------------------------------
Unhealthy
Unhealthy
Healthy
Unhealthy
Unhealthy
Unhealthy
Healthy
Unhealthy
Unhealthy
Unhealthy
Healthy
Unhealthy
Healthy
Unhealthy
Unhealthy
Healthy
Unhealthy
Unhealthy
Unhealthy
Unhealthy
...
Unhealthy
Unhealthy
Healthy
Unhealthy
Unhealthy
0.802
```
