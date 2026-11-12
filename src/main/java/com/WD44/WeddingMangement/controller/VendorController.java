package com.WD44.WeddingMangement.controller;
import com.WD44.WeddingMangement.model.*;
import com.WD44.WeddingMangement.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class VendorController extends BaseController {
    private static final List<String> CATEGORIES = List.of("Photographer", "Caterer", "Florist", "Venue", "DJ", "Decorator");
    private final VendorRepository vendors;
    private final BookingRepository bookings;
    private final StoreRepository store;
    private final ReviewRepository reviews;

    public VendorController(VendorRepository vendors, BookingRepository bookings, StoreRepository store, ReviewRepository reviews) {
        this.vendors = vendors; this.bookings = bookings; this.store = store; this.reviews = reviews;
    }

    // ---- customer side: choose your vendor ----
    @GetMapping("/customer/dashboard")
    public String catalog(@RequestParam(defaultValue = "") String category, @RequestParam(defaultValue = "") String q,
                          HttpSession s, Model m) {
        if (user(s) == null) return CUSTOMER_LOGIN;
        m.addAttribute("vendors", vendors.search(category, q));
        m.addAttribute("categories", CATEGORIES); m.addAttribute("category", category); m.addAttribute("q", q);
        return "customer-dashboard";
    }

    // ---- vendor side ----
    @GetMapping("/vendor/login") public String login() { return "vendor-login"; }

    @PostMapping("/vendor/login")
    public String doLogin(@RequestParam String email, @RequestParam String password, HttpSession s, Model m) {
        Optional<Vendor> v = vendors.authenticate(email, password);
        if (v.isPresent()) {
            s.setAttribute("vendorId", v.get().getId());
            return "redirect:" + v.get().getDashboardPath();
        }
        m.addAttribute("error", "Invalid email or password.");
        return "vendor-login";
    }

    @GetMapping("/vendor/register") public String register() { return "vendor-register"; }

    @PostMapping("/vendor/register")
    public String doRegister(@RequestParam String name, @RequestParam String email, @RequestParam String password,
                             @RequestParam String phone, @RequestParam String category, @RequestParam String city,
                             @RequestParam String price, @RequestParam String description,
                             @RequestParam(defaultValue = "Flat rate") String unit, Model m) {
        if (vendors.emailExists(email)) {
            m.addAttribute("error", "That email is already registered.");
            return "vendor-register";
        }
        vendors.save(build(name, email, password, phone, category, city, price, unit, description));
        return "redirect:/vendor/login?registered";
    }

    @GetMapping("/vendor/dashboard")
    public String dashboard(HttpSession s, Model m) {
        String id = vendorId(s);
        if (id == null) return VENDOR_LOGIN;
        Optional<Vendor> v = vendors.findById(id);
        if (v.isEmpty()) { s.invalidate(); return VENDOR_LOGIN; }
        m.addAttribute("vendor", v.get());
        m.addAttribute("bookings", bookings.findByVendor(id));
        m.addAttribute("items", store.findByVendor(id));
        m.addAttribute("reviews", reviews.findByVendor(id));
        return "vendor-dashboard";
    }

    @GetMapping("/vendor/logout") public String logout(HttpSession s) { s.invalidate(); return "redirect:/"; }

    // ---- admin adds / removes vendors ----
    @PostMapping("/admin/vendor")
    public String adminAdd(@RequestParam String name, @RequestParam String email, @RequestParam String password,
                           @RequestParam String phone, @RequestParam String category, @RequestParam String city,
                           @RequestParam String price, @RequestParam String unit, @RequestParam String description,
                           HttpSession s, RedirectAttributes ra) {
        if (s.getAttribute("admin") == null) return "redirect:/admin/login";
        if (vendors.emailExists(email)) {
            ra.addFlashAttribute("error", "A vendor with that email already exists.");
            return "redirect:/admin/dashboard";
        }
        Vendor v = build(name, email, password, phone, category, city, price, unit, description);
        vendors.save(v);
        ra.addFlashAttribute("msg", v.getName() + " was added. They can now sign in with " + v.getEmail() + ".");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/vendor/{id}/delete")
    public String adminDelete(@PathVariable String id, HttpSession s) {
        if (s.getAttribute("admin") == null) return "redirect:/admin/login";
        vendors.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    private Vendor build(String name, String email, String password, String phone, String category, String city,
                         String price, String unit, String description) {
        Vendor v = new Vendor();
        v.setId(vendors.nextId()); v.setName(c(name)); v.setEmail(c(email)); v.setPassword(c(password)); v.setPhone(c(phone));
        v.setCategory(c(category)); v.setCity(c(city)); v.setPrice(price); v.setUnit(unit); v.setDescription(c(description));
        return v;
    }
}
