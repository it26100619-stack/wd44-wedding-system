package com.WD44.WeddingMangement.WD44.WeddingMangement.model;

public enum BookingStatus {
    PENDING  { @Override public boolean canReview() { return false; } @Override public boolean canRespond() { return true; } },
    ACCEPTED { @Override public boolean canReview() { return true; }  @Override public boolean canRespond() { return false; } },
    REJECTED { @Override public boolean canReview() { return false; } @Override public boolean canRespond() { return false; } };
    public abstract boolean canReview();
    public abstract boolean canRespond();
}
