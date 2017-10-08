package com.example.root.healthclassifierapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class HealthClassifier {

    //static boolean debug = true;
    static DecisionTree tree = new DecisionTree(null, null, 0);

    public static void init(String train_file, String test_file, int instances) {
        // Create train set instances and attributes.
        ArrayList<ArrayList<Double>> train_dataset;
        ArrayList<String> train_attribute_names;
        ArrayList<ArrayList<Double>> test_dataset;
        ArrayList<String> test_attribute_names;
        // Make train data set.
        HashMap<String, Object> hash_map = createDataSet(train_file);
        train_dataset = (ArrayList<ArrayList<Double>>)hash_map.get("dataset");
        train_attribute_names = (ArrayList<String>)hash_map.get("attribute_names");
        // Make test data set.
        hash_map = createDataSet(test_file);
        test_dataset = (ArrayList<ArrayList<Double>>)hash_map.get("dataset");
        test_attribute_names = (ArrayList<String>)hash_map.get("attribute_names");
        
        // Build tree.
        tree = new DecisionTree(train_dataset, train_attribute_names, instances);

        /*
        if (debug) {
            System.out.println("Debug: make tree and print info\n");
            DecisionTree test_tree = new DecisionTree(train_dataset,train_attribute_names);
            test_tree.printInfo();
        }
        */
        int classify;
        System.out.println("\nClassify data\n");
        System.out.println("print training accuracy");
        System.out.println("---------------------------------------------------------------");
        int accuracy=0;
        for(ArrayList<Double> instance : train_dataset){
            classify=tree.classify(instance);
            if (classify == 1) System.out.println("Healthy");
            System.out.println("Unhealthy");
            if(classify==instance.get(train_attribute_names.size())) accuracy++;
        }
        tree.printAccuracy(accuracy,train_dataset.size());

        System.out.println("\nprint testing accuracy");
        System.out.println("---------------------------------------------------------------");
        accuracy=0;
        for(ArrayList<Double> instance : test_dataset){
            classify=tree.classify(instance);
            if (classify == 1) System.out.println("Healthy");
            System.out.println("Unhealthy");
            if(classify==instance.get(test_attribute_names.size())) accuracy++;
        }
        tree.printAccuracy(accuracy,test_dataset.size());
    }

    public static int classifySingleData(ArrayList<Double> app_input) {
        return tree.classify(app_input);
    }

    /**
     * Converts from text file format to a list of lists. Each sub-list represents a row from the file. 
     * You DO NOT have to use this data format if you don't want to. Use whatever data structure you 
     * find most convenient.
     */
    private static HashMap<String, Object> createDataSet(String file) {
        // List of lists. dataset.get(i) corresponds to row i from the input file.
        HashMap<String, Object> hash_map = new HashMap<String, Object>();
        ArrayList<ArrayList<Double>> dataset = new ArrayList<ArrayList<Double>>();
        ArrayList<String> attribute_names = new ArrayList<String>();    

        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(file));
            while (in.ready()) {
                String line = in.readLine();
                String prefix = line.substring(0, 2);
                if (prefix.equals("//")) {
                } else if (prefix.equals("##")) {
                    attribute_names.add(line.substring(2));
                } else {
                    String[] splitString = line.split(",");
                    //Create data row.
                    ArrayList<Double> row = new ArrayList<Double>();
                    for(String attr : splitString){
                        row.add(Double.parseDouble(attr));
                    }
                    //Add data row to data table.
                    dataset.add(row);
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Add data members to hash map to return.
        hash_map.put("dataset", dataset);
        hash_map.put("attribute_names", attribute_names);
        return hash_map;
    }
}
