package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
public class PerGuestItem extends StoreItem {
    @Override public String getType() { return "PER_GUEST"; }
    @Override public String getLabel() { return "Per guest"; }
    @Override public long priceFor(int guests) { return getPriceNumber() * guests; }
}
