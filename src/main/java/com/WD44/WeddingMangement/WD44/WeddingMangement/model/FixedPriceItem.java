package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
public class FixedPriceItem extends StoreItem {
    @Override public String getType() { return "FIXED"; }
    @Override public String getLabel() { return "Flat rate"; }
    @Override public long priceFor(int guests) { return getPriceNumber(); }
}
