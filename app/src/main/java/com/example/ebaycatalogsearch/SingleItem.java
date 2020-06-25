package com.example.ebaycatalogsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleItem extends AppCompatActivity {

    private String productID;
    private String title;

    // Request Object
    private RequestQueue requestQueue;

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

    }

    private void getSingleItem() {

        String requestUrl = "https://mjwong-csci-571-hw8.wl.r.appspot.com/singleItem?productID=" + productID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



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