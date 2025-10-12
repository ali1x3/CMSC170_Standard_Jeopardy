package up.tac;

import org.junit.Test;

import up.tac.Resource.ResourceManager;

import static org.junit.Assert.*; // Imports static methods like assertEquals, assertTrue, etc.

import java.net.URL;
import java.util.List;

public class BackendTest {
    ResourceManager resourceManager = new ResourceManager();

    @Test
    public void testSuccessfulLoadOfCsv() {
        URL path = resourceManager.getFromKnowledgeBase("module_1/knowledge.csv");
        Backend backend = new Backend(path);

        List<JeopardyQuestion> questions = backend.getGameQuestions();

        assertNotNull("The list of questions should not be null.", questions);
        assertFalse("The list of questions should not be empty for a valid file.", questions.isEmpty());
        assertEquals("The list should contain exactly 10 questions.", 10, questions.size());
    }


    @Test
    public void testGracefulHandlingOfNonexistentFile() {
        URL path = resourceManager.getFromKnowledgeBase("nonsense");
        Backend backend = new Backend(path);

        List<JeopardyQuestion> questions = backend.getGameQuestions();

        assertNotNull("The list of questions should still be initialized (not null), even on error.", questions);
        assertTrue("The list of questions should be empty for a missing file.", questions.isEmpty());
    }
}
