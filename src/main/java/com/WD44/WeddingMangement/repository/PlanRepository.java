package com.WD44.WeddingMangement.repository;
import com.WD44.WeddingMangement.model.*;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

/** PLAN MANAGEMENT repository - plans.txt (one plan per customer, id = customer id) */
@Repository
public class PlanRepository extends BaseRepository<WeddingPlan> {
    public PlanRepository(TextFileRepository file) { super(file); }
    @Override protected String fileName() { return "plans.txt"; }
    @Override protected WeddingPlan fromRow(String[] r) { return WeddingPlan.from(r); }
}
