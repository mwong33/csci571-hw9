package com.example.ebaycatalogsearch;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    private ArrayList<CatalogCard> catalogCardArrayList;

    public static class CatalogViewHolder extends RecyclerView.ViewHolder {

        public TextView catalogCardTitle;

        public CatalogViewHolder(@NonNull View itemView) {
            super(itemView);
            catalogCardTitle = itemView.findViewById(R.id.catalogCardTitle);
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

        holder.catalogCardTitle.setText(currentCatalogCard.getCatalogCardTitle());
    }

    @Override
    public int getItemCount() {
        return catalogCardArrayList.size();
    }
}
