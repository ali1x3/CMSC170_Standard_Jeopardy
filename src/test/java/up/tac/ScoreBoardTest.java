package up.tac;
import org.junit.Test;

import up.tac.model.ResourceManager;
import up.tac.model.ScoreBoard;

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
        Map<String, Integer> initial = ScoreBoard.loadScores();
        Map<String, Integer> inputScores = new HashMap<>();
        inputScores.put("bobby", 120);
        inputScores.put("Bob", 95);


        inputScores.forEach((name, score) -> {
            initial.put(name, score);
            ScoreBoard.appendScore(name, score);
        });

        Map<String, Integer> scores = ScoreBoard.loadScores();
        scores.forEach((name, score) -> {
            assertEquals("The input and outputs were not equal", score, initial.get(name));
        });
        
        assertEquals("The sizes were not equal", initial.size(), initial.size());
    }
    
}
