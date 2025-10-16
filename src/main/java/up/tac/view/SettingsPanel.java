package up.tac.view;

import up.tac.model.ResourceManager;
import up.tac.model.AudioPlayer;

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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SettingsPanel extends JPanel implements MouseListener{
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Image bg_image;
    private JPanel upperPanel, lowerPanel;
    private JLabel title, homePageButton, contentPageButton, contactPageButton, gamedescriptionButton, exitButtonLabel, minimizeButtonLabel, leaderboardsButton, settingsPanelLabel;
    private Font customFont = new Font("Arial", Font.PLAIN, 21);
    private Font boldCustomFont = new Font("Arial", Font.BOLD, 21);
    private Font titleFont = new Font("Arial", Font.BOLD, 50);
    private ImageIcon exitButton, exitButtonClicked, minimizeButton, minimizeButtonClicked;
    private ResourceManager resourceManager;
    private Dimension frameDimension;
    private JLabel toggleSound, toggleSoundFX;
    private boolean muted = false, mutedFX = false; 
    private JSlider volumeSlider;
    private float newVolume;
   

    public SettingsPanel(CardLayout cardLayout, JPanel cardPanel, ResourceManager resourceManager, Dimension frameDimension){
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
        leaderboardsButton.setFont(customFont);
        leaderboardsButton.addMouseListener(this);

        settingsPanelLabel = new JLabel("Sound"); 
        settingsPanelLabel.setForeground(java.awt.Color.black); 
        settingsPanelLabel.setFont(boldCustomFont);
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
        gbc.gridwidth = 1;


        // --- 2. GAME OVER TITLE (gridy = 1) ---
        JLabel title = new JLabel("Sound Settings");
        title.setForeground(new Color(0x0057cc));
        title.setFont(titleFont);

        
        gbc.gridy = 1;
        // Add vertical insets above and below the title
        gbc.insets = new Insets((int) (frameDimension.getHeight()/ 73), 0, (int) (frameDimension.getHeight()/ 73), 0);
        lowerPanel.add(title, gbc);


        gbc.gridy = 2; 
        
        // Reset insets or set new ones as needed for spacing between title and toggle label
        gbc.insets = new Insets((int) (frameDimension.getHeight()/ 20), 0, (int) (frameDimension.getHeight()/ 73), 0); 

        toggleSound = new JLabel("TOGGLE BG MUSIC: ON");
        toggleSound.setForeground(java.awt.Color.black);
        toggleSound.setFont(customFont);
        toggleSound.addMouseListener(this);
        

        lowerPanel.add(toggleSound, gbc); 
        
        toggleSoundFX = new JLabel("TOGGLE SOUND FX: ON");
        toggleSoundFX.setForeground(java.awt.Color.black);
        toggleSoundFX.setFont(customFont);
        toggleSoundFX.addMouseListener(this);

        gbc.gridy = 3; 
        gbc.insets = new Insets((int) (frameDimension.getHeight()/ 30), 0, (int) (frameDimension.getHeight()/ 73), 0); 

        lowerPanel.add(toggleSoundFX, gbc);

        volumeSlider = new JSlider(0, 100, 80); 
        volumeSlider.setOpaque(false);
        volumeSlider.setPreferredSize(new Dimension((int) (frameDimension.getWidth() / 3.5), (int) (frameDimension.getHeight() / 20)));
        
        // Add a ChangeListener to update the volume as the slider moves
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Get the slider value (0 to 100) and convert it to a float (0.0 to 1.0)
                newVolume = volumeSlider.getValue() / 100.0f;
                if (!mutedFX) AudioPlayer.setVolume(newVolume);
                if (!muted) AudioPlayer.setBgmVolume(newVolume); // Assuming one slider controls both
            }
        });

        gbc.gridy = 4; // New row for the slider
        gbc.insets = new Insets((int) (frameDimension.getHeight()/ 73), 0, (int) (frameDimension.getHeight()/ 73), 0);
        lowerPanel.add(volumeSlider, gbc);


        gbc.gridy = 5;
        gbc.weighty = 1.0; 
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel bottomFiller = new JPanel();
        bottomFiller.setOpaque(false);
        lowerPanel.add(bottomFiller, gbc);

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

        } else if (e.getSource() == contentPageButton) {
            AudioPlayer.stop();
            cardLayout.show(cardPanel, "Content Page");
            AudioPlayer.play("/files/AI_voice_contact.wav", true);

        }
        else if (e.getSource() == contactPageButton) {
            AudioPlayer.stop();
            cardLayout.show(cardPanel, "Contact Page");
            AudioPlayer.play("/files/AI_voice_contact.wav", true);

        }else if(e.getSource() == gamedescriptionButton){
            AudioPlayer.stop();
            cardLayout.show(cardPanel, "Game Description Page");
            AudioPlayer.play("/files/AI_voice_description.wav", true);

        }else if(e.getSource() == leaderboardsButton){
            AudioPlayer.stop();
            cardLayout.show(cardPanel, "Leaderboards Page");
            AudioPlayer.play("/files/AI_voice_leaderboards.wav", true);
        }
        else if (e.getSource() == title){
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI("https://github.com/ali1x3/CMSC170_Standard_Jeopardy"));
                }
                catch (Exception e1) {
                    System.out.println("Desktop browsing Failed.");
                    e1.printStackTrace();
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == toggleSound) {
            if (muted) {
                toggleSound.setText("TOGGLE BG MUSIC: ON");
                AudioPlayer.setBgmVolume(newVolume);
            } else {
                toggleSound.setText("TOGGLE BG MUSIC: OFF");
                AudioPlayer.setBgmVolume(0);
            }
            muted = !muted;
        } else if (e.getSource() == toggleSoundFX) {
            if (mutedFX) {
                toggleSoundFX.setText("TOGGLE SOUND FX: ON");
                AudioPlayer.setVolume(newVolume);
            } else {
                toggleSoundFX.setText("TOGGLE SOUND FX: OFF");
                AudioPlayer.setVolume(0);
            }
            mutedFX = !mutedFX;
        }
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
            leaderboardsButton.setFont(boldCustomFont);

        }else if(e.getSource() == settingsPanelLabel){
            settingsPanelLabel.setFont(customFont);

        }else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButtonClicked);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButtonClicked);
        } else if (e.getSource() == toggleSound) {
            toggleSound.setFont(boldCustomFont);
        } else if (e.getSource() == toggleSoundFX) {
            toggleSoundFX.setFont(boldCustomFont);
        }

        if (!(e.getSource() == exitButtonLabel || e.getSource() == minimizeButtonLabel || e.getSource() == title || e.getSource() == contentPageButton || e.getSource() == contactPageButton || e.getSource() == homePageButton || e.getSource() == gamedescriptionButton || e.getSource() == leaderboardsButton || e.getSource() == settingsPanelLabel || e.getSource() == toggleSound || e.getSource() == toggleSoundFX)) {
            AudioPlayer.play("/files/SFX_button_1.wav", false);
        } 
        else if (!(e.getSource() == title || e.getSource() == contentPageButton)) {
            AudioPlayer.play("/files/SFX_button_2.wav", false);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == contentPageButton) {
            contentPageButton.setFont(customFont);
        }else if (e.getSource() == toggleSound) {
            toggleSound.setFont(customFont);
        }else if (e.getSource() == toggleSoundFX) {
            toggleSoundFX.setFont(customFont);
        }
        else if (e.getSource() == contactPageButton) {
            contactPageButton.setFont(customFont);
        }
        else if (e.getSource() == homePageButton){
            homePageButton.setFont(customFont);
            
        }else if(e.getSource() == leaderboardsButton){
            leaderboardsButton.setFont(customFont);

        }else if(e.getSource() == settingsPanelLabel){
            settingsPanelLabel.setFont(boldCustomFont);

        }else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButton);

        }else if(e.getSource() == gamedescriptionButton){
            gamedescriptionButton.setFont(customFont);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButton);

        }
    }
}
