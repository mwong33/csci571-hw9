package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleItem extends AppCompatActivity {

    private String productID;
    private String title;

    // Request Object
    private RequestQueue requestQueue;

    // Classes for Tabular View
    private SectionsPageAdapter sectionsPageadapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int[] tabIconArray = {
            R.drawable.information_variant_selector,
            R.drawable.ic_seller,
            R.drawable.truck_delivery_selector
    };

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

        // Initialize the requestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Make the getSingleItem request
        getSingleItem();

        // Tab View Creation
        sectionsPageadapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = sectionsPageadapter;
        adapter.addFragment(new ProductFragment(), "Product");
        adapter.addFragment(new SellerInfoFragment(), "Seller Info");
        adapter.addFragment(new ShippingFragment(), "Shipping");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIconArray[0]);
        tabLayout.getTabAt(1).setIcon(tabIconArray[1]);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#0063D1"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).setIcon(tabIconArray[2]);
    }

    private void getSingleItem() {

        String requestUrl = "https://mjwong-csci-571-hw8.wl.r.appspot.com/singleItem?productID=" + productID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Remove the Progress Bar
                        ProgressBar singleItemProgressBar = findViewById(R.id.singleItemProgressBar);
                        singleItemProgressBar.setVisibility(View.GONE);
                        TextView singleItemProgressText = findViewById(R.id.singleItemProgressText);
                        singleItemProgressText.setVisibility(View.GONE);

                        // Get the JSONObject of the response
                        try {
                            JSONObject item = response.getJSONObject("Item");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Display the Fragment Container
                        viewPager.setVisibility(View.VISIBLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}