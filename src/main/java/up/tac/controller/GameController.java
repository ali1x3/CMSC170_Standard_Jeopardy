package up.tac.controller;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import up.tac.model.AudioPlayer;
import up.tac.model.ResourceManager;
import up.tac.view.ContactPagePanel;
import up.tac.view.ContentPagePanel;
import up.tac.view.GameDescriptionPanel;
import up.tac.view.GameOverPanel;
import up.tac.view.GamePanel;
import up.tac.view.LeaderBoardsPanel;
import up.tac.view.MainFrame;
import up.tac.view.MainPagePanel;
import up.tac.view.QuestionPanel;
import up.tac.view.SettingsPanel;

public class GameController {

    private MainFrame mainFrame;
    private ResourceManager resourceManager;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public GameController() {
        resourceManager = new ResourceManager();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        mainFrame = new MainFrame(cardLayout, cardPanel);
        mainFrame.setCursor(resourceManager.getCursor());
        mainFrame.setIconImage(resourceManager.getLogo());

        addPanels();

        mainFrame.setVisible(true);
        AudioPlayer.play("/files/AI_voice_welcome.wav", false);
        AudioPlayer.playBGM("/files/BGM_jeopardy.wav");
    }

    private void addPanels() {
        int frameWidth = 1100;
        int frameHeight = 733;

        ContentPagePanel contentPagePanel = new ContentPagePanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight));
        ContactPagePanel contactPagePanel = new ContactPagePanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight));
        QuestionPanel questionPanel = new QuestionPanel(cardLayout, cardPanel, new Dimension(frameWidth, frameHeight), resourceManager);
        GameDescriptionPanel descriptionPanel = new GameDescriptionPanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight));
        LeaderBoardsPanel leaderBoardsPanel = new LeaderBoardsPanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight));
        GameOverPanel gameOverPanel = new GameOverPanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight), leaderBoardsPanel);
        GamePanel gamePanel = new GamePanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight), questionPanel, gameOverPanel);
        MainPagePanel homePagePanel = new MainPagePanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight), gamePanel);
        SettingsPanel settingsPanel = new SettingsPanel(cardLayout, cardPanel, resourceManager, new Dimension(frameWidth, frameHeight));

        gamePanel.setName("gamePanel");
        cardPanel.add(homePagePanel, "Home Page");
        cardPanel.add(contentPagePanel, "Content Page");
        cardPanel.add(contactPagePanel, "Contact Page");
        cardPanel.add(gamePanel, "Game Panel");
        cardPanel.add(questionPanel, "Question Panel");
        cardPanel.add(descriptionPanel, "Game Description Page");
        cardPanel.add(gameOverPanel, "Game Over Page");
        cardPanel.add(leaderBoardsPanel, "Leaderboards Page");
        cardPanel.add(settingsPanel, "Settings Page");
    }
}
