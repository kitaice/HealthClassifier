import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DecisionTree {
    private DecisionTreeNode root;
    // Ordered list of attributes
    private ArrayList<String> train_attributes;
    private double[][] best_split_point_list;
    // Private double[][] print_info_list;
    private ArrayList<ArrayList<Double>> train_dataset_list;
    // Min number of instances per leaf.
    private int min_leafnum;

    // Answers static questions about decision trees.
    DecisionTree(){}
    
    /**
     * Overloaded constructor just for printing root gain info
     * @param train_dataset
     * @param train_attribute_names
     */
    DecisionTree(ArrayList<ArrayList<Double>> train_dataset, ArrayList<String> train_attribute_names) {
        this.train_attributes = train_attribute_names;
        ArrayList<ArrayList<Double>> dataset = new ArrayList<ArrayList<Double>>();
        // Create a copy of train_dataset
        for (int i = 0; i < train_dataset.size(); i++)
            dataset.add(new ArrayList<Double>());
        for (int i = 0; i < train_dataset.size(); i++) {
            for (int j = 0; j < train_dataset.get(0).size(); j++) {
                dataset.get(i).add(train_dataset.get(i).get(j).doubleValue());
            }
        }
        this.train_dataset_list = dataset;
        // Initialize the 2D array
        best_split_point_list = new double[train_attribute_names.size()][2];
        // Build decision tree
        rootInfoGain(dataset, train_attributes, min_leafnum);
    }

    /**
     * Build a decision tree given a training set then prune it using a tuning
     * set
     * 
     * @param train: the training set
     * @param tune: the tuning set
     */
    DecisionTree(ArrayList<ArrayList<Double>> train_dataset, ArrayList<String> train_attribute_names, int min_leafnum) {
        this.train_attributes = train_attribute_names;
        ArrayList<ArrayList<Double>> dataset = new ArrayList<ArrayList<Double>>();
        // Create a copy of train_dataset
        for (int i = 0; i < train_dataset.size(); i++)
            dataset.add(new ArrayList<Double>());
        for (int i = 0; i < train_dataset.size(); i++) {
            for (int j = 0; j < train_dataset.get(0).size(); j++) {
                dataset.get(i).add(train_dataset.get(i).get(j).doubleValue());
            }
        }
        this.train_dataset_list = dataset;
        this.min_leafnum = min_leafnum;
        // Initialize the list of best split points
        best_split_point_list = new double[train_attribute_names.size()][2];
        // Build decision tree
        this.root = buildTree(train_dataset_list);
    }

    private DecisionTreeNode buildTree(ArrayList<ArrayList<Double>> dataset) {
        // Check if all data has the same class value
        if (isall(dataset)) {
            int class_val = dataset.get(0).get(train_attributes.size()).intValue();
            // Leaf node
            DecisionTreeNode node = new DecisionTreeNode(class_val, null, 0.0);
            return node;
        }
        // Check if the size of dataset is lower than or equal to minimum limit
        if (dataset.size() <= min_leafnum) {
            DecisionTreeNode node = new DecisionTreeNode(pluralityClassLabel(dataset), null, 0.0);
            return node;
        }
        // Calculate the best information gain of each attribute from a data set
        rootInfoGain(dataset, train_attributes, min_leafnum);
        // Select the best attribute
        int feature = bestAttribute(best_split_point_list);
        // Create feature node
        DecisionTreeNode root = new DecisionTreeNode(-1, "A" + (feature + 1), best_split_point_list[feature][1]);
        ArrayList<ArrayList<Double>> subDataSet1 = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> subDataSet2 = new ArrayList<ArrayList<Double>>();
        // Split data into two sub data set according to specified threshold
        splitData(dataset, subDataSet1, subDataSet2, best_split_point_list[feature][1], feature);
        root.left = buildTree(subDataSet1);
        root.right = buildTree(subDataSet2);
        return root;
    }
    
    /**
     * Split the data set according to the threshold of best attribute
     * @param dataset: the training set
     * @param sub1: left subtree
     * @param sub2: right subtree
     * @param threshold: split point
     * @param attribute: the specific attribute
     */
    private void splitData(ArrayList<ArrayList<Double>> dataset, ArrayList<ArrayList<Double>> sub1, ArrayList<ArrayList<Double>> sub2, double threshold, int attribute) {
        for(int i = 0; i < dataset.size(); i++) {
            if(dataset.get(i).get(attribute).doubleValue() <= threshold) {
                ArrayList<Double> data = new ArrayList<Double>();
                for (int j = 0; j < dataset.get(i).size(); j++) {
                    data.add(dataset.get(i).get(j).doubleValue());
                } //If less than threshold add the instance to left tree
                sub1.add(data);
            } else {
                ArrayList<Double> data = new ArrayList<Double>();
                for (int j = 0; j < dataset.get(i).size(); j++) {
                    data.add(dataset.get(i).get(j).doubleValue());
                }
                sub2.add(data);
            }// Else add the instance to right tree
        }
    }
    
    /**
     * Choose the best attribute
     * @param list the list of all thresholds of all attribute
     */
    private int bestAttribute(double[][] list) {
        double best = 0;
        int index = 0;
        for (int i = 0; i < list.length; i++) {
            // If multiple attributes have the same information gain, split on 
            // the attribute that appears later in the list of attribute labels
            if (list[i][0] >= best) {
                best = list[i][0];
                index = i;
            }
        }
        return index;
    }

    /**
     * Check if all the data have the same class value
     * @param dataset
     */
    private boolean isall(ArrayList<ArrayList<Double>> dataset) {
        for (int i = 0; i < dataset.size() - 1; i++) {
            if (!dataset.get(i).get(train_attributes.size()).equals(dataset.get(i + 1).get(train_attributes.size()).doubleValue()))
                return false;
        }
        return true;
    }

    /**
     * Give class label to a data set that has less than or equal to 10 data
     * @param dataset
     */
    private int pluralityClassLabel(ArrayList<ArrayList<Double>> dataset) {
        int count = 0;
        for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i).get(train_attributes.size()).doubleValue() == 0)
                count++;
        }
        if (count > (dataset.size() - count))
            return 0;
        else
            return 1;
    }
    
    /**
     * Classify the instance
     * @param instance
     * @return
     */
    public int classify(List<Double> instance) {
        return classify(instance, root);
    }
    
    /**
     * Recursive method to classify an instance
     * @param instance
     * @param node
     * @return
     */
    private int classify(List<Double> instance, DecisionTreeNode node) {
        if(node.isLeaf())
            return node.class_label;
        if(instance.get(Integer.parseInt(node.attribute.substring(1, node.attribute.length())) - 1) <= node.threshold)
            return classify(instance, node.left);
        return classify(instance, node.right);
    }

    /**
     * Calculate all the information gain of all the attributes
     * @param dataset
     * @param train_attribute_names
     * @param min_leafnum
     */
    public void rootInfoGain(ArrayList<ArrayList<Double>> dataset, ArrayList<String> train_attribute_names, int min_leafnum) {
        this.train_attributes = train_attribute_names;
        this.train_dataset_list = dataset;
        this.min_leafnum = min_leafnum;
        // Calculate the root entropy
        double entropy = calculateEntropy(dataset);
        for (int i = 0; i < train_attribute_names.size(); i++) {
            // Sort the list according to attribute value and class value
            sortList(dataset, i);
            ArrayList<Double> thresholds = new ArrayList<Double>();
            // Calculate all possible thresholds of an attribute
            threshold(dataset, thresholds, i);
            double largestInfoGain = 0;
            double threshold = 0;
            // Select best attribute and information gain
            for (int j = 0; j < thresholds.size(); j++) {
                double infoGain = infoGain(dataset, entropy, thresholds.get(j).doubleValue(), i);
                if (infoGain >= largestInfoGain) {
                    largestInfoGain = infoGain;
                    threshold = thresholds.get(j).doubleValue();
                }
            }
            // Save the best attribute and information gain
            best_split_point_list[i][0] = largestInfoGain;
            best_split_point_list[i][1] = threshold;
        }
    }

    public void printInfo(){
        for (int i = 0; i < best_split_point_list.length; i++) {
            System.out.println(this.train_attributes.get(i) + " " + String.format("%.6f", best_split_point_list[i][0]));
        }
        
    }
    /**
     * Sort the ArrayList of ArrayList according to attribute value
     * 
     * @param dataset the input data
     * @param index the attribute number
     */
    private void sortList(ArrayList<ArrayList<Double>> dataset, int index) {
        Collections.sort(dataset, new Comparator<List<Double>>() {
            @Override
            public int compare(List<Double> o1, List<Double> o2) {
                int compare = o1.get(index).compareTo(o2.get(index));
                if(compare == 0) {
                    if(o1.get(train_attributes.size()).doubleValue() > o2.get(train_attributes.size()).doubleValue())
                        return 1;
                    else if(o1.get(train_attributes.size()).doubleValue() < o2.get(train_attributes.size()).doubleValue())
                        return -1;
                    else
                        return 0;
                } else
                    return compare;
            }
        });
    }

    /**
     * Compute candidate thresholds for an attribute
     * 
     * @param dataset
     * @param candidates
     * @param index
     */
    private void threshold(ArrayList<ArrayList<Double>> dataset, ArrayList<Double> candidates, int index) {
        double split = dataset.get(0).get(train_attributes.size()).doubleValue();
        for (int i = 1; i < dataset.size(); i++) {
            if (dataset.get(i).get(train_attributes.size()).doubleValue() != split) {
                double threshold = (dataset.get(i).get(index).doubleValue() + dataset.get(i - 1).get(index).doubleValue()) / 2;
                candidates.add(threshold);
                split = dataset.get(i).get(train_attributes.size()).doubleValue();
            }
        }
    }

    /**
     * Calculate entropy
     * 
     * @param dataset
     */
    private double calculateEntropy(ArrayList<ArrayList<Double>> dataset) {
        int count = 0;
        for (int i = 0; i < dataset.size(); i++)
            if (dataset.get(i).get(train_attributes.size()).intValue() == 0)
                count++;
        double p = (double)count / dataset.size();
        double q = (double)(dataset.size() - count) / dataset.size();
        double entropy = -(p * (Math.log(p) / Math.log(2)))
                - ((q) * (Math.log(q) / Math.log(2)));
        return entropy;
    }

    /**
     * Calculate information gain
     * 
     * @param dataset
     * @param rootInfoGain
     * @param threshold
     * @param index
     * @return
     */
    private double infoGain(ArrayList<ArrayList<Double>> dataset, double entropy, double threshold, int index) {
        int difference = 0;
        for (int i = 0; i < dataset.size(); i++) {
            if (Double.compare(dataset.get(i).get(index).doubleValue(), threshold) <= 0) {
                difference++;
            } else
                break;
        }

        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < difference; i++)
            if (dataset.get(i).get(train_attributes.size()).doubleValue() == 0)
                count1++;
        for (int i = difference; i < dataset.size(); i++)
            if (dataset.get(i).get(train_attributes.size()).doubleValue() == 0)
                count2++;

        double proportion = 1.0 * difference / dataset.size();
        double subProportion1 = 1.0 * count1 / difference;
        double subq=(double)(difference - count1) /difference;
        double subProportion2 = 1.0 * count2 / (dataset.size() - difference);
        double subq1=(double)(dataset.size()-difference - count2) / (dataset.size()-difference);
        double entropy1 = proportion * (-(subProportion1 * (Math.log(subProportion1) / Math.log(2)))
                - (subq) * (Math.log(subq) / Math.log(2)));
        double entropy2 = (1.0 - proportion) * (-(subProportion2 * (Math.log(subProportion2) / Math.log(2)))
                - ( subq1) * (Math.log(subq1) / Math.log(2)));
        if (Double.isNaN(entropy1))
            entropy1 = 0;
        if (Double.isNaN(entropy2))
            entropy2 = 0;
        return (entropy - entropy1 - entropy2);
    }

    /**
     * Print the decision tree in the specified format
     */
    public void print() {
        printTreeNode("", this.root);
    }

    /**
     * Recursively prints the tree structure, left subtree first, then right
     * subtree.
     */
    public void printTreeNode(String prefixStr, DecisionTreeNode node) {
        String printStr = prefixStr + node.attribute;

        System.out.print(printStr + " <= " + String.format("%.6f", node.threshold));
        if (node.left.isLeaf()) {
            System.out.println(": " + String.valueOf(node.left.class_label));
        } else {
            System.out.println();
            printTreeNode(prefixStr + "|\t", node.left);
        }
        System.out.print(printStr + " > " + String.format("%.6f", node.threshold));
        if (node.right.isLeaf()) {
            System.out.println(": " + String.valueOf(node.right.class_label));
        } else {
            System.out.println();
            printTreeNode(prefixStr + "|\t", node.right);
        }
    }

    public double printAccuracy(int numEqual, int numTotal) {
        double accuracy = 1.0*numEqual / (double) numTotal;
        System.out.println(accuracy);
        return accuracy;
    }

    /**
     * Private class to facilitate instance sorting by argument position since
     * java doesn't like passing variables to comparators through nested
     * variable scopes.
     */
    private class DataBinder {

        public ArrayList<Double> mData;
        public int i;

        public DataBinder(int i, ArrayList<Double> mData) {
            this.mData = mData;
            this.i = i;
        }

        public double getArgItem() {
            return mData.get(i);
        }

        public ArrayList<Double> getData() {
            return mData;
        }
    }
}
