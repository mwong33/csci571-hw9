package com.example.ebaycatalogsearch;

public class CatalogCard {

    private String catalogCardImageUrl;
    private String catalogCardTitle;

    public CatalogCard(String catalogCardImageUrl, String catalogCardTitle) {
        this.catalogCardImageUrl = catalogCardImageUrl;
        this.catalogCardTitle = catalogCardTitle;
    }

    public String getCatalogCardImageUrl() {
        return catalogCardImageUrl;
    }

    public String getCatalogCardTitle() {
        return catalogCardTitle;
    }

}
