package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Catalog extends AppCompatActivity {

    private String responseString;
    private String requestUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Intent intent = getIntent();
        responseString = intent.getStringExtra(MainActivity.RESPONSE_STRING);
        requestUrl = intent.getStringExtra(MainActivity.REQUEST_URL);

        TextView demo = findViewById(R.id.demo);

        demo.setText(responseString);
    }
}