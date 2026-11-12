package com.WD44.WeddingMangement.WD44.WeddingMangement.repository;
import com.WD44.WeddingMangement.model.*;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

/** VENDOR MANAGEMENT repository - vendors.txt (ratings are calculated from the review repository) */
@Repository
public class VendorRepository extends BaseRepository<Vendor> {
    private final ReviewRepository reviews;
    public VendorRepository(TextFileRepository file, ReviewRepository reviews) { super(file); this.reviews = reviews; }
    @Override protected String fileName() { return "vendors.txt"; }
    @Override protected Vendor fromRow(String[] r) { return Vendor.from(r); }

    /** Overrides the parent version to fill in rating and review count. */
    @Override public List<Vendor> findAll() {
        List<Review> all = reviews.findAll();
        List<Vendor> list = super.findAll();
        for (Vendor v : list) {
            List<Review> mine = all.stream().filter(r -> r.getVendorId().equals(v.getId())).collect(Collectors.toList());
            v.setReviewCount(mine.size());
            v.setRating(mine.stream().mapToInt(Review::getRatingValue).average().orElse(0));
        }
        return list;
    }
    public List<Vendor> search(String category, String q) {
        String n = q == null ? "" : q.trim().toLowerCase();
        return findAll().stream()
                .filter(v -> category == null || category.isBlank() || v.getCategory().equals(category))
                .filter(v -> n.isEmpty() || v.getName().toLowerCase().contains(n) || v.getCity().toLowerCase().contains(n))
                .collect(Collectors.toList());
    }
    public Optional<Vendor> authenticate(String email, String password) { return findAll().stream().filter(v -> v.checkLogin(email, password)).findFirst(); }
    public boolean emailExists(String email) { return findAll().stream().anyMatch(v -> v.getEmail().equalsIgnoreCase(email.trim())); }
}
