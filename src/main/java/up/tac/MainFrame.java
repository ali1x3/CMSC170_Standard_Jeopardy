package up.tac;
import up.tac.Resource.ResourceManager;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame extends JFrame{
    private final ResourceManager resourceManager;
    final int frameWidth = 1100, frameHeight = 733;

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
        AudioPlayer.play("/files/AI_voice_welcome.wav");
        AudioPlayer.playBGM("/files/BGM_jeopardy.wav");
    }

    private JPanel getCardPanel(CardLayout cardLayout) {
        JPanel cardPanel = new JPanel(cardLayout);

        MainPagePanel homePagePanel = new MainPagePanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight));
        ContentPagePanel contentPagePanel = new ContentPagePanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight));
        ContactPagePanel contactPagePanel = new ContactPagePanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight));
        QuestionPanel questionPanel = new QuestionPanel(cardLayout, cardPanel, new Dimension(frameWidth, frameHeight), resourceManager);
        GamePanel gamePanel = new GamePanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth,
            frameHeight), questionPanel);   
        GameDescriptionPanel descriptionPanel = new GameDescriptionPanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight));

        gamePanel.setName("gamePanel");
        cardPanel.add(homePagePanel, "Home Page");
        cardPanel.add(contentPagePanel, "Content Page");
        cardPanel.add(contactPagePanel, "Contact Page");
        cardPanel.add(gamePanel, "Game Panel");
        cardPanel.add(questionPanel, "Question Panel");
        cardPanel.add(descriptionPanel, "Game Description Page");
        return cardPanel;
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
