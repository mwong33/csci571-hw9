package com.example.ebaycatalogsearch;

import android.os.Bundle;
import android.text.Html;
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

import java.util.Iterator;

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

        // Display the Item Title, Price and Shipping Price
        TextView itemTitle = getView().findViewById(R.id.itemTitle);
        TextView itemPrice = getView().findViewById(R.id.itemPrice);
        TextView itemShippingPrice = getView().findViewById(R.id.itemShippingPrice);

        try {
            JSONObject itemAdvanced = new JSONObject(itemAdvancedString);

            // Title
            String titleString = itemAdvanced.getString("title");
            itemTitle.setText(titleString);

            // Price
            String priceString = "$" + itemAdvanced.getString("sellingPrice");
            itemPrice.setText(priceString);

            // Shipping Price
            String shippingPriceString = "Ships for $" + itemAdvanced.getString("shippingCost");

            if (itemAdvanced.getString("shippingCost").equals("0.0")) {
                shippingPriceString = "FREE Shipping";
            }

            itemShippingPrice.setText(shippingPriceString);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Check to display the Product Features
        LinearLayout productFeaturesLinearView = getView().findViewById(R.id.productFeaturesLinearView);

        try {
            JSONObject item = new JSONObject(itemString);

            // Check if either Subtitle or Brand are within the JSON Object
            boolean hasSubtitle = false;
            boolean hasBrand = false;

            if (item.has("Subtitle")) {
                hasSubtitle = true;
            }

            if (item.has("ItemSpecifics")) {
                JSONObject itemSpecifics = item.getJSONObject("ItemSpecifics");
                if (itemSpecifics.has("NameValueList")) {
                    JSONArray nameValueList = itemSpecifics.getJSONArray("NameValueList");
                    if (nameValueList.length() > 0) {
                        JSONObject brandObject = nameValueList.getJSONObject(0);
                        if (brandObject.has("Name") && brandObject.get("Name").equals("Brand")) {
                            if (brandObject.has("Value")) {
                                hasBrand = true;
                            }
                        }
                    }
                }
            }

            if (hasSubtitle) {
                LinearLayout subtitleHorizontalView = getView().findViewById(R.id.subtitleHorizontalView);
                TextView subtitleValue = getView().findViewById(R.id.subtitleValue);
                subtitleValue.setText(item.getString("Subtitle"));
                subtitleHorizontalView.setVisibility(View.VISIBLE);
            }

            if (hasBrand) {
                LinearLayout brandHorizontalView = getView().findViewById(R.id.brandHorizontalView);
                TextView brandValue = getView().findViewById(R.id.brandValue);

                String brandString = item.getJSONObject("ItemSpecifics").getJSONArray("NameValueList")
                        .getJSONObject(0).getJSONArray("Value").getString(0);

                brandValue.setText(brandString);
                brandHorizontalView.setVisibility(View.VISIBLE);
            }

            if (hasSubtitle || hasBrand) {
                productFeaturesLinearView.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Check to display the Specifications
        try {
            displaySpecifications();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displaySpecifications() throws JSONException {

        JSONObject item = new JSONObject(itemString);

        if (item.has("ItemSpecifics") && item.getJSONObject("ItemSpecifics").has("NameValueList")) {
            LinearLayout specificationsLinearView = getView().findViewById(R.id.specificationsLinearView);

            int filteredCount = 0;
            JSONArray specs = item.getJSONObject("ItemSpecifics").getJSONArray("NameValueList");

            String bulletListHMTL = "<ul>";

            for (int i = 0; (i < specs.length()) && (i < 5);  i++) {
                if (!specs.getJSONObject(i).getString("Name").equals("Brand")) {
                    filteredCount++;

                    String value = specs.getJSONObject(i).getJSONArray("Value").getString(0);

                    String bulletEntry = String.format("<li>%s</li>", value);
                    bulletListHMTL += bulletEntry;
                }
            }

            bulletListHMTL += "</ul>";

            if (filteredCount > 0) {
                TextView specificationsBulletList = getView().findViewById(R.id.specificationsBulletList);
                specificationsBulletList.setText(Html.fromHtml(bulletListHMTL));
                specificationsLinearView.setVisibility(View.VISIBLE);
            }

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
