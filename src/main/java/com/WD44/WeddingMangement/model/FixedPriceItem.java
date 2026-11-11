package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
public class FixedPriceItem extends StoreItem {
    //2
    @Override public String getType() { return "FIXED"; }
    @Override public String getLabel() { return "Flat rate"; }
    @Override public long priceFor(int guests) { return getPriceNumber();
    }
}
