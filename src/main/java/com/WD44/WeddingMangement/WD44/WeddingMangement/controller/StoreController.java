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

/** STORE MANAGEMENT: a vendor adds and removes the packages couples can choose from. */
@Controller
@RequestMapping("/vendor/item")
public class StoreController extends BaseController {
    private final StoreRepository store;
    public StoreController(StoreRepository store) { this.store = store; }

    @PostMapping
    public String add(@RequestParam String name, @RequestParam String price,
                      @RequestParam(defaultValue = "FIXED") String type, HttpSession s) {
        String vid = vendorId(s);
        if (vid == null) return VENDOR_LOGIN;
        StoreItem item = StoreItem.create(type);      // returns FixedPriceItem or PerGuestItem
        item.setId(store.nextId()); item.setVendorId(vid); item.setName(c(name)); item.setPrice(price);
        store.save(item);
        return "redirect:/vendor/dashboard";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, HttpSession s) {
        String vid = vendorId(s);
        if (vid == null) return VENDOR_LOGIN;
        store.deleteOwned(id, vid);
        return "redirect:/vendor/dashboard";
    }
}
