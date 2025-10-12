package up.tac;

import java.util.List;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Backend {

    private List<JeopardyQuestion> gameQuestions;

    public Backend(URL csvFilePath) {
        ReadCsv reader = new ReadCsv(csvFilePath);
        this.gameQuestions = reader.loadQuestions();
    }

    public List<JeopardyQuestion> getGameQuestions() {
        return gameQuestions;
    }
    
    public JeopardyQuestion getGameQuestion(int index) {
        return gameQuestions.get(index);
    }

    public List<String> getQuestions() {
        return gameQuestions.stream()
                .map(JeopardyQuestion::getQuestion)
                .collect(Collectors.toList());
    }

    public List<String> getPossibleAnswers1() {
        return gameQuestions.stream()
                .map(q -> q.getOptions()[0])
                .collect(Collectors.toList());
    }

    public List<String> getPossibleAnswers2() {
        return gameQuestions.stream()
                .map(q -> q.getOptions()[1])
                .collect(Collectors.toList());
    }

    
    public List<String> getPossibleAnswers3() {
        return gameQuestions.stream()
                .map(q -> q.getOptions()[2])
                .collect(Collectors.toList());
    }

    public List<String> getPossibleAnswers4() {
        return gameQuestions.stream()
                .map(q -> q.getOptions()[3])
                .collect(Collectors.toList());
    }

    // returns list of correct answers
    public List<String> getCorrectAnswers() {
        return gameQuestions.stream()
                .map(JeopardyQuestion::getCorrectAnswer)
                .collect(Collectors.toList());
    }
}

