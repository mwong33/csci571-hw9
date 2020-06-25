package com.example.ebaycatalogsearch;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    private ArrayList<CatalogCard> catalogCardArrayList;

    public static class CatalogViewHolder extends RecyclerView.ViewHolder {

        public ImageView catalogCardImage;
        public TextView catalogCardTitle;
        public TextView catalogCardShipping;
        public TextView catalogCardTopRated;
        private TextView catalogCardCondition;

        public CatalogViewHolder(@NonNull View itemView) {
            super(itemView);
            catalogCardImage = itemView.findViewById(R.id.catalogCardImage);
            catalogCardTitle = itemView.findViewById(R.id.catalogCardTitle);
            catalogCardShipping = itemView.findViewById(R.id.catalogCardShipping);
            catalogCardTopRated = itemView.findViewById(R.id.catalogCardTopRated);
            catalogCardCondition = itemView.findViewById(R.id.catalogCardCondition);
        }
    }

    public CatalogAdapter(ArrayList<CatalogCard> catalogCardArrayList) {
        this.catalogCardArrayList = catalogCardArrayList;
    }

    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_card, parent, false);
        CatalogViewHolder evh = new CatalogViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder holder, int position) {
        CatalogCard currentCatalogCard = catalogCardArrayList.get(position);

        // Setting the Catalog Card Image
        String catalogCardImageUrl = currentCatalogCard.getCatalogCardImageUrl();

        if (!catalogCardImageUrl.equals("https://csci571.com/hw/hw8/images/ebayDefault.png")) {
            Picasso.with(holder.catalogCardImage.getContext()).load(catalogCardImageUrl).into(holder.catalogCardImage);
        }

        // Setting the Catalog Card Title
        String catalogCardTitleHTML = "<p style=\"color:black\"><b>" + currentCatalogCard.getCatalogCardTitle() + "</b></p>";

        holder.catalogCardTitle.setText(Html.fromHtml(catalogCardTitleHTML));

        // Setting the Catalog Card Shipping Details
        holder.catalogCardShipping.setText(Html.fromHtml(currentCatalogCard.getCatalogCardShipping()));

        // Set the Catalog Card Top Rated text if it is true
        if (currentCatalogCard.catalogCardTopRated()) {
            holder.catalogCardTopRated.setText(Html.fromHtml("<b>Top Rated Listing</b>"));
            holder.catalogCardTopRated.setVisibility(View.VISIBLE);
        }

        // Set the Catalog Card Condition
        String catalogCardConditionHTML = "<b><i>" + currentCatalogCard.catalogCardCondition() + "</i></b>";

        holder.catalogCardCondition.setText(Html.fromHtml(catalogCardConditionHTML));

    }

    @Override
    public int getItemCount() {
        return catalogCardArrayList.size();
    }
}
