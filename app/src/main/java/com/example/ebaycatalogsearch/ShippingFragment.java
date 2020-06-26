package com.example.ebaycatalogsearch;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class ShippingFragment extends Fragment {

    private String itemString;
    private String itemAdvancedString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shipping_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Initialize the itemString from the itemString passed by SingleItem activity
        itemString = getArguments().getString("itemString");
        itemAdvancedString = getArguments().getString("itemAdvancedString");
        try {
            createShippingInformation();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void createShippingInformation() throws JSONException {
        // Create the JSONObject
        JSONObject item = new JSONObject(itemString);
        JSONObject itemAdvanced = new JSONObject(itemAdvancedString);

        // Set the shippingInformationTitle
        String shippingInformationTitleHTML = "<p style=\"color:black\"><b>Shipping Information</b></p>";
        TextView shippingInformationTitle = getView().findViewById(R.id.shippingInformationTitle);
        shippingInformationTitle.setText(Html.fromHtml(shippingInformationTitleHTML));

        // Set the shippingBulletList
        String handlingTime = item.getString("HandlingTime");

        String oneDayShippingAvailable = "No";
        if (itemAdvanced.getString("oneDayShippingAvailable").equals("true")) {
            oneDayShippingAvailable = "Yes";
        }

        String shippingType = itemAdvanced.getString("shippingType");
        
        String shippingBulletListHTML = String.format("<ul>\n" +
                "  <li><b>Handling  Time: </b>%s</li>\n" +
                "  <li><b>One Day Shipping Available: </b>%s</li>\n" +
                "  <li><b>Shipping Type: </b>%s</li>\n" +
                "  <li><b>Shipping From: </b>%s</li>\n" +
                "  <li><b>Ship To Locations: </b>%s</li>\n" +
                "  <li><b>Expedited Shipping: </b>%s</li>\n" +
                "</ul>  \n", handlingTime, oneDayShippingAvailable, shippingType, 4, 5, 6, 7);
        TextView shippingBulletList = getView().findViewById(R.id.shippingBulletList);
        shippingBulletList.setText(Html.fromHtml(shippingBulletListHTML));
    }
}
