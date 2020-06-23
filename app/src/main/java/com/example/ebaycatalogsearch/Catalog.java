package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Catalog extends AppCompatActivity {

    private String responseString;
    private String requestUrl;
    private String requestKeywords;
    private JSONObject responseJSON;
    private JSONArray responseItemsArray;
    private int itemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Intent intent = getIntent();
        responseString = intent.getStringExtra(MainActivity.RESPONSE_STRING);
        requestUrl = intent.getStringExtra(MainActivity.REQUEST_URL);
        requestKeywords = intent.getStringExtra(MainActivity.REQUEST_KEYWORDS);

        Log.e("Catalog.java - url", requestUrl);

        try {
            responseJSON = new JSONObject(responseString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            responseItemsArray = responseJSON.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            itemCount = Integer.parseInt(responseJSON.getString("itemCount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView itemCountDisplay = findViewById(R.id.itemCountDisplay);
        itemCountDisplay.setText("Showing " + itemCount + " results for " + requestKeywords);


    }
}