package com.example.root.healthclassifierapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OtherDisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra (MainActivity.MESSAGE);

        TextView textview = (TextView) findViewById(R.id.textView2);
        textview.setText(message);
    }
}
