package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Catalog extends AppCompatActivity {

    // Response and Request Data
    private String requestUrl;
    private String requestKeywords;
    private JSONObject responseJSON;
    private JSONArray responseItemsArray;
    private int itemCount;

    // Request Object
    private RequestQueue requestQueue;

    // Loading Bar elements
    private ProgressBar catalogProgressBar;
    private TextView catalogProgressText;

    // No Records element
    private TextView noRecords;

    // Catalog elements
    private TextView itemCountDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Acquire the Request URL and Request Keywords from MainActivity.java
        Intent intent = getIntent();
        requestUrl = intent.getStringExtra(MainActivity.REQUEST_URL);
        requestKeywords = intent.getStringExtra(MainActivity.REQUEST_KEYWORDS);

        // Initialize the Loading Bar elements
        catalogProgressBar = findViewById(R.id.catalogProgressBar);
        catalogProgressText = findViewById(R.id.catalogProgressText);

        // Initialize the No Records element
        noRecords = findViewById(R.id.noRecords);

        // Initialize the Catalog elements
        itemCountDisplay = findViewById(R.id.itemCountDisplay);

        // Initialize the requestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Execute the HTTP Request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Remove the loading bar once response is complete
                        catalogProgressBar.setVisibility(View.GONE);
                        catalogProgressText.setVisibility(View.GONE);

                        // Acquire the data
                        responseJSON = response;
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

                        if (itemCount == 0) {
                            // Display the No Records message and No Records Toast
                            noRecords.setVisibility(View.VISIBLE);
                            Toast.makeText(Catalog.this, "No Records",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Display the item count display
                            TextView itemCountDisplay = findViewById(R.id.itemCountDisplay);
                            itemCountDisplay.setText(createItemCountDisplay());
                            itemCountDisplay.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

    }

    private SpannableString createItemCountDisplay() {
        String rawItemCountDisplayString = "Showing " + itemCount + " results for " + requestKeywords;

        int itemCountStartIndex = 8;
        int itemCountEndIndex = itemCountStartIndex + String.valueOf(itemCount).length();

        int requestKeywordsStartIndex = itemCountStartIndex + String.valueOf(itemCount).length() + 13;
        int requestKeywordsEndIndex = rawItemCountDisplayString.length();

        SpannableString itemCountDisplayString = new SpannableString(rawItemCountDisplayString);

        ForegroundColorSpan fcsBlue1 = new ForegroundColorSpan(Color.BLUE);
        ForegroundColorSpan fcsBlue2 = new ForegroundColorSpan(Color.BLUE);

        itemCountDisplayString.setSpan(fcsBlue1, itemCountStartIndex, itemCountEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemCountDisplayString.setSpan(fcsBlue2, requestKeywordsStartIndex, requestKeywordsEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return itemCountDisplayString;
    }

}