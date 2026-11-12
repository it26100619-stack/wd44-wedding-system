package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
public class PerGuestPricing implements PricingPolicy {
    @Override public long total(long basePrice, int guests) { return basePrice * guests; }
    @Override public String getLabel() { return "Per guest"; }
}
