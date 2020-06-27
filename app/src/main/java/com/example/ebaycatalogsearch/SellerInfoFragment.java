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

import java.util.Iterator;

public class SellerInfoFragment extends Fragment {

    private String itemString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.seller_info_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Initialize the itemString from the itemString passed by SingleItem activity
        itemString = getArguments().getString("itemString");

        try {
            createSellerInformation();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void createSellerInformation() throws JSONException {
        // Create the JSONObject
        JSONObject item = new JSONObject(itemString);

        // Set the sellerBulletList
        JSONObject seller = item.getJSONObject("Seller");
        TextView sellerBulletList = getView().findViewById(R.id.sellerBulletList);
        sellerBulletList.setText(Html.fromHtml(createBulletList(seller)));

        // Set the returnPoliciesBulletList
        JSONObject returnPolicy = item.getJSONObject("ReturnPolicy");
        TextView returnPoliciesBulletList = getView().findViewById(R.id.returnPoliciesBulletList);
        returnPoliciesBulletList.setText(Html.fromHtml(createBulletList(returnPolicy)));

    }

    private String createBulletList(JSONObject json) throws JSONException{
        String bulletListHTML = "<ul>";

        Iterator<String> keys = json.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            String value = json.getString(key);
            String bulletEntry = String.format("<li><b>%s: </b>%s</li>", convertToReadable(key), value);
            bulletListHTML += bulletEntry;
        }

        bulletListHTML += "</ul>";

        return bulletListHTML;
    }

    private String convertToReadable(String camelCase) {

        String[] splitCamelCaseArray = camelCase.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");

        String readableString = String.join(" ", splitCamelCaseArray);

        readableString = readableString.substring(0,1).toUpperCase() + readableString.substring(1);

        return readableString;
    }
}
