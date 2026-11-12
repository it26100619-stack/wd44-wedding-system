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

/** BOOKING MANAGEMENT: date and guests, send request, my bookings, vendor accepts or declines. */
@Controller
public class BookingController extends BaseController {
    private final BookingRepository bookings;
    private final VendorRepository vendors;
    private final PlanRepository plans;
    private final StoreRepository store;
    private final ReviewRepository reviews;

    public BookingController(BookingRepository bookings, VendorRepository vendors, PlanRepository plans,
                             StoreRepository store, ReviewRepository reviews) {
        this.bookings = bookings; this.vendors = vendors; this.plans = plans; this.store = store; this.reviews = reviews;
    }

    @GetMapping("/customer/book/{vendorId}")
    public String form(@PathVariable String vendorId, HttpSession s, Model m) {
        Customer cu = user(s);
        if (cu == null) return CUSTOMER_LOGIN;
        Optional<Vendor> v = vendors.findById(vendorId);
        if (v.isEmpty()) return "redirect:/customer/dashboard";
        m.addAttribute("vendor", v.get());
        m.addAttribute("plan", plans.findById(cu.getId()).orElse(null));
        m.addAttribute("items", store.findByVendor(vendorId));
        return "customer-book";
    }

    @PostMapping("/customer/book")
    public String book(@RequestParam String vendorId, @RequestParam String date, @RequestParam int guests,
                       @RequestParam(defaultValue = "") String note, HttpSession s) {
        Customer cu = user(s);
        if (cu == null) return CUSTOMER_LOGIN;
        Optional<Vendor> v = vendors.findById(vendorId);
        if (v.isEmpty() || date.isBlank() || guests < 1) return "redirect:/customer/dashboard";
        Booking b = new Booking();
        b.setId(bookings.nextId()); b.setCustomerId(cu.getId()); b.setCustomerName(cu.getName());
        b.setVendorId(v.get().getId()); b.setVendorName(v.get().getName()); b.setDate(c(date)); b.setNote(c(note));
        b.setStatus("PENDING"); b.setGuests(String.valueOf(guests)); b.setTotal(String.valueOf(v.get().totalFor(guests)));
        bookings.save(b);
        return "redirect:/customer/bookings?sent";
    }

    @GetMapping("/customer/bookings")
    public String mine(HttpSession s, Model m) {
        Customer cu = user(s);
        if (cu == null) return CUSTOMER_LOGIN;
        m.addAttribute("bookings", bookings.findByCustomer(cu.getId()));
        m.addAttribute("reviewedIds", reviews.reviewedBookingIds());
        return "customer-bookings";
    }

    @PostMapping("/vendor/booking/{id}/{status}")
    public String respond(@PathVariable String id, @PathVariable String status, HttpSession s) {
        String vid = vendorId(s);
        if (vid == null) return VENDOR_LOGIN;
        bookings.findById(id).filter(b -> b.getVendorId().equals(vid)).ifPresent(b -> {
            if (b.getStatusEnum().canRespond() && (status.equals("ACCEPTED") || status.equals("REJECTED"))) {
                b.setStatus(status);
                bookings.upsert(b);
            }
        });
        return "redirect:/vendor/dashboard";
    }
}
