package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
import static com.WD44.WeddingMangement.repository.TextFileRepository.at;

/**
 * STORE MANAGEMENT - OOP concepts
 * 1. Abstraction   : StoreItem is abstract; getType(), getLabel() and priceFor() are declared but not written here.
 * 2. Inheritance   : FixedPriceItem and PerGuestItem extend StoreItem and share name, price and vendorId.
 * 3. Polymorphism  : priceFor(guests) behaves differently in each subclass, and the code that calls it
 *                    never needs to know which subclass it has.
 */
public abstract class StoreItem extends BaseEntity {
    private String vendorId, name, price = "0";

    public abstract String getType();
    public abstract String getLabel();
    public abstract long priceFor(int guests);

    public static StoreItem create(String type) { return "PER_GUEST".equals(type) ? new PerGuestItem() : new FixedPriceItem(); }
    public static StoreItem from(String[] r) {
        StoreItem s = create(at(r, 4, "FIXED"));
        s.setId(r[0]); s.setVendorId(r[1]); s.setName(r[2]); s.setPrice(r[3]);
        return s;
    }
    @Override public String[] row() { return new String[]{getId(), vendorId, name, price, getType()}; }

    public long getPriceNumber() { try { return Long.parseLong(price); } catch (Exception e) { return 0; } }
    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPrice() { return price; }
    public void setPrice(String price) { String d = price == null ? "" : price.replaceAll("[^0-9]", ""); this.price = d.isEmpty() ? "0" : d; }
}
