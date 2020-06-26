package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class Catalog extends AppCompatActivity implements CatalogAdapter.OnCatalogCardListener {

    // Response and Request Data
    private String requestUrl;
    private String requestKeywords;
    private JSONObject responseJSON;
    private JSONArray responseItemsArray;
    private int itemCount;

    // Request Object
    private RequestQueue requestQueue;

    // Swipe Refresh Layout
    private SwipeRefreshLayout swipeRefreshItems;

    // Loading Bar elements
    private ProgressBar catalogProgressBar;
    private TextView catalogProgressText;

    // No Records element
    private TextView noRecords;

    // Items Elements
    private TextView itemCountDisplay;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter catalogAdapter;
    private GridLayoutManager gridLayoutManager;

    // Catalog Card Array
    private ArrayList<CatalogCard> catalogCardArrayList;

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

        // Initialize Items Elements
        itemCountDisplay = findViewById(R.id.itemCountDisplay);
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize the requestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Initialize the swipeRefreshItems
        swipeRefreshItems = findViewById(R.id.swipeRefreshItems);

        // Create the onRefresh method for swipeRefreshItems
        swipeRefreshItems.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        // Initialize the Catalog Card Array List
        catalogCardArrayList = new ArrayList<>();

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
                            try {
                                catalogCardArrayList = createCatalogCardArrayList(responseItemsArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // Display the item count display
                            createItemCountDisplay(catalogCardArrayList.size());

                            // Setup the Grid View
                            setupGridView();

                            // Display the scrollView
                            itemCountDisplay.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
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

    private void refreshItems() {
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
                            try {
                                catalogCardArrayList = createCatalogCardArrayList(responseItemsArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // Display the item count display
                            createItemCountDisplay(catalogCardArrayList.size());

                            // Setup the Grid View
                            setupGridView();

                            // Display the scrollView
                            itemCountDisplay.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        if(swipeRefreshItems.isRefreshing()) {
                            swipeRefreshItems.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(swipeRefreshItems.isRefreshing()) {
                    swipeRefreshItems.setRefreshing(false);
                }
            }
        });
        requestQueue.add(request);
    }

    private void setupGridView() {
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        catalogAdapter = new CatalogAdapter(catalogCardArrayList, this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(catalogAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
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

            // Get the Catalog Card Product ID
            String catalogCardProductID = item.getString("productID");

            catalogCardArrayList.add(new CatalogCard(catalogCardImageUrl, catalogCardTitle,
                    catalogCardShipping, catalogCardTopRated, catalogCardCondition, catalogCardPrice,
                    catalogCardProductID));
        }

        return catalogCardArrayList;
    }

    @Override
    public void onCatalogCardClick(int position) throws JSONException {
        Intent intent = new Intent(this, SingleItem.class);

        // Pass the Item Title and Product ID and the entire JSON Response (allItemsString)
        String catalogCardProductID = catalogCardArrayList.get(position).getCatalogCardProductID();
        intent.putExtra("productID", catalogCardProductID);

        String catalogCardTitle = catalogCardArrayList.get(position).getCatalogCardTitle();
        intent.putExtra("title", catalogCardTitle);

        JSONObject item = responseItemsArray.getJSONObject(position);
        String itemAdvancedString = item.toString();
        intent.putExtra("itemAdvancedString", itemAdvancedString);

        startActivity(intent);
    }
}