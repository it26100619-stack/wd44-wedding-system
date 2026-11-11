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

/** PLAN MANAGEMENT: wedding date, venue, budget, guests, countdown and budget tracker. */
// Refactored plan management controller
@Controller
@RequestMapping("/customer/wedding")
public class PlanController extends BaseController {
    private final PlanRepository plans;
    private final BookingRepository bookings;
    public PlanController(PlanRepository plans, BookingRepository bookings) { this.plans = plans; this.bookings = bookings; }

    @GetMapping
    public String view(HttpSession s, Model m) {
        Customer cu = user(s);
        if (cu == null) return CUSTOMER_LOGIN;
        WeddingPlan p = plans.findById(cu.getId()).orElse(null);
        m.addAttribute("plan", p);
        if (p != null) {
            long committed = bookings.findByCustomer(cu.getId()).stream()
                    .filter(b -> !b.getStatus().equals("REJECTED")).mapToLong(b -> num(b.getTotal())).sum();
            m.addAttribute("daysLeft", p.getDaysLeft()); m.addAttribute("committed", committed);
            m.addAttribute("remaining", p.remaining(committed)); m.addAttribute("percent", p.percentUsed(committed));
        }
        return "customer-wedding";
    }

    @PostMapping
    public String save(@RequestParam String date, @RequestParam(defaultValue = "") String venue, @RequestParam String budget,
                       @RequestParam String guests, HttpSession s) {
        Customer cu = user(s);
        if (cu == null) return CUSTOMER_LOGIN;
        WeddingPlan p = new WeddingPlan();
        p.setId(cu.getId()); p.setDate(c(date)); p.setVenue(c(venue)); p.setBudget(budget); p.setGuests(guests);
        plans.upsert(p);
        return "redirect:/customer/wedding?saved";
    }
}
