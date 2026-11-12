package com.WD44.WeddingMangement.model;
import static com.WD44.WeddingMangement.repository.TextFileRepository.at;

/**
 * VENDOR MANAGEMENT - OOP concepts
 * 1. Inheritance   : Vendor extends User (name, email, password, phone, login check come from the parent).
 * 2. Polymorphism  : getRole/getDashboardPath/describe are overridden; totalFor() calls a PricingPolicy,
 *                    and FlatRatePricing / PerGuestPricing each calculate the price in their own way.
 * 3. Encapsulation : private fields, price kept as digits only, rating/reviewCount filled by the repository.
 */
public class Vendor extends User {
    private String category, city, price = "0", description, unit = "Flat rate";
    private double rating;
    private int reviewCount;

    public static Vendor from(String[] r) {
        Vendor v = new Vendor();
        v.setId(r[0]); v.setName(r[1]); v.setEmail(r[2]); v.setPassword(r[3]); v.setPhone(r[4]);
        v.setCategory(r[5]); v.setCity(r[6]); v.setPrice(r[7]); v.setDescription(r[8]); v.setUnit(at(r, 9, "Flat rate"));
        return v;
    }
    @Override public String[] row() {
        return new String[]{getId(), getName(), getEmail(), getPassword(), getPhone(), category, city, price, description, unit};
    }
    @Override public String getRole() { return "VENDOR"; }
    @Override public String getDashboardPath() { return "/vendor/dashboard"; }
    @Override public String describe() { return super.describe() + " - " + category + ", " + city; }

    public PricingPolicy getPricing() { return "Per guest".equals(unit) ? new PerGuestPricing() : new FlatRatePricing(); }
    public long totalFor(int guests) { return getPricing().total(getPriceNumber(), guests); }
    public long getPriceNumber() { try { return Long.parseLong(price); } catch (Exception e) { return 0; } }
    public String getIcon() {
        return switch (category == null ? "" : category) {
            case "Photographer" -> "📷"; case "Caterer" -> "🍽️"; case "Florist" -> "🌸";
            case "Venue" -> "🏛️"; case "DJ" -> "🎧"; default -> "✨";
        };
    }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPrice() { return price; }
    public void setPrice(String price) { String d = price == null ? "" : price.replaceAll("[^0-9]", ""); this.price = d.isEmpty() ? "0" : d; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = "Per guest".equals(unit) ? "Per guest" : "Flat rate"; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
}
