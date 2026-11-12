package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
import static com.WD44.WeddingMangement.repository.TextFileRepository.at;

/**
 * BOOKING MANAGEMENT - OOP concepts
 * 1. Encapsulation : private fields; setStatus() rejects any value that is not a valid BookingStatus.
 * 2. Inheritance   : Booking extends BaseEntity (id) and so can be saved by the shared BaseRepository.
 * 3. Polymorphism  : the BookingStatus constants decide canReview() / canRespond() themselves.
 */
public class Booking extends BaseEntity {
    private String customerId, customerName, vendorId, vendorName, date, note, guests = "0", total = "0";
    private BookingStatus status = BookingStatus.PENDING;

    public static Booking from(String[] r) {
        Booking b = new Booking();
        b.setId(r[0]); b.setCustomerId(r[1]); b.setCustomerName(r[2]); b.setVendorId(r[3]); b.setVendorName(r[4]);
        b.setDate(r[5]); b.setNote(r[6]); b.setStatus(r[7]); b.setGuests(at(r, 8, "0")); b.setTotal(at(r, 9, "0"));
        return b;
    }
    @Override public String[] row() { return new String[]{getId(), customerId, customerName, vendorId, vendorName, date, note, status.name(), guests, total}; }

    public String getStatus() { return status.name(); }
    public void setStatus(String s) { this.status = BookingStatus.valueOf(s.trim().toUpperCase()); }
    public BookingStatus getStatusEnum() { return status; }
    public boolean isReviewable() { return status.canReview(); }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getGuests() { return guests; }
    public void setGuests(String guests) { this.guests = guests; }
    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }
}
