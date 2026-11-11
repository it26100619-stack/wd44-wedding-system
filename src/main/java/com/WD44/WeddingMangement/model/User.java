package com.WD44.WeddingMangement.model;
/**
 * USER MANAGEMENT - OOP concepts
 * 1. Abstraction   : User is abstract; getRole() and getDashboardPath() have no body here.
 * 2. Inheritance   : Customer, Admin (and Vendor) extend User and reuse its fields and checkLogin().
 * 3. Encapsulation : all fields are private; the password is never exposed by a getter-based check.
 */
public abstract class User extends BaseEntity {
    private String name, email, password, phone;

    public String getName() { return name; }
    public void setName(String name) { this.name = name == null ? "" : name.trim(); }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email == null ? "" : email.trim(); }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone == null ? "" : phone.trim(); }

    public boolean checkLogin(String email, String password) {
        return this.email != null && this.email.equalsIgnoreCase(email.trim()) && this.password != null && this.password.equals(password);
    }
    public abstract String getRole();
    public abstract String getDashboardPath();
    public String describe() { return getRole() + ": " + name + " (" + email + ")"; }
}
