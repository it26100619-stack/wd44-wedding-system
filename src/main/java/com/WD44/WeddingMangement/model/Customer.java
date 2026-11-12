package com.WD44.WeddingMangement.model;
public class Customer extends User {
    public static Customer from(String[] r) {
        Customer c = new Customer();
        c.setId(r[0]); c.setName(r[1]); c.setEmail(r[2]); c.setPassword(r[3]); c.setPhone(r[4]);
        return c;
    }
    @Override public String[] row() { return new String[]{getId(), getName(), getEmail(), getPassword(), getPhone()}; }
    @Override public String getRole() { return "CUSTOMER"; }
    @Override public String getDashboardPath() { return "/customer/dashboard"; }
}
