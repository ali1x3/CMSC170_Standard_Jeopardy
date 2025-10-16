package up.tac.model;

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
    public static void appendScore(String name, int score) {
        String entry = name + ":" + score;
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
    public static Map<String, Integer> loadScores() {
        Map<String, Integer> scores = new HashMap<>();
        try {
            if (Files.exists(SCORE_FILE)) {
                List<String> lines = Files.readAllLines(SCORE_FILE);
                for (String line : lines) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        scores.put(parts[0], Integer.parseInt(parts[1]));
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
        appendScore("Alice", 120);
        appendScore("Bob", 95);

        Map<String, Integer> scores = loadScores();
        System.out.println("Current scores:");
        scores.forEach((name, score) -> System.out.println(name + " -> " + score));
    }
}

