package com.WD44.WeddingMangement.repository;
import com.WD44.WeddingMangement.model.*;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

/** USER MANAGEMENT repository - customers.txt */
@Repository
public class UserRepository extends BaseRepository<Customer> {
    public UserRepository(TextFileRepository file) { super(file); }
    @Override protected String fileName() { return "customers.txt"; }
    @Override protected Customer fromRow(String[] r) { return Customer.from(r); }

    public Optional<Customer> authenticate(String email, String password) {
        return findAll().stream().filter(c -> c.checkLogin(email, password)).findFirst();
    }
    public boolean emailExists(String email) { return findAll().stream().anyMatch(c -> c.getEmail().equalsIgnoreCase(email.trim())); }
}
