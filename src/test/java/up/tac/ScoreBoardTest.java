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
        HashMap<String, Integer> inputScores = new HashMap<>();
        inputScores.put("Alice", 120);
        inputScores.put("Bob", 95);

        inputScores.forEach((name, score) -> ScoreBoard.appendScore(name, score));

        Map<String, Integer> scores = ScoreBoard.loadScores();
        scores.forEach((name, score) -> {
            assertEquals("The input and outputs were not equal", score, inputScores.get(name));
        });
    }
    
}
