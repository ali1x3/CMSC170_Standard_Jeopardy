package up.tac.view;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class ScoreTrackerBar extends JPanel {

    private double scorePercentage = 1.0; 
    private int fillHeight = 0;
    private Color fillColor = Color.YELLOW;

    public ScoreTrackerBar(Dimension preferredSize, Border border) {
        setPreferredSize(preferredSize);
        setOpaque(false); 
        setBorder(border); 
    }

    public int getScoreTrackerHeight() {
        return fillHeight;
    }

    public void setScorePercentage(double percentage) {
        this.scorePercentage = Math.max(0.0, Math.min(1.0, percentage));
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        fillHeight = (int) (height * scorePercentage);
        int startY = height - fillHeight; 
        Graphics2D g2d = (Graphics2D) g; 

        g2d.setColor(fillColor);
        g2d.fillRect(0, startY, width, fillHeight);
    }
}
