package com.carens.activity_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private TextView tVwelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String fullname = intent.getStringExtra("fullname");

        tVwelcome = findViewById(R.id.welcome);
        tVwelcome.setText("Welcome, "+fullname);
    }
}