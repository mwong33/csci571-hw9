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

public class ShippingFragment extends Fragment {

    private String itemString;

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

        // Set the shippingInformationTitle
        String shippingInformationTitleHTML = "<p style=\"color:black\"><b>Shipping Information</b></p>";
        TextView shippingInformationTitle = getView().findViewById(R.id.shippingInformationTitle);
        shippingInformationTitle.setText(Html.fromHtml(shippingInformationTitleHTML));

        // Set the shippingBulletList
        String shippingBulletListHTML = "<ul>\n" +
                "  <li><b>Handling  Time: </b></li>\n" +
                "  <li><b>One Day Shipping Available: </b></li>\n" +
                "  <li><b>Shipping Type: </b></li>\n" +
                "  <li><b>Shipping From: </b></li>\n" +
                "  <li><b>Ship To Locations: </b></li>\n" +
                "  <li><b>Expedited Shipping: </b></li>\n" +
                "</ul>  \n";
        TextView shippingBulletList = getView().findViewById(R.id.shippingBulletList);
        shippingBulletList.setText(Html.fromHtml(shippingBulletListHTML));
    }
}
