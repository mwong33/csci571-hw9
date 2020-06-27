package com.example.ebaycatalogsearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductFragment extends Fragment {

    private String itemString;
    private String itemAdvancedString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Initialize the itemString from the itemString passed by SingleItem activity
        itemString = getArguments().getString("itemString");
        itemAdvancedString = getArguments().getString("itemAdvancedString");

        // Set the imageLinearView
        try {
            displayImageLinearView();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void displayImageLinearView() throws JSONException {

        LinearLayout imageLinearView = getView().findViewById(R.id.imageLinearView);

        JSONObject item = new JSONObject(itemString);
        JSONArray pictureURLArray = item.getJSONArray("PictureURL");

        for (int i = 0; i < pictureURLArray.length(); i++) {

            String pictureURL = pictureURLArray.getString(i);

            ImageView pictureView= new ImageView(getContext());
            pictureView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            pictureView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            Picasso.with(getContext()).load(pictureURL).into(pictureView);
            imageLinearView.addView(pictureView);
        }

    }
}
