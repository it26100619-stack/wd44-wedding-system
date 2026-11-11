package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
public class FlatRatePricing implements PricingPolicy {
    //3
    @Override public long total(long basePrice, int guests) { return basePrice; }
    @Override public String getLabel() { return "Flat rate"; }
}
