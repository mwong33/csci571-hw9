package com.example.ebaycatalogsearch;

public class CatalogCard {

    private String catalogCardImageUrl;
    private String catalogCardTitle;
    private String catalogCardShipping;
    private boolean catalogCardTopRated;
    private String catalogCardCondition;

    public CatalogCard(String catalogCardImageUrl, String catalogCardTitle, String catalogCardShipping,
                       boolean catalogCardTopRated, String catalogCardCondition) {
        this.catalogCardImageUrl = catalogCardImageUrl;
        this.catalogCardTitle = catalogCardTitle;
        this.catalogCardShipping = catalogCardShipping;
        this.catalogCardTopRated = catalogCardTopRated;
        this.catalogCardCondition = catalogCardCondition;
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

    public boolean catalogCardTopRated() {
        return catalogCardTopRated;
    }

    public String catalogCardCondition() {
        return catalogCardCondition;
    }

}
