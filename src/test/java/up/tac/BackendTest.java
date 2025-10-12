package up.tac;

import org.junit.Test;

import up.tac.Resource.ResourceManager;

import static org.junit.Assert.*; // Imports static methods like assertEquals, assertTrue, etc.

import java.net.URL;
import java.util.List;
import java.util.Random;

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
    
    @Test
    public void checkIfAllQuestionsHaveAnswers() {
        for(int i = 0; i < 48; i++) {
            int row = (i / 8) + 1;
            int column = (i % 8) + 1;
            boolean answerFound = loadQuestions(row, column);
            assertTrue("Not all questions have answers", answerFound);
        }

    }

    private boolean loadQuestions(int row, int column) { 
        String module = "module_" + column;
        String classification = "";
        switch (row) {
            case 1:
                classification = "/knowledge.csv";
                break;
            case 2:
                classification = "/comprehension.csv";
                break;
            case 3:
                classification = "/application.csv";
                break;
            case 4:
                classification = "/analysis.csv";
                break;
            case 5:
                classification = "/synthesis.csv";
                break;
            case 6:
                classification = "/evaluation.csv";
                break;
        }

        String file_path = module + classification;
        URL something = resourceManager.getFromKnowledgeBase(file_path);
        Backend backend = new Backend(something);

        try {

            for (int i = 0; i < 10; i++) {
                String question = backend.getQuestions().get(i);
                String correct = backend.getCorrectAnswers().get(i);
                String choice1 = backend.getPossibleAnswers1().get(i);
                String choice2 = backend.getPossibleAnswers2().get(i);
                String choice3 = backend.getPossibleAnswers3().get(i);
                String choice4 = backend.getPossibleAnswers4().get(i);

                boolean found =
                correct.equalsIgnoreCase(choice1) ||
                correct.equalsIgnoreCase(choice2) ||
                correct.equalsIgnoreCase(choice3) ||
                correct.equalsIgnoreCase(choice4);

                if (!found) {
                    System.err.println("❌ Correct Answer not found for question " + i);
                    System.err.println("Quesiton: " + question);
                    System.err.println("Correct: " + correct);
                    System.err.println("Choices1: " + choice1);
                    System.err.println("Choices2: " + choice2);
                    System.err.println("Choices3: " + choice3);
                    System.err.println("Choices4: " + choice4);
                    System.err.println("File: " + file_path);
                    return false;
                }
            }
        }
        catch (IndexOutOfBoundsException e) {
            int len = backend.getQuestions().size();
            int correct = backend.getCorrectAnswers().size();
            int choice1 = backend.getPossibleAnswers1().size();
            int choice2 = backend.getPossibleAnswers2().size();
            int choice3 = backend.getPossibleAnswers3().size();
            int choice4 = backend.getPossibleAnswers4().size();
            System.err.println("num of questions: " + len);
            System.err.println("num of correct: " + correct);
            System.err.println("num of choice1: " + choice1);
            System.err.println("num of choice2: " + choice2);
            System.err.println("num of choice3: " + choice3);
            System.err.println("num of choice4: " + choice4);

            System.err.println("File: " + file_path);
            System.err.println(e);

            for (String question : backend.getQuestions()) {
                System.err.println("Question: " + question);
            }
            return false;
        }
        catch (Exception e) {
            System.err.println("File: " + file_path);
            System.err.println(e);
            return false;
        }

        return true;
    }
}
