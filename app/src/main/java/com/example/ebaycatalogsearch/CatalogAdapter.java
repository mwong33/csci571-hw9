package com.example.ebaycatalogsearch;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    private ArrayList<CatalogCard> catalogCardArrayList;
    private OnCatalogCardListener mOnCatalogCardListener;

    public static class CatalogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView catalogCardImage;
        public TextView catalogCardTitle;
        public TextView catalogCardShipping;
        public TextView catalogCardTopRated;
        private TextView catalogCardCondition;
        private TextView catalogCardPrice;
        private OnCatalogCardListener onCatalogCardListener;

        public CatalogViewHolder(@NonNull View itemView, OnCatalogCardListener onCatalogCardListener) {
            super(itemView);
            catalogCardImage = itemView.findViewById(R.id.catalogCardImage);
            catalogCardTitle = itemView.findViewById(R.id.catalogCardTitle);
            catalogCardShipping = itemView.findViewById(R.id.catalogCardShipping);
            catalogCardTopRated = itemView.findViewById(R.id.catalogCardTopRated);
            catalogCardCondition = itemView.findViewById(R.id.catalogCardCondition);
            catalogCardPrice = itemView.findViewById(R.id.catalogCardPrice);

            this.onCatalogCardListener = onCatalogCardListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                onCatalogCardListener.onCatalogCardClick(getAdapterPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public CatalogAdapter(ArrayList<CatalogCard> catalogCardArrayList, OnCatalogCardListener mOnCatalogCardListener) {
        this.catalogCardArrayList = catalogCardArrayList;
        this.mOnCatalogCardListener = mOnCatalogCardListener;
    }

    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_card, parent, false);
        CatalogViewHolder evh = new CatalogViewHolder(v, mOnCatalogCardListener);
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
        if (currentCatalogCard.getCatalogCardTopRated()) {
            holder.catalogCardTopRated.setText(Html.fromHtml("<b>Top Rated Listing</b>"));
            holder.catalogCardTopRated.setVisibility(View.VISIBLE);
        }

        // Set the Catalog Card Condition
        String catalogCardConditionHTML = "<b><i>" + currentCatalogCard.getCatalogCardCondition() + "</i></b>";

        holder.catalogCardCondition.setText(Html.fromHtml(catalogCardConditionHTML));

        // Set the Catalog Card Price
        String catalogCardConditionPrice = "<p style=\"color:#AEBD74\"><b>$" + currentCatalogCard.getCatalogCardPrice() + "</b></p>";

        holder.catalogCardPrice.setText(Html.fromHtml(catalogCardConditionPrice));
    }

    @Override
    public int getItemCount() {
        return catalogCardArrayList.size();
    }

    public interface OnCatalogCardListener {
        void onCatalogCardClick(int position) throws JSONException;
    }
}
