package up.tac;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadCsv {
    private String filePath;
    private List<JeopardyQuestion> questions;

    public ReadCsv(String filePath) {
        this.filePath = filePath;
        this.questions = new ArrayList<>();
    }

    public List<JeopardyQuestion> loadQuestions() {
        String line = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
               
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (values.length == 6) {
               
                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].trim().replaceAll("^\"|\"$", "");
                    }

                    String questionText = values[0];
                    String[] options = Arrays.copyOfRange(values, 1, 5); // Columns 2-5
                    String correctAnswer = values[5];

                 
                    this.questions.add(new JeopardyQuestion(questionText, options, correctAnswer));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return this.questions;
    }

    
    public List<JeopardyQuestion> getQuestions() {
        return questions;
    }
}