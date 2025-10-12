package up.tac;

import org.junit.Test;
import static org.junit.Assert.*; // Imports static methods like assertEquals, assertTrue, etc.

import java.util.List;

public class BackendTest {

    @Test
    public void testSuccessfulLoadOfApplicationCsv() {
        Backend backend = new Backend("src\\main\\resources\\knowledge_base\\module_1\\analysis.csv");

        List<JeopardyQuestion> questions = backend.getGameQuestions();

        assertNotNull("The list of questions should not be null.", questions);
        assertFalse("The list of questions should not be empty for a valid file.", questions.isEmpty());
        assertEquals("The list should contain exactly 10 questions.", 10, questions.size());
    }

    @Test
    public void testSuccessfulLoadOfAnalysisCsv() {
        Backend backend = new Backend("src\\main\\resources\\knowledge_base\\module_1\\analysis.csv");

        List<JeopardyQuestion> questions = backend.getGameQuestions();

        assertNotNull("The list of questions should not be null.", questions);
        assertFalse("The list should not be empty.", questions.isEmpty());
        assertEquals("The list should contain exactly 10 questions.", 10, questions.size());
    }

    @Test
    public void testGracefulHandlingOfNonexistentFile() {
        Backend backend = new Backend("this_file_does_not_exist.csv");

        List<JeopardyQuestion> questions = backend.getGameQuestions();

        assertNotNull("The list of questions should still be initialized (not null), even on error.", questions);
        assertTrue("The list of questions should be empty for a missing file.", questions.isEmpty());
    }
}
