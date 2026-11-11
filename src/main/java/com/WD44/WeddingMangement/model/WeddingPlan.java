package com.WD44.WeddingMangement.WD44.WeddingMangement.model;
import static com.WD44.WeddingMangement.repository.TextFileRepository.at;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * PLAN MANAGEMENT - OOP concepts
 * 1. Encapsulation : private fields; budget and guests are cleaned in their setters (guests is at least 1).
 * 2. Inheritance   : extends BaseEntity - the plan's id is the owner's customer id.
 * 3. Abstraction   : the countdown and budget maths are hidden behind getDaysLeft() / percentUsed() / remaining().
 */
public class WeddingPlan extends BaseEntity {
    private String date = "", budget = "0", guests = "1", venue = "";

    public static WeddingPlan from(String[] r) {
        WeddingPlan w = new WeddingPlan();
        w.setId(r[0]); w.setDate(r[1]); w.setBudget(r[2]); w.setGuests(r[3]); w.setVenue(at(r, 4, ""));
        return w;
    }
    @Override public String[] row() { return new String[]{getId(), date, budget, guests, venue}; }

    public String getCustomerId() { return getId(); }
    public long getDaysLeft() { try { return ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(date)); } catch (Exception e) { return 0; } }
    public long getBudgetNumber() { try { return Long.parseLong(budget); } catch (Exception e) { return 0; } }
    public long remaining(long committed) { return getBudgetNumber() - committed; }
    public long percentUsed(long committed) { return getBudgetNumber() > 0 ? Math.min(100, committed * 100 / getBudgetNumber()) : 0; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date == null ? "" : date; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue == null ? "" : venue; }
    public String getBudget() { return budget; }
    public void setBudget(String budget) { String d = budget == null ? "" : budget.replaceAll("[^0-9]", ""); this.budget = d.isEmpty() ? "0" : d; }
    public String getGuests() { return guests; }
    public void setGuests(String guests) {
        String d = guests == null ? "" : guests.replaceAll("[^0-9]", "");
        this.guests = String.valueOf(Math.max(1, d.isEmpty() ? 1 : Integer.parseInt(d)));
    }
}
