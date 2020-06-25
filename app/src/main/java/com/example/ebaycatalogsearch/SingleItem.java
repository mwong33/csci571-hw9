package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SingleItem extends AppCompatActivity {

    private String productID;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        // Initialize the variables passed by intent
        Intent intent = getIntent();
        productID = intent.getStringExtra("productID");
        title = intent.getStringExtra("title");

        // Set the Single Item Title
        this.setTitle(title);

    }
}