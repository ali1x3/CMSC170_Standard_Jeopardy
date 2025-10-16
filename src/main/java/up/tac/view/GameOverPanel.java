package up.tac.view;

import up.tac.model.ResourceManager;
import up.tac.model.AudioPlayer;
import up.tac.model.Score;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GameOverPanel extends JPanel implements MouseListener{
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Image bg_image;
    private JPanel upperPanel, lowerPanel;
    private JLabel title; 
    private Font customFont = new Font("Arial", Font.PLAIN, 21);
    private Font boldCustomFont = new Font("Arial", Font.BOLD, 21);
    private Font titleFont = new Font("Arial", Font.BOLD, 56);
    private ImageIcon exitButton, exitButtonClicked, minimizeButton, minimizeButtonClicked;
    private JTextField nameInputField;
    private JLabel homeButton, saveAndGoHomeButton, exitButtonLabel, minimizeButtonLabel;
    private boolean inLeaderboard = true; 
    private int currentScore = 0;
    private LeaderBoardsPanel leaderBoardsPanel;

   
    private Dimension frameDimension;

    private ResourceManager resourceManager;

    public GameOverPanel(CardLayout cardLayout, JPanel cardPanel, ResourceManager resourceManager, Dimension frameDimension, LeaderBoardsPanel leaderBoardsPanel){
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.resourceManager = resourceManager;
        this.frameDimension = frameDimension; 
        this.leaderBoardsPanel = leaderBoardsPanel;

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


         // load and resize the exit and minimize buttons

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
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/16, (int) (frameDimension.getWidth()/27.5), (int) frameDimension.getHeight()/49, (int) (frameDimension.getWidth()/2.4));

        upperPanel.add(title, gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/18, 0, (int) (frameDimension.getHeight()/40.7), (int) (frameDimension.getWidth()/137.5));

        upperPanel.add(minimizeButtonLabel, gbc);

        gbc.gridx = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/18, 0, (int) (frameDimension.getHeight()/40.7), (int) (frameDimension.getWidth()/27.5));
        upperPanel.add(exitButtonLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
    
        gbc.gridx = 4;
        gbc.weightx = 1;
        upperPanel.add(new JLabel(), gbc);
    }

    private void setLowerPanel() {
        lowerPanel = new JPanel();
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // --- 1. TOP VERTICAL SPACER (Pushes content down) ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.3; // Allocates space and pushes components below it down
        gbc.fill = GridBagConstraints.VERTICAL;
        JPanel topSpacer = new JPanel();
        topSpacer.setOpaque(false);
        lowerPanel.add(topSpacer, gbc);

        // Reset weighty for content components
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1; // Ensure it doesn't span across multiple columns (if you add more later)


        // --- 2. GAME OVER TITLE (gridy = 1) ---
        JLabel title = new JLabel("Game Over");
        title.setForeground(new Color(0x0057cc));
        title.setFont(titleFont);

        // Note: Removed the setBounds() call here. It interferes with GridBagLayout.
        
        gbc.gridy = 1;
        // Adjusted vertical insets to control space above/below the title
        gbc.insets = new Insets((int) (frameDimension.getHeight()/ 73), 0, (int) (frameDimension.getHeight()/ 73), 0);
        lowerPanel.add(title, gbc);

        // --- 3. INSTRUCTIONS (gridy = 2) ---
        JLabel instructions = new JLabel("Input Name for Leaderboards");
        instructions.setForeground(java.awt.Color.black);
        instructions.setFont(customFont);
        
        gbc.gridy = 2;
        // Add some space below the instructions
        gbc.insets = new Insets(10, 0, 20, 0); 
        lowerPanel.add(instructions, gbc);

        // --- 4. NAME INPUT FIELD (gridy = 3) ---
        if (inLeaderboard) {
            nameInputField = new JTextField("Anonymous");
            nameInputField.setFont(customFont);
            nameInputField.setForeground(Color.DARK_GRAY);
            // FIX: Enforce a preferred width for the text field to center it properly
            nameInputField.setPreferredSize(new Dimension((int)(frameDimension.getWidth()/4), (int)frameDimension.getHeight()/20));

            gbc.gridy = 3;
            gbc.fill = GridBagConstraints.HORIZONTAL; // Allow text field to use its width
            gbc.insets = new Insets(0, 0, 20, 0); // Space below the text field
            lowerPanel.add(nameInputField, gbc);
            gbc.fill = GridBagConstraints.NONE; // Reset fill for the button
        } else {
            // Handle homeButton visibility if inLeaderboard is false
            gbc.gridy = 3;
            lowerPanel.add(homeButton, gbc); // Assuming homeButton is initialized elsewhere
        }

        // --- 5. SAVE BUTTON (gridy = 4) ---
        saveAndGoHomeButton = new JLabel("Save & Go Home");
        saveAndGoHomeButton.setForeground(java.awt.Color.black);
        saveAndGoHomeButton.setFont(customFont);
        saveAndGoHomeButton.addMouseListener(this);
        saveAndGoHomeButton.setToolTipText("Enter your name in the game to save your score to the leaderboard");
        
        // Note: Removed the setBounds() call here as it interferes with GridBagLayout.
        
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0); // No extra insets needed here
        
        if(inLeaderboard){
            saveAndGoHomeButton.setVisible(true);
            // You'll need to initialize homeButton if you want to use it
            // homeButton.setVisible(false); 
            lowerPanel.add(saveAndGoHomeButton, gbc);
        }else{
            // saveAndGoHomeButton.setVisible(false);
            // homeButton.setVisible(true);
            // lowerPanel.add(homeButton, gbc);
            // Added the button that is visible in 'inLeaderboard' mode for consistency
            lowerPanel.add(saveAndGoHomeButton, gbc);
        }
        
        // --- 6. BOTTOM VERTICAL FILLER (Pushes content up) ---
        gbc.gridy = 5;
        gbc.weighty = 1.0; // Allocates space and pulls components above it up
        gbc.fill = GridBagConstraints.VERTICAL;
        JPanel bottomFiller = new JPanel();
        bottomFiller.setOpaque(false);
        lowerPanel.add(bottomFiller, gbc);
    }

    public void reinit() {
        nameInputField.setText("Anonymous");
    }

    public void setScore(int score) {
        this.currentScore = score;
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
        else if (e.getSource() == homeButton) {
            AudioPlayer.stop();
            cardLayout.show(cardPanel, "Home Page");
            AudioPlayer.play("/files/AI_voice_home.wav", true);
        }
        else if (e.getSource() == saveAndGoHomeButton) {
            JFrame parentFrame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
             if (CustomPopupDialog.showConfirm(parentFrame,
                        "Saving Score",
                        "This cannot be changed afterwards. Proceed?")) {
                Score newScore = new Score();
                newScore.setDate(java.time.LocalDate.now());
                newScore.setName(nameInputField.getText());
                newScore.setScore(currentScore);
                leaderBoardsPanel.addScore(newScore);
                leaderBoardsPanel.reinit();
                AudioPlayer.stop();
                cardLayout.show(cardPanel, "Home Page");
                AudioPlayer.play("/files/AI_voice_savedToLeaderboard.wav", true);
            }
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
        if (e.getSource() == homeButton) {
            homeButton.setFont(boldCustomFont);
        }
        else if (e.getSource() == saveAndGoHomeButton) {
            saveAndGoHomeButton.setFont(boldCustomFont);

        }else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButtonClicked);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButtonClicked);
        }

        if (!(e.getSource() == exitButtonLabel || e.getSource() == minimizeButtonLabel || e.getSource() == title)) {
            AudioPlayer.play("/files/SFX_button_1.wav", false);
        } 
        else if (!(e.getSource() == title)) {
            AudioPlayer.play("/files/SFX_button_2.wav", false);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == homeButton){
            homeButton.setFont(customFont);

        }else if (e.getSource() == saveAndGoHomeButton) {
            saveAndGoHomeButton.setFont(customFont);

        }else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButton);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButton);       
        }
    }
}
