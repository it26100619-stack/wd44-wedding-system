package com.WD44.WeddingMangement.repository;
import com.WD44.WeddingMangement.model.*;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

/** STORE MANAGEMENT repository - items.txt (FixedPriceItem / PerGuestItem rows) */
@Repository
public class StoreRepository extends BaseRepository<StoreItem> {
    public StoreRepository(TextFileRepository file) { super(file); }
    @Override protected String fileName() { return "items.txt"; }
    @Override protected StoreItem fromRow(String[] r) { return StoreItem.from(r); }

    public List<StoreItem> findByVendor(String vendorId) { return findAll().stream().filter(i -> i.getVendorId().equals(vendorId)).collect(Collectors.toList()); }
    public void deleteOwned(String id, String vendorId) {
        file.write(fileName(), file.read(fileName()).stream().filter(r -> !(r[0].equals(id) && r[1].equals(vendorId))).collect(Collectors.toList()));
    }
}
