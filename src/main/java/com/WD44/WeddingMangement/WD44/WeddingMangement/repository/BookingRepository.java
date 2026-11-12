package com.WD44.WeddingMangement.WD44.WeddingMangement.repository;
import com.WD44.WeddingMangement.model.*;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class BookingRepository extends BaseRepository<Booking> {
    public BookingRepository(TextFileRepository file) { super(file); }
    @Override protected String fileName() { return "bookings.txt"; }
    @Override protected Booking fromRow(String[] r) { return Booking.from(r); }

    public List<Booking> findByCustomer(String customerId) { return findAll().stream().filter(b -> b.getCustomerId().equals(customerId)).collect(Collectors.toList()); }
    public List<Booking> findByVendor(String vendorId) { return findAll().stream().filter(b -> b.getVendorId().equals(vendorId)).collect(Collectors.toList()); }
}
