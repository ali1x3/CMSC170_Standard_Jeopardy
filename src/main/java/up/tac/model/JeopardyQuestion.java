package up.tac.model;

import java.util.Arrays;

/**
 * Represents a single question in the Jeopardy game.
 * This class is a data model and is not meant to be run directly.
 * Its purpose is simply to hold the data for one question.
 */
public class JeopardyQuestion {

    private String question;
    private String[] options;
    private String correctAnswer;

    public JeopardyQuestion(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // --- Getters ---

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Provides a string representation of the JeopardyQuestion object,
     * which is useful for debugging.
     */
    @Override
    public String toString() {
        return "JeopardyQuestion {" +
                "\n  question='" + question + '\'' +
                ",\n  options=" + Arrays.toString(options) +
                ",\n  correctAnswer='" + correctAnswer + '\'' +
                "\n}";
    }
}

