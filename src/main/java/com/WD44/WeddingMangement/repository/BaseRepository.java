package com.WD44.WeddingMangement.repository;

import com.WD44.WeddingMangement.model.Storable;
import java.util.*;
import java.util.stream.Collectors;


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
