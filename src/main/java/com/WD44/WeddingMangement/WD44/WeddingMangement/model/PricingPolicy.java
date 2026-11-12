package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
public interface PricingPolicy {
    long total(long basePrice, int guests);
    String getLabel();
}
