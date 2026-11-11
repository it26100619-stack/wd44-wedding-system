package com.WD44.WeddingMangement.model;
/** Not stored in a file: built from the admin.email / admin.password settings. */
public class Admin extends User {
    public Admin(String email, String password) { setName("Administrator"); setEmail(email); setPassword(password); }
    @Override public String[] row() { return new String[]{getId(), getName(), getEmail()}; }
    @Override public String getRole() { return "ADMIN"; }
    @Override public String getDashboardPath() { return "/admin/dashboard"; }
}
