package up.tac.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    final int frameWidth = 1100, frameHeight = 733;

    public MainFrame(CardLayout cardLayout, JPanel cardPanel) {
        setup();
        add(cardPanel);
        cardLayout.show(cardPanel, "Home Page");
    }

    private void setup() {
        // Set Up Jframe
        setTitle("AI Jeopardy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setBounds((int) ((screenSize.getWidth() - frameWidth) / 2), (int) ((screenSize.getHeight() - frameHeight) / 2), frameWidth, frameHeight);
        setResizable(false);
    }
}
