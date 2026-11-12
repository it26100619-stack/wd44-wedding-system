package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
import static com.WD44.WeddingMangement.repository.TextFileRepository.at;

public class Review extends BaseEntity implements Comparable<Review> {
    private String vendorId, customerName, rating = "5", comment = "", bookingId = "";

    public static Review from(String[] r) {
        Review v = new Review();
        v.setId(r[0]); v.setVendorId(r[1]); v.setCustomerName(r[2]); v.setRating(r[3]); v.setComment(r[4]); v.setBookingId(at(r, 5, ""));
        return v;
    }
    @Override public String[] row() { return new String[]{getId(), vendorId, customerName, rating, comment, bookingId}; }
    @Override public int compareTo(Review o) { return Integer.compare(Integer.parseInt(o.getId()), Integer.parseInt(getId())); }
    @Override public String toString() { return customerName + " rated " + rating + "/5"; }

    public int getRatingValue() { return Integer.parseInt(rating); }
    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment == null ? "" : comment; }
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public String getRating() { return rating; }
    public void setRating(String rating) {
        int n;
        try { n = Integer.parseInt(rating.trim()); } catch (Exception e) { n = 5; }
        this.rating = String.valueOf(Math.max(1, Math.min(5, n)));
    }
}
