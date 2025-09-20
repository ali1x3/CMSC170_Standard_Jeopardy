package up.tac;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame extends JFrame{
    public MainFrame() {
        // Set Up Jframe
        setTitle("AI Jeopardy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setBounds(100, 50, 900, 600);
        setResizable(false);

        BufferedImage cursorImage = null;
        URL cursorURL = getClass().getClassLoader().getResource("files/cursor.png");
        if (cursorURL != null) {
            try {
                cursorImage = ImageIO.read(cursorURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (cursorImage != null) {
                Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0,0), "Custom Cursor");
                setCursor(customCursor);
            } else {
                System.out.println("Custom Cursor Failed to Load");
            }
        } else {
            System.out.println("Custom Cursor Failed to Load");
        }
        

        URL logoURL = getClass().getClassLoader().getResource("files/icon.png");
        if(logoURL != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(logoURL)); 
            System.out.println("Successfully loaded in program icon!");
        } else { 
            System.out.println("URL is null. Failed to load program icon."); 
        }
        
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        MainPagePanel homePagePanel = new MainPagePanel(cardLayout, cardPanel);
        ContentPagePanel contentPagePanel = new ContentPagePanel(cardLayout, cardPanel);
        ContactPagePanel contactPagePanel = new ContactPagePanel(cardLayout, cardPanel);
        GamePanel gamePanel = new GamePanel(cardLayout, cardPanel);

        cardPanel.add(homePagePanel, "Home Page");
        cardPanel.add(contentPagePanel, "Content Page");
        cardPanel.add(contactPagePanel, "Contact Page");
        cardPanel.add(gamePanel, "Game Panel");
        
        add(cardPanel);

        cardLayout.show(cardPanel, "Home Page");

        
        setVisible(true);

    }

}
