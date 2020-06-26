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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class ShippingFragment extends Fragment {

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
        itemAdvancedString = getArguments().getString("itemAdvancedString");
        try {
            createShippingInformation();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void createShippingInformation() throws JSONException {
        // Create the JSONObject
        JSONObject itemAdvanced = new JSONObject(itemAdvancedString);

        // Set the shippingInformationTitle
        String shippingInformationTitleHTML = "<p style=\"color:black\"><b>Shipping Information</b></p>";
        TextView shippingInformationTitle = getView().findViewById(R.id.shippingInformationTitle);
        shippingInformationTitle.setText(Html.fromHtml(shippingInformationTitleHTML));

        // Set the shippingBulletList
        JSONObject shippingInfo = itemAdvanced.getJSONArray("shippingInfo").getJSONObject(0);

        Iterator<String> keys = shippingInfo.keys();

        String shippingBulletListHTML = "<ul>";

        while(keys.hasNext()) {
            String key = keys.next();
            if (!key.equals("shippingServiceCost")) {
                JSONArray keyValueArray = shippingInfo.getJSONArray(key);
                String value = keyValueArray.getString(0);

                if (value.equals("true")) {
                    value = "Yes";
                } else if (value.equals("false")) {
                    value = "No";
                }

                String bulletEntry = String.format("<li><b>%s: </b>%s</li>", convertToReadable(key), value);

                shippingBulletListHTML += bulletEntry;

            }
        }

        shippingBulletListHTML += "</ul>";

        TextView shippingBulletList = getView().findViewById(R.id.shippingBulletList);
        shippingBulletList.setText(Html.fromHtml(shippingBulletListHTML));
    }

    private String convertToReadable(String camelCase) {

        String[] splitCamelCaseArray = camelCase.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");

        String readableString = String.join(" ", splitCamelCaseArray);

        readableString = readableString.substring(0,1).toUpperCase() + readableString.substring(1);

        return readableString;
    }
}
