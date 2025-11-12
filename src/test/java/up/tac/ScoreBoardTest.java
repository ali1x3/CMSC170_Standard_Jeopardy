package up.tac;
import org.junit.Test;

import up.tac.ScoreBoard.ScoreData;
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
        Map<String, ScoreData> initial = ScoreBoard.loadScores();
        Map<String, ScoreData> inputScores = new HashMap<>();

        inputScores.put("bobby", new ScoreData(120, "Oct 13, 2025"));
        inputScores.put("Bob", new ScoreData(300, "Oct 12, 2025"));

        inputScores.forEach((name, scoreData) -> {
            initial.put(name, scoreData);
                ScoreBoard.appendScore(name, scoreData.score(), scoreData.date());
        });

        Map<String, ScoreData> scores = ScoreBoard.loadScores();
        scores.forEach((name, scoreValue) -> {
            assertEquals("The input and outputs were not equal", scoreValue, initial.get(name));
        });
        
        assertEquals("The sizes were not equal", initial.size(), scores.size());
    }
    
}
