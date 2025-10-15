package up.tac;

import java.awt.Dimension;
import java.awt.Image;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import up.tac.Resource.ResourceManager;

/**
 * QuestionButton
 */
public class QuestionButton extends JButton {
    private static int totalButtons = 0;
    private static int disabledButtons = 0;
    private int questionValue, row, column;
    private JeopardyQuestion question;
    private Backend backend;
    private String module;
    private String classification;
	private ResourceManager resourceManager;

    public QuestionButton (int questionValue, int row, int column, Dimension frameDimension, ResourceManager resourceManager) {
        this.questionValue = questionValue;
        this.row = row;
        this.column = column;
        this.resourceManager = resourceManager;

        ImageIcon normalIcon = new ImageIcon(getClass().getResource("/files/gameButton_bg.png"));
        Image normalIconResized = normalIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/14.66), (int) (frameDimension.getHeight()/14.66), Image.SCALE_DEFAULT);
        ImageIcon rolloverIcon = new ImageIcon(getClass().getResource("/files/gameButtonClicked_bg.png"));
        Image rolloverIconResized = rolloverIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/14.66), (int) (frameDimension.getHeight()/14.66), Image.SCALE_DEFAULT);

        this.setIcon(new ImageIcon(normalIconResized));
        this.setPressedIcon(new ImageIcon(rolloverIconResized));
        loadQuestion();
        totalButtons += 1;
    }
    public void loadQuestion() {
        // TODO: load a jeopardy question and its answers
        // in the corresponding module and blooms classification
        module = "module_" + column;
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
        backend = new Backend(something);

        Random rand = new Random();
        int random = rand.nextInt(10);
        this.question = backend.getGameQuestion(random);
    }

    public void disableButton(Icon icon) {
        this.setEnabled(false);
        this.setDisabledIcon(icon);
        this.setText("");
        disabledButtons += 1;
        System.out.println("deac: " + disabledButtons);
        System.out.println("total: " + totalButtons);
    }

    public void checkGameOver() {
        if (disabledButtons == totalButtons) {
            System.out.println("GAME OVER");
        }
    }

    public JeopardyQuestion getJeopardyQuestion() {
        return question;
    }

    public int getQuestionValue() {
		return questionValue;
	}

	public String getModule() {
		return module;
	}

	public String getClassifcation() {
		return classification;
	}

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
}
