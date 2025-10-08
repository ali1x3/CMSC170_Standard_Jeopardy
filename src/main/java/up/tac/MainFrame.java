package up.tac;
import up.tac.Resource.ResourceManager;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame extends JFrame{
    private final ResourceManager resourceManager;

    public MainFrame() {
        setup();
        resourceManager = new ResourceManager();
        setCursor(resourceManager.getCursor()); // load custom cursor
        setIconImage(resourceManager.getLogo()); // load the logo
        
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = getCardPanel(cardLayout);

        add(cardPanel);

        cardLayout.show(cardPanel, "Home Page");
        setVisible(true);
    }

    private static JPanel getCardPanel(CardLayout cardLayout) {
        JPanel cardPanel = new JPanel(cardLayout);

        MainPagePanel homePagePanel = new MainPagePanel(cardLayout, cardPanel);
        ContentPagePanel contentPagePanel = new ContentPagePanel(cardLayout, cardPanel);
        ContactPagePanel contactPagePanel = new ContactPagePanel(cardLayout, cardPanel);
        GamePanel gamePanel = new GamePanel(cardLayout, cardPanel);

        cardPanel.add(homePagePanel, "Home Page");
        cardPanel.add(contentPagePanel, "Content Page");
        cardPanel.add(contactPagePanel, "Contact Page");
        cardPanel.add(gamePanel, "Game Panel");
        return cardPanel;
    }

    private void setup() {
        // Set Up Jframe
        setTitle("AI Jeopardy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setBounds(100, 50, 900, 600);
        setResizable(false);
    }

}
