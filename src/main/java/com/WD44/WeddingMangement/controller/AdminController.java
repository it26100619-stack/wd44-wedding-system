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

import org.springframework.beans.factory.annotation.Value;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Value("${admin.email:admin@everlove.com}") private String adminEmail;
    @Value("${admin.password:admin123}") private String adminPassword;
    private final VendorRepository vendors;
    private final UserRepository users;
    private final BookingRepository bookings;
    public AdminController(VendorRepository vendors, UserRepository users, BookingRepository bookings) {
        this.vendors = vendors; this.users = users; this.bookings = bookings;
    }

    @GetMapping("/login") public String login() { return "admin-login"; }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email, @RequestParam String password, HttpSession s, Model m) {
        Admin admin = new Admin(adminEmail, adminPassword);
        if (admin.checkLogin(email, password)) {
            s.setAttribute("admin", true);
            return "redirect:" + admin.getDashboardPath();
        }
        m.addAttribute("error", "Invalid admin email or password.");
        return "admin-login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession s, Model m) {
        if (s.getAttribute("admin") == null) return "redirect:/admin/login";
        m.addAttribute("vendors", vendors.findAll());
        m.addAttribute("customerCount", users.count());
        m.addAttribute("bookings", bookings.findAll());
        return "admin-dashboard";
    }

    @GetMapping("/logout") public String logout(HttpSession s) { s.invalidate(); return "redirect:/"; }
}
