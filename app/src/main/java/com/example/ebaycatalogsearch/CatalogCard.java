package com.example.ebaycatalogsearch;

public class CatalogCard {

    private String catalogCardImageUrl;
    private String catalogCardTitle;
    private String catalogCardShipping;

    public CatalogCard(String catalogCardImageUrl, String catalogCardTitle, String catalogCardShipping) {
        this.catalogCardImageUrl = catalogCardImageUrl;
        this.catalogCardTitle = catalogCardTitle;
        this.catalogCardShipping = catalogCardShipping;
    }

    public String getCatalogCardImageUrl() {
        return catalogCardImageUrl;
    }

    public String getCatalogCardTitle() {
        return catalogCardTitle;
    }

    public String getCatalogCardShipping() {
        return catalogCardShipping;
    }

}
