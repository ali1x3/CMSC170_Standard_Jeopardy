package up.tac;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame extends JFrame{
    final int frameWidth = 1100, frameHeight = 733;

    public MainFrame() {
        // Set Up Jframe
        setTitle("AI Jeopardy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setBounds(100, 50, frameWidth, frameHeight);
        setResizable(false);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        BufferedImage cursorImage = null;
        URL cursorURL = getClass().getClassLoader().getResource("files/cursor.png");
        if (cursorURL != null) {
            try {
                cursorImage = ImageIO.read(cursorURL);
            }
            catch (IOException e) {
                IO.println("Failed to import cursor image: " + e.getMessage());
            }
            if (cursorImage != null) {
                Cursor customCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "Custom Cursor");
                setCursor(customCursor);
            }
            else {
                System.err.println("Custom Cursor Failed to Load");
            }
        }
        else {
            System.err.println("Custom Cursor Failed to Load");
        }
        

        URL logoURL = getClass().getClassLoader().getResource("files/icon.png");
        if (logoURL != null) {
            setIconImage(toolkit.getImage(logoURL));
            System.out.println("Successfully loaded in program icon!");
        }
        else { 
            System.out.println("URL is null. Failed to load program icon."); 
        }
        
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = getJPanel(cardLayout);

        add(cardPanel);

        cardLayout.show(cardPanel, "Home Page");
        setVisible(true);
    }

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
        cardPanel.add(questionPanel, "Question Panel");
        return cardPanel;
    }

}
