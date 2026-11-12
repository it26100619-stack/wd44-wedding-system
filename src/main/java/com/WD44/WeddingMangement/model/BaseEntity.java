package com.WD44.WeddingMangement.model;

public abstract class BaseEntity implements Storable {
    private String id;
    @Override public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
