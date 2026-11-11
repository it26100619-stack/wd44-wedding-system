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

/** USER MANAGEMENT: customer register, login, logout and profile. */
@Controller
@RequestMapping("/customer")
public class UserController extends BaseController {
    private final UserRepository users;
    public UserController(UserRepository users) { this.users = users; }

    @GetMapping("/login") public String login() { return "customer-login"; }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email, @RequestParam String password, HttpSession s, Model m) {
        Optional<Customer> cu = users.authenticate(email, password);
        if (cu.isPresent()) {
            s.setAttribute("customer", cu.get());
            return "redirect:" + cu.get().getDashboardPath();
        }
        m.addAttribute("error", "Invalid email or password.");
        return "customer-login";
    }

    @GetMapping("/register") public String register() { return "customer-register"; }

    @PostMapping("/register")
    public String doRegister(@RequestParam String name, @RequestParam String email, @RequestParam String password,
                             @RequestParam String phone, Model m) {
        if (users.emailExists(email)) {
            m.addAttribute("error", "That email is already registered.");
            return "customer-register";
        }
        Customer cu = new Customer();
        cu.setId(users.nextId()); cu.setName(c(name)); cu.setEmail(c(email)); cu.setPassword(c(password)); cu.setPhone(c(phone));
        users.save(cu);
        return "redirect:/customer/login?registered";
    }

    @GetMapping("/logout") public String logout(HttpSession s) { s.invalidate(); return "redirect:/"; }

    @GetMapping("/profile")
    public String profile(HttpSession s) { return user(s) == null ? CUSTOMER_LOGIN : "customer-profile"; }

    @PostMapping("/profile")
    public String saveProfile(@RequestParam String name, @RequestParam String phone,
                              @RequestParam(defaultValue = "") String newPassword, HttpSession s) {
        Customer cu = user(s);
        if (cu == null) return CUSTOMER_LOGIN;
        cu.setName(c(name)); cu.setPhone(c(phone));
        if (!newPassword.isBlank()) cu.setPassword(c(newPassword));
        users.upsert(cu);
        s.setAttribute("customer", cu);
        return "redirect:/customer/profile?saved";
    }
}
