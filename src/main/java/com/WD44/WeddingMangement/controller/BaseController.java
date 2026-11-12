package com.WD44.WeddingMangement.controller;

import com.WD44.WeddingMangement.model.Customer;
import com.WD44.WeddingMangement.repository.TextFileRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;


public abstract class BaseController {
    protected static final String CUSTOMER_LOGIN = "redirect:/customer/login";
    protected static final String VENDOR_LOGIN = "redirect:/vendor/login";

    protected static String c(String s) { return TextFileRepository.clean(s); }
    protected static long num(String s) { try { return Long.parseLong(s.replaceAll("[^0-9]", "")); } catch (Exception e) { return 0; } }
    protected Customer user(HttpSession s) { return (Customer) s.getAttribute("customer"); }
    protected String vendorId(HttpSession s) { return (String) s.getAttribute("vendorId"); }

    /** Makes "customer" available to the navigation bar on every customer page. */
    @ModelAttribute("customer") public Customer currentCustomer(HttpSession s) { return user(s); }
}
