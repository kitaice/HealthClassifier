package com.example.root.healthclassifierapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE = "com.example.healthclassifierapp.MESSAGE";
    public static HealthClassifier hclass = new HealthClassifier();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            hclass.init("/storage/sdcard/train.csv","storage/sdcard/test.csv", 10);


    }

   public void displayMessage(View view) {
        ArrayList<Double> instance = new ArrayList<Double>();
        EditText HR_edittext = (EditText) findViewById(R.id.editText14);
        String HR_str = HR_edittext.getText().toString();
        double HR_val = Double.parseDouble(HR_str);
        instance.add(HR_val);
        EditText BP_L_edittext = (EditText) findViewById(R.id.editText13);
        String BP_L_str = BP_L_edittext.getText().toString();
        double BP_L_val = Double.parseDouble(BP_L_str);
        instance.add(BP_L_val);
        EditText BP_H_edittext = (EditText) findViewById(R.id.editText20);
        String BP_H_str = BP_H_edittext.getText().toString();
        double BP_H_val = Double.parseDouble(BP_H_str);
        instance.add(BP_H_val);
        EditText BMI_edittext = (EditText) findViewById(R.id.editText19);
        String BMI_str = BMI_edittext.getText().toString();
        double BMI_val = Double.parseDouble(BMI_str);
        instance.add(BMI_val);
        EditText BS_edittext = (EditText) findViewById(R.id.editText18);
        String BS_str = BS_edittext.getText().toString();
        double BS_val = Double.parseDouble(BS_str);
        instance.add(BS_val);
        EditText PBS_edittext = (EditText) findViewById(R.id.editText17);
        String PBS_str = PBS_edittext.getText().toString();
        double PBS_val = Double.parseDouble(PBS_str);
        instance.add(PBS_val);
        EditText BC_edittext = (EditText) findViewById(R.id.editText16);
        String BC_str = BC_edittext.getText().toString();
        double BC_val = Double.parseDouble(BC_str);
        instance.add(BC_val);

        int classifier = hclass.classifySingleData(instance);

        Intent intent = null;
        if (classifier == 1) {
            intent = new Intent(this, DisplayMessageActivity.class);
        } else {
            intent = new Intent(this, OtherDisplayMessageActivity.class);
        }
       // Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
    }
}
