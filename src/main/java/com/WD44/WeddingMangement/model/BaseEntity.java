package com.WD44.WeddingMangement.model;
/** INHERITANCE: every saved object (user, booking, review, plan, item) inherits its id from here. */
public abstract class BaseEntity implements Storable {
    private String id;
    @Override public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
