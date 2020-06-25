package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
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

import java.util.ArrayList;

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

    // Views and Layouts
    private RecyclerView recyclerView;
    private RecyclerView.Adapter catalogAdapter;
    private GridLayoutManager gridLayoutManager;
    private LinearLayout itemsLayout;

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

        // Initialize these views
        itemsLayout = findViewById(R.id.itemsLayout);

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
                            // Create an array of the CatalogCard objects
                            ArrayList<CatalogCard> catalogCardArrayList = new ArrayList<>();
                            try {
                                catalogCardArrayList = createCatalogCardArrayList(responseItemsArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // Display the item count display
                            createItemCountDisplay(catalogCardArrayList.size());

                            // Setup the Grid View
                            recyclerView = findViewById(R.id.recyclerView);
                            recyclerView.setHasFixedSize(true);
                            gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
                            catalogAdapter = new CatalogAdapter(catalogCardArrayList);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            recyclerView.setAdapter(catalogAdapter);
                            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

                            // Display the scrollView
                            itemsLayout.setVisibility(View.VISIBLE);
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

    private void createItemCountDisplay(int catalogCardCount) {

        String htmlItemCountDisplayString = "<p>Showing <span style=\"color:#0063D1\">" + catalogCardCount + "</span> results for "
                + "<span style=\"color:#0063D1\">" + requestKeywords + "</span></p>";

        itemCountDisplay.setText(Html.fromHtml(htmlItemCountDisplayString));
    }

    private ArrayList<CatalogCard> createCatalogCardArrayList(JSONArray responseItemsArray) throws JSONException {
        ArrayList<CatalogCard> catalogCardArrayList = new ArrayList<>();

        // Make sure we limit to 50 results
        for (int i = 0; (i < responseItemsArray.length()) && (i < 50); i++) {

            JSONObject item = responseItemsArray.getJSONObject(i);

            // Get the Catalog Card Image URL
            String catalogCardImageUrl = item.getString("galleryURL");

            // Get the Catalog Card Title
            String catalogCardTitle = item.getString("title");

            // Get the Catalog Card Shipping Price. If it is 0.0, string will be "FREE Shipping"
            String shippingCost = item.getString("shippingCost");

            String catalogCardShipping = "";

            if (shippingCost.equals("0.0")) {
                catalogCardShipping = "<p><b>FREE</b> Shipping</p>";
            } else {
                catalogCardShipping = "Ships for <b>$" + shippingCost + "</b>";
            }

            // Get the Catalog Card Top Rated info. If it is we display catalogCardTopRated textView
            boolean catalogCardTopRated = false;

            if (item.getString("topRatedListing").equals("true")) {
                catalogCardTopRated = true;
            }

            // Get the Catalog Card Condition
            String catalogCardCondition = item.getString("condition");

            // Get the Catalog Card Price
            String catalogCardPrice = item.getString("sellingPrice");

            catalogCardArrayList.add(new CatalogCard(catalogCardImageUrl, catalogCardTitle,
                    catalogCardShipping, catalogCardTopRated, catalogCardCondition, catalogCardPrice));
        }

        return catalogCardArrayList;
    }

}