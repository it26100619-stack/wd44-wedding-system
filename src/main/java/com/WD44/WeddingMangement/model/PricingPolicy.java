package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
public interface PricingPolicy {
    //6
    long total(long basePrice, int guests);
    String getLabel();
}
