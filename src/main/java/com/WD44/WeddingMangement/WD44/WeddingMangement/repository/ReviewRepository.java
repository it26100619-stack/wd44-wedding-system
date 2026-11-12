package com.WD44.WeddingMangement.WD44.WeddingMangement.repository;
import com.WD44.WeddingMangement.model.*;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ReviewRepository extends BaseRepository<Review> {
    public ReviewRepository(TextFileRepository file) { super(file); }
    @Override protected String fileName() { return "reviews.txt"; }
    @Override protected Review fromRow(String[] r) { return Review.from(r); }

    public List<Review> findByVendor(String vendorId) {
        List<Review> list = findAll().stream().filter(r -> r.getVendorId().equals(vendorId)).collect(Collectors.toList());
        Collections.sort(list);   // uses Review.compareTo (newest first)
        return list;
    }
    public boolean existsForBooking(String bookingId) { return findAll().stream().anyMatch(r -> r.getBookingId().equals(bookingId)); }
    public List<String> reviewedBookingIds() { return findAll().stream().map(Review::getBookingId).collect(Collectors.toList()); }
}
