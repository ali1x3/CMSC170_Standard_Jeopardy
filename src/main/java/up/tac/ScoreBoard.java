package up.tac;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ScoreBoard {

    // The scoreboard file (stored beside your JAR)
    private static final Path SCORE_FILE = Paths.get("data", "scores.txt");

    // Ensure the file exists when the class loads
    static {
        try {
            if (Files.notExists(SCORE_FILE)) {
                Files.createFile(SCORE_FILE);
                System.out.println("Created new scoreboard file: " + SCORE_FILE.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error initializing scoreboard file:");
            e.printStackTrace();
        }
    }

    // Write (replace) all scores
    public static void saveScores(Map<String, Integer> scores) {
        List<String> lines = new ArrayList<>();
        for (var entry : scores.entrySet()) {
            lines.add(entry.getKey() + ":" + entry.getValue());
        }
        try {
            Files.write(SCORE_FILE, lines, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Scores saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving scores:");
            e.printStackTrace();
        }
    }

    // Append a single score entry
    public static void appendScore(String name, int score, String date) {
        String entry = name + ":" + score + ":" + date;
        try {
            Files.writeString(
                SCORE_FILE,
                entry + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            System.out.println("Added new score: " + entry);
        } catch (IOException e) {
            System.err.println("Error appending score:");
            e.printStackTrace();
        }
    }

    // Read all scores into a map
    public static Map<String, Map> loadScores() {
        Map<String, Map> scores = new HashMap<>();
        
        try {
            if (Files.exists(SCORE_FILE)) {
                List<String> lines = Files.readAllLines(SCORE_FILE);
                for (String line : lines) {
                    String[] parts = line.split(":");
                    System.out.println("SCOREBOARD.LOADSCORES(): " + parts[0] + "," + parts[1] + "," + parts[2]);
                    if (parts.length == 3) {
                        Map<Integer, String> scoreValue = new HashMap<>();
                        scoreValue.put(Integer.parseInt(parts[1]), parts[2]);
                        scores.put(parts[0], scoreValue);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading scores:");
            e.printStackTrace();
        }

        return scores;
    }

    // For quick testing
    public static void main(String[] args) {
        appendScore("Abby", 120, "Oct 16, 2025");
        appendScore("Bryan", 95, "Aug 8, 2025");

        Map<String, Map> scores = loadScores();
        System.out.println("Current scores:");
        scores.forEach((name, scoreValue) -> {
            scoreValue.forEach((score, date) -> {
                System.out.println(name + " -> " + score + ", " + date);
            });
        });
    }
}

