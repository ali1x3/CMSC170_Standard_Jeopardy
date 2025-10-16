package up.tac.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Score {
    private int score;

    private String name;
    private String date; // stored as a string; may be in various formats

    public Score() {}

    public Score(String name, String date, int score) {
        this.name = name;
        this.date = date;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the date formatted for display, e.g. "May 17, 2024".
     * This method will try to parse the stored date string using common patterns
     * (including "MMM-dd-yyyy") and return a display string using pattern
     * "MMM d, yyyy". If parsing fails it will return the raw stored string.
     */
    public String getDate() {
        if (date == null || date.isBlank()) return "";

        // patterns to try when parsing the stored value
        String[] parsePatterns = new String[] {
            "MMM-dd-yyyy", // e.g. May-17-2024
            "MMM d, yyyy", // e.g. May 17, 2024
            "yyyy-MM-dd",  // ISO 2024-05-17
            "MM/dd/yyyy"   // common US format
        };

        for (String p : parsePatterns) {
            try {
                DateTimeFormatter parser = DateTimeFormatter.ofPattern(p);
                LocalDate ld = LocalDate.parse(this.date, parser);
                DateTimeFormatter out = DateTimeFormatter.ofPattern("MMM d, yyyy");
                return ld.format(out);
            } catch (DateTimeParseException ignored) {
                // try next
            }
        }

        // if parse failed, return raw stored string
        return date;
    }

    /**
     * Access the raw stored date string (may be in any format).
     */
    public String getRawDate() {
        return date;
    }

    public void setDate(String date) {
        if (date == null || date.isBlank()) {
            this.date = date;
            return;
        }

        // try to parse incoming date string using known patterns and normalize to MMM-dd-yyyy
        String[] parsePatterns = new String[] {
            "MMM-dd-yyyy", // e.g. May-17-2024
            "MMM d, yyyy", // e.g. May 17, 2024
            "yyyy-MM-dd",  // ISO 2024-05-17
            "MM/dd/yyyy"   // common US format
        };

        for (String p : parsePatterns) {
            try {
                DateTimeFormatter parser = DateTimeFormatter.ofPattern(p);
                LocalDate ld = LocalDate.parse(date, parser);
                DateTimeFormatter store = DateTimeFormatter.ofPattern("MMM-dd-yyyy");
                this.date = ld.format(store);
                return;
            } catch (DateTimeParseException ignored) {
                // try next
            }
        }

        // if parsing failed, keep the raw value
        this.date = date;
    }

    /**
     * Set stored date directly from a LocalDate; it will be stored in MMM-dd-yyyy format.
     */
    public void setDate(LocalDate localDate) {
        if (localDate == null) {
            this.date = null;
            return;
        }
        DateTimeFormatter store = DateTimeFormatter.ofPattern("MMM-dd-yyyy");
        this.date = localDate.format(store);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
