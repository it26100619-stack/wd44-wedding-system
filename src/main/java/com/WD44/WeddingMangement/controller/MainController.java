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
public class MainController extends BaseController {
    private final VendorRepository vendors;
    public MainController(VendorRepository vendors) { this.vendors = vendors; }

    @GetMapping("/")
    public String index(Model m) { m.addAttribute("vendors", vendors.findAll()); return "index"; }
}
