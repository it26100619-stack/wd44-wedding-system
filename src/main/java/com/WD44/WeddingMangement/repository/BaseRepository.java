package com.WD44.WeddingMangement.repository;

import com.WD44.WeddingMangement.model.Storable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Shared parent of the six component repositories.
 * Abstraction  : fileName() and fromRow() are abstract - each child says which file and how to build its object.
 * Inheritance  : UserRepository, VendorRepository, BookingRepository, PlanRepository, ReviewRepository and
 *                StoreRepository all inherit findAll/findById/save/upsert/deleteById/count from here.
 * Polymorphism : generic code works on any Storable, and children may override methods (VendorRepository.findAll).
 */
public abstract class BaseRepository<T extends Storable> {
    protected final TextFileRepository file;
    protected BaseRepository(TextFileRepository file) { this.file = file; }

    protected abstract String fileName();
    protected abstract T fromRow(String[] row);

    public List<T> findAll() { return file.read(fileName()).stream().map(this::fromRow).collect(Collectors.toList()); }
    public Optional<T> findById(String id) { return findAll().stream().filter(t -> t.getId().equals(id)).findFirst(); }
    public void save(T item) { file.append(fileName(), item.row()); }
    public String nextId() { return file.nextId(fileName()); }
    public int count() { return file.read(fileName()).size(); }

    /** Replace the row with the same id, or add it if it does not exist yet. */
    public void upsert(T item) {
        List<String[]> rows = file.read(fileName());
        boolean found = false;
        for (int i = 0; i < rows.size(); i++)
            if (rows.get(i)[0].equals(item.getId())) { rows.set(i, item.row()); found = true; }
        if (!found) rows.add(item.row());
        file.write(fileName(), rows);
    }
    public void deleteById(String id) {
        file.write(fileName(), file.read(fileName()).stream().filter(r -> !r[0].equals(id)).collect(Collectors.toList()));
    }
}
