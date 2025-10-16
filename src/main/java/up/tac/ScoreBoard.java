package up.tac;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ScoreBoard {
    record ScoreData(int score, String date) {}        

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
    public static void saveScores(Map<String, ScoreData> scores) {
        List<String> lines = new ArrayList<>();
        for (var entry : scores.entrySet()) {
            lines.add(entry.getKey() + ":" + entry.getValue().score() + ":" + entry.getValue().date());
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
        Map<String, ScoreData> scores = loadScores();

        // Update only if player doesn't exist or has a lower score
        if (!scores.containsKey(name) || score > scores.get(name).score()) {
            scores.put(name, new ScoreData(score, date));
            System.out.println("Updated high score for " + name + ": " + score);
        } else {
            System.out.println("No update. Existing score is higher or equal.");
        }

        // Now write the entire map back to the file
        saveScores(scores);
    }
    // Read all scores into a map
    public static Map<String, ScoreData> loadScores() {
        Map<String, ScoreData> scores = new HashMap<>();
        
        try {
            if (Files.exists(SCORE_FILE)) {
                List<String> lines = Files.readAllLines(SCORE_FILE);
                for (String line : lines) {
                    String[] parts = line.split(":");
                    System.out.println("SCOREBOARD.LOADSCORES(): " + parts[0] + "," + parts[1] + "," + parts[2]);
                    if (parts.length == 3) {
                        scores.put(parts[0], new ScoreData(Integer.parseInt(parts[1]), parts[2]));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading scores:");
            e.printStackTrace();
        }

        return scores;
    }
}

