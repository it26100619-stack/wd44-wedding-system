package com.WD44.WeddingMangement.repository;

import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/** Low-level file access: one record per line, fields separated by "|", files live in ./data */
@Repository
public class TextFileRepository {
    private final Path dir = Paths.get("data");

    private Path file(String name) throws IOException {
        Files.createDirectories(dir);
        return dir.resolve(name);
    }
    public static String clean(String s) {
        return s == null ? "" : s.replace("|", "/").replace("\n", " ").replace("\r", " ").trim();
    }
    /** Safe column read so older rows with fewer columns still load. */
    public static String at(String[] r, int i, String def) { return i < r.length && !r[i].isBlank() ? r[i] : def; }

    public synchronized List<String[]> read(String name) {
        try {
            Path p = file(name);
            List<String[]> rows = new ArrayList<>();
            if (!Files.exists(p)) return rows;
            for (String line : Files.readAllLines(p)) if (!line.isBlank()) rows.add(line.split("\\|", -1));
            return rows;
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }
    public synchronized void write(String name, List<String[]> rows) {
        try { Files.write(file(name), rows.stream().map(r -> String.join("|", r)).collect(Collectors.toList())); }
        catch (IOException e) { throw new UncheckedIOException(e); }
    }
    public synchronized void append(String name, String[] row) {
        List<String[]> rows = read(name);
        rows.add(row);
        write(name, rows);
    }
    public synchronized String nextId(String name) {
        return String.valueOf(read(name).stream().mapToInt(r -> Integer.parseInt(r[0])).max().orElse(0) + 1);
    }
}
