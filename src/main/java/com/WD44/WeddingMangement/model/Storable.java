package com.WD44.WeddingMangement.model;
/** ABSTRACTION: the contract every saved object follows. */
public interface Storable {
    String getId();
    String[] row();
}
