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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import up.tac.Resource.ResourceManager;


public class GamePanel extends JPanel implements MouseListener{
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private QuestionPanel questionPanel;
    private Image bg_image;
    private JPanel upperPanel, lowerPanel;
    private JLabel title, exitButtonLabel, minimizeButtonLabel, endButtonLabel, scoreTracker;
    private Font customFont = new Font("Arial", Font.PLAIN, 21);
    private Font boldCustomFont = new Font("Arial", Font.BOLD, 21);
    private Font titleFont = new Font("Arial", Font.BOLD, 50);
    private ImageIcon exitButton, exitButtonClicked, minimizeButton, minimizeButtonClicked, endButton, endButtonClicked;
    private Dimension frameDimension;
    private ScoreTrackerBar trackerBar;
    private int totalScore = 0;
    private ResourceManager resourceManager;

    public GamePanel(CardLayout cardLayout, JPanel cardPanel, ResourceManager resourceManager, Dimension frameDimension, QuestionPanel questionPanel){
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.resourceManager = resourceManager;
        this.frameDimension = frameDimension;
        this.questionPanel = questionPanel;

        bg_image = resourceManager.getImageIcon("Game Panel BG").getImage();

        this.setLayout(new BorderLayout());

        customFont = resourceManager.getCousineRegular();
        customFont = customFont.deriveFont(Font.PLAIN, (int) frameDimension.getHeight()/25);
        boldCustomFont = resourceManager.getCousineBold();
        boldCustomFont = boldCustomFont.deriveFont(Font.BOLD, (int) frameDimension.getHeight()/25);
        titleFont = resourceManager.getAnonymousProBold();
        titleFont = titleFont.deriveFont(Font.BOLD, (int) frameDimension.getHeight()/10);

        setUpperPanel();
        setLowerPanel();

        // add the upper and lower panels
        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.CENTER);

    }

    private void setUpperPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        upperPanel = new JPanel();
        upperPanel.setOpaque(false);
        upperPanel.setPreferredSize(new Dimension(0, (int) (frameDimension.getHeight()/7.3)));
        upperPanel.setLayout(new GridBagLayout());

        title = new JLabel("You are currently playing!");
        title.setForeground(java.awt.Color.lightGray);
        title.setFont(boldCustomFont);
        title.addMouseListener(this);


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
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/11), (int) (frameDimension.getWidth()/27.5), (int) (frameDimension.getHeight()/19.8), (int) (frameDimension.getWidth()/2.53));

        upperPanel.add(title, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/4.9), 0, (int) (frameDimension.getHeight()/5.8), (int) (frameDimension.getWidth()/110));

        upperPanel.add(minimizeButtonLabel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/4.9), (int) (frameDimension.getWidth()/110), (int) (frameDimension.getHeight()/5.8), (int) (frameDimension.getWidth()/31.43));
        upperPanel.add(exitButtonLabel, gbc);


    }

    private void setLowerPanel() {
        lowerPanel = new JPanel();
        lowerPanel.setPreferredSize(new Dimension((int) frameDimension.getHeight(), (int) (frameDimension.getHeight()/2)));
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new GridBagLayout());

        JPanel fillerPanel = new JPanel();
        fillerPanel.setLayout(new BorderLayout((int) (frameDimension.getWidth()/44), (int) (frameDimension.getWidth()/44)));
        fillerPanel.setOpaque(false);

        Border border = BorderFactory.createLineBorder(Color.black, 3);

        Border padding = BorderFactory.createEmptyBorder((int) (frameDimension.getWidth()/137.5), (int) (frameDimension.getWidth()/137.5), (int) (frameDimension.getWidth()/137.5), (int) (frameDimension.getWidth()/137.5));

        Border combinedBorder = BorderFactory.createCompoundBorder(border, padding);


        // -------- RIGHT PANEL CODE STARTS HERE ---------

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension((int) (frameDimension.getWidth()/5.5), (int) (frameDimension.getHeight()/1.5)));
        rightPanel.setBackground(new Color(0xd1d3d4));
        rightPanel.setBorder(combinedBorder);
        rightPanel.setLayout(new GridBagLayout());

        trackerBar = new ScoreTrackerBar(new Dimension((int) (frameDimension.getWidth()/15), (int) (frameDimension.getHeight()/2)), border);

        trackerBar.setScorePercentage(totalScore/16800);

        scoreTracker = new JLabel("Score: " + Integer.toString(totalScore));
        scoreTracker.setFont(boldCustomFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/40)));
        scoreTracker.setAlignmentX(CENTER_ALIGNMENT);

        endButton = new ImageIcon(getClass().getResource("/files/endButton.jpg"));
        Image endButtonImageResized = endButton.getImage().getScaledInstance((int) (frameDimension.getWidth()/7.5), (int) (frameDimension.getHeight()/19), Image.SCALE_DEFAULT);
        endButton = new ImageIcon(endButtonImageResized);

        endButtonClicked = new ImageIcon(getClass().getResource("/files/endButton_clicked.jpg"));
        Image endButtonClickedImageResized = endButtonClicked.getImage().getScaledInstance((int) (frameDimension.getWidth()/7.5), (int) (frameDimension.getHeight()/19), Image.SCALE_AREA_AVERAGING);
        endButtonClicked = new ImageIcon(endButtonClickedImageResized);

        endButtonLabel = new JLabel(endButton);
        endButtonLabel.addMouseListener(this);

        JPanel moreFillerPanel = new JPanel();
        moreFillerPanel.setLayout(new GridBagLayout());
        moreFillerPanel.setOpaque(false);

        JLabel label95 = new JLabel("95%");
        JLabel label85 = new JLabel("85%");
        JLabel label75 = new JLabel("75%");
        JLabel label65 = new JLabel("65%");
        JLabel label55 = new JLabel("55%");
        JLabel label45 = new JLabel("45%");
        JLabel label35 = new JLabel("35%");
        JLabel label25 = new JLabel("25%");
        JLabel label15 = new JLabel("15%");
        JLabel label05 = new JLabel("5%");

        int fontSize = (int) (frameDimension.getHeight() / 45);

        label95.setFont(titleFont.deriveFont(Font.BOLD, fontSize));
        label85.setFont(titleFont.deriveFont(Font.BOLD, fontSize));
        label75.setFont(titleFont.deriveFont(Font.BOLD, fontSize));
        label65.setFont(titleFont.deriveFont(Font.BOLD, fontSize));
        label55.setFont(titleFont.deriveFont(Font.BOLD, fontSize));
        label45.setFont(titleFont.deriveFont(Font.BOLD, fontSize));
        label35.setFont(titleFont.deriveFont(Font.BOLD, fontSize));
        label25.setFont(titleFont.deriveFont(Font.BOLD, fontSize));
        label15.setFont(titleFont.deriveFont(Font.BOLD, fontSize));
        label05.setFont(titleFont.deriveFont(Font.BOLD, fontSize));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridheight = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        int horizontalInset = (int) (frameDimension.getWidth() / 220);
        gbc.insets = new Insets(0, 0, 0, horizontalInset);

        gbc.gridy = 0;
        moreFillerPanel.add(label95, gbc);

        gbc.gridy = 1;
        moreFillerPanel.add(label85, gbc);

        gbc.gridy = 2;
        moreFillerPanel.add(label75, gbc);

        gbc.gridy = 3;
        moreFillerPanel.add(label65, gbc);

        gbc.gridy = 4;
        moreFillerPanel.add(label55, gbc);

        gbc.gridy = 5;
        moreFillerPanel.add(label45, gbc);

        gbc.gridy = 6;
        moreFillerPanel.add(label35, gbc);

        gbc.gridy = 7;
        moreFillerPanel.add(label25, gbc);

        gbc.gridy = 8;
        moreFillerPanel.add(label15, gbc);

        gbc.gridy = 9;
        moreFillerPanel.add(label05, gbc);


        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, (int) (frameDimension.getWidth()/110), 0, 0);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 10;
        moreFillerPanel.add(trackerBar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;

        rightPanel.add(moreFillerPanel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/45), 0, 0, 0);

        rightPanel.add(scoreTracker, gbc);

        gbc.insets = new Insets((int) (frameDimension.getHeight()/40), 0, 0, 0);
        gbc.gridy = 2;

        rightPanel.add(endButtonLabel, gbc);

        // -------- LEFT PANEL CODE STARTS HERE ---------
        ImageIcon normalIcon = new ImageIcon(getClass().getResource("/files/gameButton_bg.png"));
        Image normalIconResized = normalIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/14.66), (int) (frameDimension.getHeight()/14.66), Image.SCALE_DEFAULT);
        ImageIcon rolloverIcon = new ImageIcon(getClass().getResource("/files/gameButtonClicked_bg.png"));
        Image rolloverIconResized = rolloverIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/14.66), (int) (frameDimension.getHeight()/14.66), Image.SCALE_DEFAULT);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension((int) (frameDimension.getWidth()/1.467), (int) (frameDimension.getHeight()/1.5)));
        leftPanel.setBackground(new Color(0xd1d3d4));
        leftPanel.setBorder(combinedBorder);
        leftPanel.setLayout(new GridLayout(7, 8, 3, 3));

        for(int i = 0; i < 8; i++) {
            JLabel category = new JLabel("CATEGORY", JLabel.CENTER);
            category.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/43.1)));
            leftPanel.add(category);
        }


        for(int i = 0; i < 48; i++) {
            JButton tempButton = new JButton(new ImageIcon(normalIconResized));
            tempButton.setPressedIcon(new ImageIcon(rolloverIconResized));
            int row = i / 8;
            int value = (row + 1) * 100;
            tempButton.setText(String.valueOf(value));
            tempButton.setHorizontalTextPosition(JButton.CENTER);
            tempButton.setVerticalTextPosition(JButton.CENTER);
            tempButton.setForeground(Color.BLACK);
            tempButton.setBorderPainted(false);
            tempButton.setContentAreaFilled(false);
            tempButton.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/43.1)));
            leftPanel.add(tempButton);
            tempButton.addMouseListener(this);
        }


        // -------- COMBINING BOTH LEFT AND RIGHT PANELS ---------

        leftPanel.setOpaque(true);
        rightPanel.setOpaque(true);

        JPanel anotherFillerPanel = new JPanel();
        anotherFillerPanel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/36.65), (int) (frameDimension.getWidth()/110), (int) (frameDimension.getHeight()/36.65), (int) (frameDimension.getWidth()/110));
        anotherFillerPanel.add(leftPanel);

        fillerPanel.add(anotherFillerPanel, BorderLayout.CENTER);

        fillerPanel.add(rightPanel, BorderLayout.EAST);

        gbc.insets = new Insets((int) (frameDimension.getHeight()/73.3), (int) (frameDimension.getWidth()/110), (int) (frameDimension.getHeight()/18.325), (int) (frameDimension.getWidth()/110));
        lowerPanel.add(fillerPanel);

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
        else if (e.getSource() instanceof JButton) {
            totalScore += 200;
            trackerBar.setScorePercentage((double) totalScore/16800);
            scoreTracker.setText("Score: " + Integer.toString(totalScore));
            System.out.println("Button Pressed!");
            repaint();
            cardLayout.show(cardPanel, "Question Panel");
            AudioPlayer.play("/files/AI_voice_timer.wav", false);
            AudioPlayer.playBGM("/files/BGM_question_panel.wav");
            questionPanel.initializePanel();
            questionPanel.setRemainingTime(10);
            questionPanel.startTimer();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == endButtonLabel) {
            totalScore = 0;
            trackerBar.setScorePercentage((double) totalScore/16800);
            scoreTracker.setText("Score: " + Integer.toString(totalScore));
            repaint();
            cardLayout.show(cardPanel, "Home Page");
            AudioPlayer.play("/files/AI_voice_end_game.wav", false);
            AudioPlayer.playBGM("/files/BGM_jeopardy.wav");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButtonClicked);
            AudioPlayer.play("/files/SFX_button_2.wav", false);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButtonClicked);
            AudioPlayer.play("/files/SFX_button_2.wav", false);
        }
        else if(e.getSource() == endButtonLabel) {
            endButtonLabel.setIcon(endButtonClicked);
            AudioPlayer.play("/files/SFX_button_1.wav", false);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButton);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButton);
        }
        else if(e.getSource() == endButtonLabel) {
            endButtonLabel.setIcon(endButton);
        }
    }
}
