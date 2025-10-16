package up.tac;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import up.tac.Resource.ResourceManager;


public class LeaderBoardsPanel extends JPanel implements MouseListener{
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Image bg_image;
    private JPanel upperPanel, lowerPanel;
    private JLabel title, homePageButton, contentPageButton, contactPageButton, exitButtonLabel, minimizeButtonLabel, gamedescriptionButton, leaderboardsButton, settingsPanelLabel;
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

        settingsPanelLabel = new JLabel("Sound"); 
        settingsPanelLabel.setForeground(java.awt.Color.black); 
        settingsPanelLabel.setFont(customFont);
        settingsPanelLabel.addMouseListener(this);

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
        gbc.gridwidth = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/16, (int) (frameDimension.getWidth()/27.5), (int) frameDimension.getHeight()/49, (int) (frameDimension.getWidth()/2.4));

        upperPanel.add(title, gbc);

        gbc.gridx = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/18, 0, (int) (frameDimension.getHeight()/40.7), (int) (frameDimension.getWidth()/137.5));

        upperPanel.add(minimizeButtonLabel, gbc);

        gbc.gridx = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/18, 0, (int) (frameDimension.getHeight()/40.7), (int) (frameDimension.getWidth()/27.5));
        upperPanel.add(exitButtonLabel, gbc);

         // --- ROW 1: Navigation Buttons ---
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        
        // Home Button (gridx = 0)
        gbc.gridx = 0;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/91.625), (int) (frameDimension.getWidth()/27.5), (int) (frameDimension.getHeight()/13.4), (int) (frameDimension.getWidth()/110));
        upperPanel.add(homePageButton, gbc);

        // Rules Button (gridx = 1)
        gbc.gridx = 1;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/91.625), (int) (frameDimension.getWidth()/110), (int) (frameDimension.getHeight()/13.4), (int) (frameDimension.getWidth()/110));
        upperPanel.add(contentPageButton, gbc); // Rules

        // About Button (gridx = 2)
        gbc.gridx = 2;
        upperPanel.add(contactPageButton, gbc); // About

        // Description Button (gridx = 3)
        gbc.gridx = 3;
        upperPanel.add(gamedescriptionButton, gbc);

        // Leaderboards Button (gridx = 4)
        gbc.gridx = 4;
        upperPanel.add(leaderboardsButton, gbc);

        // *** NEW: Settings Label (Sound) ***
        gbc.gridx = 5;
        // Use the same insets as the previous buttons for consistent spacing
        gbc.insets = new Insets((int) (frameDimension.getHeight()/91.625), (int) (frameDimension.getWidth()/110), (int) (frameDimension.getHeight()/13.4), (int) (frameDimension.getWidth()/110));
        upperPanel.add(settingsPanelLabel, gbc);

        // *** Horizontal Filler ***
        // This filler is crucial to push the left-anchored navigation links to the left
        // and absorb the remaining horizontal space. It now moves to gridx=6.
        gbc.gridx = 6;
        gbc.weightx = 1; // Takes up all remaining horizontal space
        gbc.insets = new Insets(0, 0, 0, 0); // No insets needed for the filler
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

    // ensure scores list exists
    if (scores == null) {
        System.out.println("SCORES IS EMPTY. LOADING.");
        scores = loadScores(); //new java.util.ArrayList<>();
    }
    
    // populate rows from scores
    populateScoresPanel(scoresContainerPanel, scores);

    // put the scoresContainer inside a scroll pane
    scoresScroll = new JScrollPane(scoresContainerPanel);
    scoresScroll.setOpaque(false);
    scoresScroll.getViewport().setOpaque(false);
    scoresScroll.setBorder(null);
    Dimension scrollSize = new Dimension((int)(frameDimension.getWidth()/1.2), (int)(frameDimension.getHeight()/3.5));
    scoresScroll.setPreferredSize(scrollSize);

         gbc.gridx = 0;
        gbc.gridy = 1; // Now occupying gridy 1
        gbc.fill = GridBagConstraints.BOTH; // Expand both ways
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // IMPORTANT: Take up all remaining vertical space!
        gbc.insets = new Insets(10, 10, (int)(frameDimension.getHeight()/12.0), 10);
        lowerPanel.add(scoresScroll, gbc);

        // --- 4. ADD VERTICAL FILLER at the bottom ---
        // You now need a filler to absorb the extra vertical space below the scroll pane.
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0; // This filler takes all the rest of the vertical space
        gbc.fill = GridBagConstraints.VERTICAL;
        JPanel finalFiller = new JPanel();
        finalFiller.setOpaque(false);
        lowerPanel.add(finalFiller, gbc);
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
        container.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.fill = GridBagConstraints.NONE;

        // --- Define Column Dimensions ---
        // These dimensions enforce the column widths to prevent overlap.
        Dimension nameDim = new Dimension((int)(frameDimension.getWidth()/3.5), (int)(frameDimension.getHeight()/20));
        Dimension dateDim = new Dimension((int)(frameDimension.getWidth()/3.5), (int)(frameDimension.getHeight()/20));
        Dimension scoreDim = new Dimension((int)(frameDimension.getWidth()/6.5), (int)(frameDimension.getHeight()/20));

        // --- 1. ADD HORIZONTAL FILLER 1 (LEFT SIDE) ---
        // This spacer takes up extra space on the left to push the content block right.
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; 
        gbc.gridheight = GridBagConstraints.REMAINDER; // Span all rows to the bottom
        
        JPanel leftSpacer = new JPanel();
        leftSpacer.setOpaque(false);
        container.add(leftSpacer, gbc);
        
        // Reset constraints for content area
        gbc.weightx = 0;
        gbc.gridheight = 1; // Reset height to 1 for standard rows
        int content_start_x = 1;
        int row = 0; 
        
        // --- 2. ADD HEADERS (Row 0) ---
        
        // Header 1: Name
        gbc.gridx = content_start_x; 
        gbc.gridy = row;
        gbc.insets = new Insets(6, 0, 6, 6); 
        JLabel nameHeader = new JLabel("Name");
        nameHeader.setFont(boldCustomFont);
        nameHeader.setHorizontalAlignment(JLabel.CENTER);
        nameHeader.setPreferredSize(nameDim); 
        container.add(nameHeader, gbc);

        // Header 2: Date
        gbc.gridx = content_start_x + 1; 
        gbc.insets = new Insets(6, 6, 6, 6);
        JLabel dateHeader = new JLabel("Date");
        dateHeader.setFont(boldCustomFont);
        dateHeader.setHorizontalAlignment(JLabel.CENTER);
        dateHeader.setPreferredSize(dateDim);
        container.add(dateHeader, gbc);

        // Header 3: Score
        gbc.gridx = content_start_x + 2; 
        int last_content_x = gbc.gridx; 
        gbc.insets = new Insets(6, 6, 6, 0);
        JLabel scoreHeader = new JLabel("Score");
        scoreHeader.setFont(boldCustomFont);
        scoreHeader.setHorizontalAlignment(JLabel.CENTER);
        scoreHeader.setPreferredSize(scoreDim);
        container.add(scoreHeader, gbc);

        row++; // Move to the next row for the scores

        // --- 3. ADD SCORE ROWS (Starting Row 1) ---
        gbc.insets = new Insets(6, 0, 6, 0);

        for (Score s : scoreList) {
            System.out.println("ENTERED FOR LOOP");
            System.out.println("name = " + s.getName());
            System.out.println("score = " + s.getScore());
            System.out.println("date = " + s.getDate());
            
            gbc.gridy = row;

            gbc.gridx = content_start_x;
            JLabel nameLabel = new JLabel(s.getName() == null ? "-" : s.getName());
            nameLabel.setFont(customFont);
            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            nameLabel.setPreferredSize(nameDim); 
            container.add(nameLabel, gbc);

            gbc.gridx = content_start_x + 1;
            JLabel dateLabel = new JLabel(s.getDate() == null ? "-" : s.getDate());
            dateLabel.setFont(customFont);
            dateLabel.setHorizontalAlignment(JLabel.CENTER);
            dateLabel.setPreferredSize(dateDim);
            container.add(dateLabel, gbc);

            gbc.gridx = content_start_x + 2;
            JLabel scoreLabel = new JLabel(Integer.toString(s.getScore()));
            scoreLabel.setFont(customFont);
            scoreLabel.setHorizontalAlignment(JLabel.CENTER);
            scoreLabel.setPreferredSize(scoreDim);
            container.add(scoreLabel, gbc);

            row++;
        }

        // --- 4. ADD VERTICAL FILLER (The Expansion/Collapse Fix) ---
        // This MUST be added after all rows to push them up, and before the right spacer
        // to correctly define the boundary of the grid.
        gbc.gridx = content_start_x; 
        gbc.gridy = row; // Starts immediately after the last score row
        gbc.weighty = 1.0; // IMPORTANT: Takes up all vertical remainder
        gbc.weightx = 0;
        gbc.gridwidth = 3; // Spans the three content columns
        gbc.gridheight = 1; 
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.NORTH; 
        
        JPanel verticalFiller = new JPanel();
        verticalFiller.setOpaque(false);
        container.add(verticalFiller, gbc);

        // --- 5. ADD HORIZONTAL FILLER 2 (RIGHT SIDE) ---
        // This spacer must be added last to fill the right side and balance the left spacer.
        gbc.gridx = last_content_x + 1; 
        gbc.gridy = 0; // Starts at row 0
        gbc.weightx = 1.0; 
        gbc.weighty = 0; 
        gbc.gridwidth = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER; // Spans all rows
        gbc.anchor = GridBagConstraints.WEST;
        
        JPanel rightSpacer = new JPanel();
        rightSpacer.setOpaque(false);
        container.add(rightSpacer, gbc);
    }

    private ArrayList<Score> loadScores() {
        ArrayList<Score> scorelist = new ArrayList<>();
        Map<String, Map> loadedScores = ScoreBoard.loadScores();
        loadedScores.forEach((name, scoreValue) -> {
            scoreValue.forEach((score, date) -> {
                System.out.println("Loading: " + name + ", " + score + ", " + date);
                scorelist.add(new Score(name, (String) date, (int) score));
            });
        });
        return scorelist;
    }

    public void addScore(Score score) {
        scores.add(score);
        ScoreBoard.appendScore(score.getName(), score.getScore(), score.getDate());
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
        }else if(e.getSource() == settingsPanelLabel){
            AudioPlayer.stop();
            cardLayout.show(cardPanel, "Settings Page");
            AudioPlayer.play("/files/AI_voice_sound.wav", true);
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

        } else if(e.getSource() == settingsPanelLabel){
            settingsPanelLabel.setFont(boldCustomFont );

        }
        else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButtonClicked);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButtonClicked);
        }

        if (!(e.getSource() == exitButtonLabel || e.getSource() == minimizeButtonLabel || e.getSource() == title || e.getSource() == contentPageButton || e.getSource() == contactPageButton || e.getSource() == homePageButton || e.getSource() == gamedescriptionButton || e.getSource() == leaderboardsButton || e.getSource() == settingsPanelLabel)) {
            AudioPlayer.play("/files/SFX_button_1.wav", false);
            System.out.println("sfx1");
        } 
        else if (!(e.getSource() == title)) {
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

        }else if(e.getSource() == settingsPanelLabel){
            settingsPanelLabel.setFont(customFont);

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

