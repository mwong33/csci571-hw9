package com.example.ebaycatalogsearch;

public class CatalogCard {

    private String catalogCardImageUrl;
    private String catalogCardTitle;
    private String catalogCardShipping;
    private boolean catalogCardTopRated;
    private String catalogCardCondition;
    private String catalogCardPrice;
    private String catalogCardProductID;

    public CatalogCard(String catalogCardImageUrl, String catalogCardTitle, String catalogCardShipping,
                       boolean catalogCardTopRated, String catalogCardCondition, String catalogCardPrice,
                       String catalogCardProductID) {
        this.catalogCardImageUrl = catalogCardImageUrl;
        this.catalogCardTitle = catalogCardTitle;
        this.catalogCardShipping = catalogCardShipping;
        this.catalogCardTopRated = catalogCardTopRated;
        this.catalogCardCondition = catalogCardCondition;
        this.catalogCardPrice = catalogCardPrice;
        this.catalogCardProductID = catalogCardProductID;
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

    public boolean getCatalogCardTopRated() {
        return catalogCardTopRated;
    }

    public String getCatalogCardCondition() {
        return catalogCardCondition;
    }

    public String getCatalogCardPrice() {
        return catalogCardPrice;
    }

    public String getCatalogCardProductID() {
        return catalogCardProductID;
    }

}
