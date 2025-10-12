package up.tac;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * QuestionButton
 */
public class QuestionButton extends JButton {
    private int questionValue, row, column;

    public QuestionButton (int questionValue, int row, int column, Dimension frameDimension) {
        this.questionValue = questionValue;
        this.row = row;
        this.column = column;
        ImageIcon normalIcon = new ImageIcon(getClass().getResource("/files/gameButton_bg.png"));
        Image normalIconResized = normalIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/14.66), (int) (frameDimension.getHeight()/14.66), Image.SCALE_DEFAULT);
        ImageIcon rolloverIcon = new ImageIcon(getClass().getResource("/files/gameButtonClicked_bg.png"));
        Image rolloverIconResized = rolloverIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/14.66), (int) (frameDimension.getHeight()/14.66), Image.SCALE_DEFAULT);
        this.setIcon(new ImageIcon(normalIconResized));
        this.setPressedIcon(new ImageIcon(rolloverIconResized));
        loadQuestion();
    }
    private void loadQuestion() {
        // TODO: load a jeopardy question and its answers w
        // in the corresponding module and blooms classification
    }

    public Object getQuestions() {
        return true;
    }

    public int getQuestionValue() {
		return questionValue;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
