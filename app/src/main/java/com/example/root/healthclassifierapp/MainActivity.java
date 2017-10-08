package com.example.root.healthclassifierapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE = "com.example.healthclassifierapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText edittext = (EditText) findViewById(R.id.editText);
        String message = edittext.getText().toString();
        intent.putExtra(MESSAGE, message);
        startActivity(intent);
    }
}
