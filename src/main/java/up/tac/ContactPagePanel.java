package up.tac;

import up.tac.Resource.ResourceManager;

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


public class ContactPagePanel extends JPanel implements MouseListener{
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Image bg_image;
    private JPanel upperPanel, lowerPanel;
    private JLabel title, homePageButton, contentPageButton, contactPageButton, exitButtonLabel, minimizeButtonLabel;
    private Font customFont = new Font("Arial", Font.PLAIN, 21);
    private Font boldCustomFont = new Font("Arial", Font.BOLD, 21);
    private Font titleFont = new Font("Arial", Font.BOLD, 56);
    private ImageIcon exitButton, exitButtonClicked, minimizeButton, minimizeButtonClicked;

    private JLabel github1, github2, github3, github4, github5, github6;
    private JLabel email1, email2, email3, email4, email5, email6;
    private Dimension frameDimension;

    private ResourceManager resourceManager;

    public ContactPagePanel(CardLayout cardLayout, JPanel cardPanel, ResourceManager resourceManager,
                            Dimension frameDimension){
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.resourceManager = resourceManager;
        this.frameDimension = frameDimension;

        bg_image = resourceManager.getImageIcon("Contact Panel BG").getImage();

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

        contentPageButton = new JLabel("Content");
        contentPageButton.setForeground(java.awt.Color.black);
        contentPageButton.setFont(customFont);
        contentPageButton.addMouseListener(this);

        contactPageButton = new JLabel("Contact");
        contactPageButton.setForeground(java.awt.Color.black);
        contactPageButton.setFont(boldCustomFont);
        contactPageButton.addMouseListener(this);

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
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/16, (int) (frameDimension.getWidth()/27.5), (int) frameDimension.getHeight()/49, (int) (frameDimension.getWidth()/2.4));

        upperPanel.add(title, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) frameDimension.getHeight()/18, 0, (int) (frameDimension.getHeight()/40.7), (int) (frameDimension.getWidth()/137.5));

        upperPanel.add(minimizeButtonLabel, gbc);

        gbc.gridx = 4;
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

        gbc.gridx = 3;
        gbc.weightx = 1;
        upperPanel.add(new JLabel(), gbc);
    }

    private void setLowerPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        lowerPanel = new JPanel();
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("CONTACT");
        title.setForeground(new Color(0x0057cc));
        title.setFont(titleFont);

        Font smallerFont = customFont.deriveFont(Font.PLAIN, (int) (frameDimension.getHeight()/36.7));
        // For Contact 1
        JLabel name1 = new JLabel("ALEX AVILA");
        github1 = new JLabel("ali1x3");
        email1 = new JLabel("abavila@up.edu.ph");
        name1.setFont(smallerFont);
        github1.setFont(smallerFont);
        email1.setFont(smallerFont);
        github1.addMouseListener(this);
        email1.addMouseListener(this);

        // For Contact 2
        JLabel name2 = new JLabel("ANDREI MARTILLO");
        github2 = new JLabel("ddrhckrzz");
        email2 = new JLabel("agmartillo@up.edu.ph");
        name2.setFont(smallerFont);
        github2.setFont(smallerFont);
        email2.setFont(smallerFont);
        github2.addMouseListener(this);
        email2.addMouseListener(this);

        // For Contact 3
        JLabel name3 = new JLabel("DESIRRE BARBOSA");
        github3 = new JLabel("desiredes");
        email3 = new JLabel("dibarbosa@up.edu.ph");
        name3.setFont(smallerFont);
        github3.setFont(smallerFont);
        email3.setFont(smallerFont);
        github3.addMouseListener(this);
        email3.addMouseListener(this);

        // For Contact 4
        JLabel name4 = new JLabel("MAC ALVARICO");
        github4 = new JLabel("RoninMac");
        email4 = new JLabel("mpalvarico@up.edu.ph");
        name4.setFont(smallerFont);
        github4.setFont(smallerFont);
        email4.setFont(smallerFont);
        github4.addMouseListener(this);
        email4.addMouseListener(this);

        // For Contact 5
        JLabel name5 = new JLabel("NIKKA NAPUTO");
        github5 = new JLabel("nikkanap");
        email5 = new JLabel("nenaputo@up.edu.ph");
        name5.setFont(smallerFont);
        github5.setFont(smallerFont);
        email5.setFont(smallerFont);
        github5.addMouseListener(this);
        email5.addMouseListener(this);

        // For Contact 6
        JLabel name6 = new JLabel("VANCE ORTEGA");
        github6 = new JLabel("MoresVance");
        email6 = new JLabel("mcortega@up.edu.ph");
        name6.setFont(smallerFont);
        github6.setFont(smallerFont);
        email6.setFont(smallerFont);
        github6.addMouseListener(this);
        email6.addMouseListener(this);

        gbc.insets = new Insets(0, 0, (int) (frameDimension.getHeight()/49), 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.ipady = 20;

        lowerPanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridy = 1;
        gbc.ipady = 3;

        gbc.insets = new Insets((int) (frameDimension.getHeight()/49), (int) (frameDimension.getWidth()/55), 0, (int) (frameDimension.getWidth()/55));
        lowerPanel.add(name1, gbc);

        gbc.gridx = 1;
        lowerPanel.add(github1, gbc);

        gbc.gridx = 2;
        lowerPanel.add(email1, gbc);

        gbc.insets = new Insets((int) (frameDimension.getHeight()/73), (int) (frameDimension.getWidth()/55), 0, (int) (frameDimension.getWidth()/55));
        gbc.gridy = 2;

        gbc.gridx = 0;
        lowerPanel.add(name2, gbc);

        gbc.gridx = 1;
        lowerPanel.add(github2, gbc);

        gbc.gridx = 2;
        lowerPanel.add(email2, gbc);

        gbc.gridy = 3;

        gbc.gridx = 0;
        lowerPanel.add(name3, gbc);

        gbc.gridx = 1;
        lowerPanel.add(github3, gbc);

        gbc.gridx = 2;
        lowerPanel.add(email3, gbc);

        gbc.gridy = 4;

        gbc.gridx = 0;
        lowerPanel.add(name4, gbc);

        gbc.gridx = 1;
        lowerPanel.add(github4, gbc);

        gbc.gridx = 2;
        lowerPanel.add(email4, gbc);

        gbc.gridy = 5;

        gbc.gridx = 0;
        lowerPanel.add(name5, gbc);

        gbc.gridx = 1;
        lowerPanel.add(github5, gbc);

        gbc.gridx = 2;
        lowerPanel.add(email5, gbc);

        gbc.insets = new Insets((int) (frameDimension.getHeight()/73), 0, (int) (frameDimension.getHeight()/4.9), 0);
        gbc.gridy = 6;

        gbc.gridx = 0;
        lowerPanel.add(name6, gbc);

        gbc.gridx = 1;
        lowerPanel.add(github6, gbc);

        gbc.gridx = 2;
        lowerPanel.add(email6, gbc);

    }

    private void audioPlayer(String filePath) {
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
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
            cardLayout.show(cardPanel, "Home Page");
            audioPlayer("/files/AI_voice_home.wav");
        }
        else if (e.getSource() == contentPageButton) {
            cardLayout.show(cardPanel, "Content Page");
            audioPlayer("/files/AI_voice_content.wav");
        }
        else if (e.getSource() == github1){
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI("https://github.com/ali1x3"));
                }
                catch (Exception e1) {
                    System.out.println("Desktop browsing Failed.");
                    e1.printStackTrace();
                }
            }
        }
        else if (e.getSource() == github2){
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI("https://github.com/ddrhckrzz"));
                }
                catch (Exception e1) {
                    System.out.println("Desktop browsing Failed.");
                    e1.printStackTrace();
                }
            }

        }
        else if (e.getSource() == github3){
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI("https://github.com/desiredes"));
                }
                catch (Exception e1) {
                    System.out.println("Desktop browsing Failed.");
                    e1.printStackTrace();
                }
            }

        }
        else if (e.getSource() == github4){
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI("https://github.com/RoninMac"));
                } catch (Exception e1) {
                    System.out.println("Desktop browsing Failed.");
                    e1.printStackTrace();
                }
            }

        } else if (e.getSource() == github5){
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI("https://github.com/nikkanap"));
                }
                catch (Exception e1) {
                    System.out.println("Desktop browsing Failed.");
                    e1.printStackTrace();
                }
            }

        } else if (e.getSource() == github6){
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI("https://github.com/MoresVance"));
                }
                catch (Exception e1) {
                    System.out.println("Desktop browsing Failed.");
                    e1.printStackTrace();
                }
            }

        } else if (e.getSource() == title){
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
            contactPageButton.setFont(customFont);
        }
        else if (e.getSource() == homePageButton) {
            homePageButton.setFont(boldCustomFont);
        }
        else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButtonClicked);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButtonClicked);
        }
        else if (e.getSource() == github1 || e.getSource() == github2 || e.getSource() == github3 || e.getSource() == github4 || e.getSource() == github5 || e.getSource() == github6) {
            ((Component) e.getSource()).setForeground(java.awt.Color.blue);
        } 

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == contentPageButton) {
            contentPageButton.setFont(customFont);
        }
        else if (e.getSource() == contactPageButton) {
            contactPageButton.setFont(boldCustomFont);
        }
        else if (e.getSource() == homePageButton){
            homePageButton.setFont(customFont);
        }
        else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButton);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButton);       
        }
        else if (e.getSource() == github1 || e.getSource() == github2 || e.getSource() == github3 || e.getSource() == github4 || e.getSource() == github5 || e.getSource() == github6) {
            ((Component) e.getSource()).setForeground(java.awt.Color.black);
        } 
    }
}
