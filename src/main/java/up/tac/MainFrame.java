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
        JPanel cardPanel = getCardPanel(cardLayout, resourceManager);

        add(cardPanel);

        cardLayout.show(cardPanel, "Home Page");
        setVisible(true);
    }

    private static JPanel getCardPanel(CardLayout cardLayout, ResourceManager resourceManager) {
        JPanel cardPanel = new JPanel(cardLayout);

        MainPagePanel homePagePanel = new MainPagePanel(cardLayout, cardPanel, resourceManager);
        ContentPagePanel contentPagePanel = new ContentPagePanel(cardLayout, cardPanel, resourceManager);
        ContactPagePanel contactPagePanel = new ContactPagePanel(cardLayout, cardPanel, resourceManager);
        GamePanel gamePanel = new GamePanel(cardLayout, cardPanel, resourceManager);

    private JPanel getJPanel(CardLayout cardLayout) {
        JPanel cardPanel = new JPanel(cardLayout);

        MainPagePanel homePagePanel = new MainPagePanel(cardLayout, cardPanel, new Dimension(frameWidth, frameHeight));
        ContentPagePanel contentPagePanel = new ContentPagePanel(cardLayout, cardPanel, new Dimension(frameWidth, frameHeight));
        ContactPagePanel contactPagePanel = new ContactPagePanel(cardLayout, cardPanel, new Dimension(frameWidth, frameHeight));
        QuestionPanel questionPanel = new QuestionPanel(cardLayout, cardPanel, new Dimension(frameWidth, frameHeight));
        GamePanel gamePanel = new GamePanel(cardLayout, cardPanel, new Dimension(frameWidth, frameHeight), questionPanel);
        
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
