package com.WD44.WeddingMangement.WD44.WeddingMangement.controller;
import com.WD44.WeddingMangement.model.*;
import com.WD44.WeddingMangement.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;
import java.util.stream.Collectors;

/** REVIEW MANAGEMENT: write a review for an accepted booking. */
@Controller
@RequestMapping("/customer/review")
public class ReviewController extends BaseController {
    private final ReviewRepository reviews;
    private final BookingRepository bookings;
    public ReviewController(ReviewRepository reviews, BookingRepository bookings) { this.reviews = reviews; this.bookings = bookings; }

    private Optional<Booking> reviewable(Customer cu, String bookingId) {
        return bookings.findById(bookingId).filter(b -> b.getCustomerId().equals(cu.getId()) && b.isReviewable());
    }

    @GetMapping("/{bookingId}")
    public String form(@PathVariable String bookingId, HttpSession s, Model m) {
        Customer cu = user(s);
        if (cu == null) return CUSTOMER_LOGIN;
        Optional<Booking> b = reviewable(cu, bookingId);
        if (b.isEmpty()) return "redirect:/customer/bookings";
        m.addAttribute("booking", b.get());
        m.addAttribute("reviews", reviews.findByVendor(b.get().getVendorId()));
        return "customer-review";
    }

    @PostMapping
    public String post(@RequestParam String bookingId, @RequestParam String rating,
                       @RequestParam(defaultValue = "") String comment, HttpSession s) {
        Customer cu = user(s);
        if (cu == null) return CUSTOMER_LOGIN;
        Optional<Booking> b = reviewable(cu, bookingId);
        if (b.isPresent() && !reviews.existsForBooking(bookingId)) {
            Review r = new Review();
            r.setId(reviews.nextId()); r.setVendorId(b.get().getVendorId()); r.setCustomerName(cu.getName());
            r.setRating(rating); r.setComment(c(comment)); r.setBookingId(bookingId);
            reviews.save(r);
        }
        return "redirect:/customer/bookings";
    }
}
