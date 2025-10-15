package up.tac;

import up.tac.Resource.ResourceManager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class LeaderBoardsPanel extends JPanel implements MouseListener{
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Image bg_image;
    private JPanel upperPanel, lowerPanel;
    private JLabel title, homePageButton, contentPageButton, contactPageButton, exitButtonLabel, minimizeButtonLabel, gamedescriptionButton, leaderboardsButton;
    private Font customFont = new Font("Arial", Font.PLAIN, 21);
    private Font boldCustomFont = new Font("Arial", Font.BOLD, 21);
    private Font titleFont = new Font("Arial", Font.BOLD, 56);
    private ImageIcon exitButton, exitButtonClicked, minimizeButton, minimizeButtonClicked;
    private java.util.ArrayList<Score> scores;
    private Dimension frameDimension;
    private JPanel scoresContainerPanel;
    private JScrollPane scoresScroll;
    private JPanel headerPanel;

    private ResourceManager resourceManager;

    public LeaderBoardsPanel(CardLayout cardLayout, JPanel cardPanel, ResourceManager resourceManager,
                            Dimension frameDimension){
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.resourceManager = resourceManager;
        this.frameDimension = frameDimension;

        bg_image = resourceManager.getImageIcon("Leaderboards Panel BG").getImage();

        this.setLayout(new BorderLayout());

        customFont = resourceManager.getCousineRegular();
        customFont = customFont.deriveFont(Font.PLAIN, (int) frameDimension.getHeight()/25);
        boldCustomFont = resourceManager.getCousineBold();
        boldCustomFont = boldCustomFont.deriveFont(Font.BOLD, (int) frameDimension.getHeight()/25);
        titleFont = resourceManager.getAnonymousProBold();
        titleFont = titleFont.deriveFont(Font.BOLD, (int) frameDimension.getHeight()/10);

        setUpperPanel();
        setLowerPanel();
        reinit();

        // add the upper and lower panels
        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.CENTER);

    }

    private void setUpperPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        upperPanel = new JPanel();
        upperPanel.setOpaque(false);
        upperPanel.setLayout(new GridBagLayout());

        title = new JLabel("Algorithmic Avengers Inc.");
        title.setForeground(java.awt.Color.lightGray);
        title.setFont(boldCustomFont);
        title.addMouseListener(this);

        homePageButton = new JLabel("Home");
        homePageButton.setForeground(java.awt.Color.black);
        homePageButton.setFont(customFont);
        homePageButton.addMouseListener(this);

        contentPageButton = new JLabel("Rules");
        contentPageButton.setForeground(java.awt.Color.black);
        contentPageButton.setFont(customFont);
        contentPageButton.addMouseListener(this);

        contactPageButton = new JLabel("About");
        contactPageButton.setForeground(java.awt.Color.black);
        contactPageButton.setFont(customFont);
        contactPageButton.addMouseListener(this);

        gamedescriptionButton = new JLabel("Description");
        gamedescriptionButton.setForeground(java.awt.Color.black);
        gamedescriptionButton.setFont(customFont);
        gamedescriptionButton.addMouseListener(this);

        leaderboardsButton = new JLabel("Leaderboards");
        leaderboardsButton.setForeground(java.awt.Color.black);
        leaderboardsButton.setFont(boldCustomFont);
        leaderboardsButton.addMouseListener(this);

        exitButton = resourceManager.getImageIcon("Exit Button");
        Image exitButtonResized = exitButton.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        exitButton = new ImageIcon(exitButtonResized);

        minimizeButton = resourceManager.getImageIcon("Minimize Button");
        Image minimizeButtonResized = minimizeButton.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        minimizeButton = new ImageIcon(minimizeButtonResized);

        exitButtonClicked = resourceManager.getImageIcon("Exit Button Clicked");
        Image exitButtonClickedResized = exitButtonClicked.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        exitButtonClicked = new ImageIcon(exitButtonClickedResized);

        minimizeButtonClicked = resourceManager.getImageIcon("Minimize Button Clicked");
        Image minimizeButtonClickedResized = minimizeButtonClicked.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        minimizeButtonClicked = new ImageIcon(minimizeButtonClickedResized);

        exitButtonLabel = new JLabel(exitButton);
        exitButtonLabel.addMouseListener(this);

        minimizeButtonLabel = new JLabel(minimizeButton);
        minimizeButtonLabel.addMouseListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/16, (int) (frameDimension.getWidth()/27.5), (int) frameDimension.getHeight()/49, (int) (frameDimension.getWidth()/2.4));

        upperPanel.add(title, gbc);

        gbc.gridx = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/18, 0, (int) (frameDimension.getHeight()/40.7), (int) (frameDimension.getWidth()/137.5));

        upperPanel.add(minimizeButtonLabel, gbc);

        gbc.gridx = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/18, 0, (int) (frameDimension.getHeight()/40.7), (int) (frameDimension.getWidth()/27.5));
        upperPanel.add(exitButtonLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        gbc.insets = new Insets((int) (frameDimension.getHeight()/91.625), (int) (frameDimension.getWidth()/27.5), (int) (frameDimension.getHeight()/13.4), (int) (frameDimension.getWidth()/110));
        upperPanel.add(homePageButton, gbc);

        gbc.insets = new Insets((int) (frameDimension.getHeight()/91.625), (int) (frameDimension.getWidth()/110), (int) (frameDimension.getHeight()/13.4), (int) (frameDimension.getWidth()/110));
        gbc.gridx = 1;
        upperPanel.add(contentPageButton, gbc);

        gbc.gridx = 2;
        upperPanel.add(contactPageButton, gbc);

        gbc.insets = new Insets((int) (frameDimension.getHeight()/91.625), (int) (frameDimension.getWidth()/110), (int) (frameDimension.getHeight()/13.4), (int) (frameDimension.getWidth()/110));
        gbc.gridx = 3;
        upperPanel.add(gamedescriptionButton, gbc);

        gbc.insets = new Insets((int) (frameDimension.getHeight()/91.625), (int) (frameDimension.getWidth()/110), (int) (frameDimension.getHeight()/13.4), (int) (frameDimension.getWidth()/110));
        gbc.gridx = 4;
        upperPanel.add(leaderboardsButton, gbc);


        gbc.gridx = 5;
        gbc.weightx = 1;
        upperPanel.add(new JLabel(), gbc);
    }

    private void setLowerPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        lowerPanel = new JPanel();
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("LEADERBOARDS");
        // Move the label a little bit more up by adjusting the top inset
        gbc.insets = new Insets(0, 10, 3, 10); // negative top inset moves it up
        title.setForeground(new Color(0x0057cc));
        title.setFont(titleFont);
    
        title.setBounds((int) (frameDimension.getWidth()/ 17), (int) (frameDimension.getHeight()/45), (int) (frameDimension.getWidth()/1.5), (int) (frameDimension.getHeight()/10));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 10, 10, 10);
        lowerPanel.add(title, gbc);

    // Panel that will contain the score rows in a grid
    scoresContainerPanel = new JPanel(new GridBagLayout());
    scoresContainerPanel.setOpaque(false);


    // header panel (static height) placed directly under the title
    headerPanel = new JPanel(new GridBagLayout());
    headerPanel.setOpaque(false);
    GridBagConstraints hgbc = new GridBagConstraints();
    hgbc.gridx = 0;
    hgbc.gridy = 0;
    hgbc.insets = new Insets(6, 60, 6, 60);
    hgbc.anchor = GridBagConstraints.WEST;
    JLabel nameHeader = new JLabel("Name");
    nameHeader.setFont(boldCustomFont);
    // give headers a fixed preferred height so they don't resize with rows
    nameHeader.setPreferredSize(new Dimension((int)(frameDimension.getWidth()/3.5), (int)(frameDimension.getHeight()/20)));
    headerPanel.add(nameHeader, hgbc);

    hgbc.gridx = 1;
    JLabel dateHeader = new JLabel("Date");
    dateHeader.setFont(boldCustomFont);
    dateHeader.setPreferredSize(new Dimension((int)(frameDimension.getWidth()/3.5), (int)(frameDimension.getHeight()/20)));
    headerPanel.add(dateHeader, hgbc);

    hgbc.gridx = 2;
    JLabel scoreHeader = new JLabel("Score");
    scoreHeader.setFont(boldCustomFont);
    scoreHeader.setPreferredSize(new Dimension((int)(frameDimension.getWidth()/6.5), (int)(frameDimension.getHeight()/20)));
    headerPanel.add(scoreHeader, hgbc);

        // ensure scores list exists
        if (scores == null) scores = new java.util.ArrayList<>();

        // populate rows from scores
        populateScoresPanel(scoresContainerPanel, scores);

        // put the scoresContainer inside a scroll pane
    scoresScroll = new JScrollPane(scoresContainerPanel);
    scoresScroll.setOpaque(false);
    scoresScroll.getViewport().setOpaque(false);
    scoresScroll.setBorder(null);
    Dimension scrollSize = new Dimension((int)(frameDimension.getWidth()/1.2), (int)(frameDimension.getHeight()/2.5));
    scoresScroll.setPreferredSize(scrollSize);

    gbc.gridx = 0;
        // add header panel directly under title with a small gap, then scroll below it
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 10, 4, 10);
        lowerPanel.add(headerPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,10,10,10);
        lowerPanel.add(scoresScroll, gbc);
    }

    public void reinit() {
        sortScoresByScore();
        if (scores.size() != 0) {
            populateScoresPanel(scoresContainerPanel, scores);
            scoresContainerPanel.setVisible(true);
            // Remove all previous score rows except the header (row 0)
            scoresContainerPanel.removeAll();

            // Re-populate with current scores
            populateScoresPanel(scoresContainerPanel, scores);

            scoresContainerPanel.revalidate();
            scoresContainerPanel.repaint();
            revalidate();
            repaint();
        } else {
            scoresContainerPanel.setVisible(false);
        }
        
    }

    private void populateScoresPanel(JPanel container, java.util.List<Score> scoreList) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 60, 6, 60);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 1; // header is row 0
        for (Score s : scoreList) {
            gbc.gridy = row;

            gbc.gridx = 0;
            JLabel nameLabel = new JLabel(s.getName() == null ? "-" : s.getName());
            nameLabel.setFont(customFont);
            container.add(nameLabel, gbc);

            gbc.gridx = 1;
            JLabel dateLabel = new JLabel(s.getDate() == null ? "-" : s.getDate());
            dateLabel.setFont(customFont);
            container.add(dateLabel, gbc);

            gbc.gridx = 2;
            JLabel scoreLabel = new JLabel(Integer.toString(s.getScore()));
            scoreLabel.setFont(customFont);
            container.add(scoreLabel, gbc);

            row++;
        }
    }
    public void addScore(Score score) {
        scores.add(score);
    }
    /**
     * Sort the current scores list by score (descending) and refresh the UI.
     */
    public void sortScoresByScore() {
        if (scores == null || scores.isEmpty()) return;
        scores.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // don't proceed if bg_image is null
        if (bg_image == null) {
            System.out.println("Background Image failed to Load");
            return;
        }


        // proceed if bg_image is not null
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(bg_image, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == minimizeButtonLabel) {
            System.out.println("Minimize Button Pressed");
            java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame) {
            ((JFrame) window).setState(JFrame.ICONIFIED);
            }
        }
        else if (e.getSource() == exitButtonLabel) {
            System.out.println("Exit Button Pressed");
            System.exit(0);
        }
        else if (e.getSource() == homePageButton) {
            AudioPlayer.stop();
            cardLayout.show(cardPanel, "Home Page");
            AudioPlayer.play("/files/AI_voice_home.wav", true);
        }
        else if (e.getSource() == contentPageButton) {
            AudioPlayer.stop();
            cardLayout.show(cardPanel, "Content Page");
            AudioPlayer.play("/files/AI_voice_content.wav", true);
        }else if (e.getSource() == contactPageButton) {
            AudioPlayer.stop();
            cardLayout.show(cardPanel, "Contact Page");
            AudioPlayer.play("/files/AI_voice_contact.wav", true);
        }else if (e.getSource() == gamedescriptionButton) {
            AudioPlayer.stop();
            cardLayout.show(cardPanel, "Game Description Page");
            AudioPlayer.play("/files/AI_voice_description.wav", true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == contentPageButton) {
            contentPageButton.setFont(boldCustomFont);
        }
        else if (e.getSource() == contactPageButton) {
            contactPageButton.setFont(boldCustomFont);
        }
        else if (e.getSource() == homePageButton) {
            homePageButton.setFont(boldCustomFont);

        }else if(e.getSource() == gamedescriptionButton){
            gamedescriptionButton.setFont(boldCustomFont);

        }else if(e.getSource() == leaderboardsButton){
            leaderboardsButton.setFont(customFont);

        }
        else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButtonClicked);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButtonClicked);
        }

        if (!(e.getSource() == exitButtonLabel || e.getSource() == minimizeButtonLabel || e.getSource() == title || e.getSource() == contentPageButton || e.getSource() == contactPageButton || e.getSource() == homePageButton || e.getSource() == gamedescriptionButton || e.getSource() == leaderboardsButton)) {
            AudioPlayer.play("/files/SFX_button_1.wav", false);
            System.out.println("sfx1");
        } 
        else if (!(e.getSource() == title || e.getSource() == leaderboardsButton) ) {
            AudioPlayer.play("/files/SFX_button_2.wav", false);
            System.out.println("sfx2");
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == contentPageButton) {
            contentPageButton.setFont(customFont);
        }
        else if (e.getSource() == contactPageButton) {
            contactPageButton.setFont(customFont);
        }
        else if (e.getSource() == homePageButton){
            homePageButton.setFont(customFont);

        }else if(e.getSource() == leaderboardsButton){
            leaderboardsButton.setFont(boldCustomFont);

        }else if(e.getSource() == gamedescriptionButton){
            gamedescriptionButton.setFont(customFont);
        }
        else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButton);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButton);       
        }
    }
}

