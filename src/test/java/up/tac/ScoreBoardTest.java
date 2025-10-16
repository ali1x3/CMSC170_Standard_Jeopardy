package up.tac;
import org.junit.Test;

import up.tac.Resource.ResourceManager;

import static org.junit.Assert.*; // Imports static methods like assertEquals, assertTrue, etc.

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * ScoreBoardTest
 */
public class ScoreBoardTest {
    @Test 
    public void checkReadAndWrite() {
        Map<String, Map> initial = ScoreBoard.loadScores();
        Map<String, Map> inputScores = new HashMap<>();

        Map<Integer, String> scoreValues = new HashMap<>();
        scoreValues.put(120, "Oct 13, 2025");
        inputScores.put("bobby", scoreValues);

        scoreValues = new HashMap<>();
        scoreValues.put(300, "Oct 12, 2025");
        inputScores.put("Bob", scoreValues);

        inputScores.forEach((name, scoreValue) -> {
            initial.put(name, scoreValue);

            scoreValue.forEach((score, date) -> {
                ScoreBoard.appendScore(name, (int) score, (String) date);
            });
        });

        Map<String, Map> scores = ScoreBoard.loadScores();
        scores.forEach((name, scoreValue) -> {
            assertEquals("The input and outputs were not equal", scoreValue, initial.get(name));
        });
        
        assertEquals("The sizes were not equal", initial.size(), initial.size());
    }
    
}
