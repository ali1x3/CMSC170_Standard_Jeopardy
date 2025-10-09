package up.tac;

import up.tac.Resource.ResourceManager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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
import java.io.IOException;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MainPagePanel extends JPanel implements MouseListener{
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Image bg_image;
    private JPanel upperPanel, lowerPanel;
    private JLabel title, homePageButton, contentPageButton, contactPageButton, startButtonLabel, exitButtonLabel, minimizeButtonLabel;
    private Font customFont = new Font("Arial", Font.PLAIN, 30);
    private Font boldCustomFont = new Font("Arial", Font.BOLD, 30);
    private Font titleFont = new Font("Arial", Font.BOLD, 72);
    private ImageIcon startButton, startButtonClicked, exitButton, exitButtonClicked, minimizeButton, minimizeButtonClicked;
    private ResourceManager resourceManager;
    private ImageIcon bg_gif;
    private Dimension frameDimension;

    public MainPagePanel(CardLayout cardLayout, JPanel cardPanel, ResourceManager resourceManager, Dimension frameDimension){
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.resourceManager = resourceManager;
        this.frameDimension = frameDimension;

        bg_image = resourceManager.getImageIcon("Main Panel BG").getImage();

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
        homePageButton.setFont(boldCustomFont);
        homePageButton.addMouseListener(this);

        contentPageButton = new JLabel("Content");
        contentPageButton.setForeground(java.awt.Color.black);
        contentPageButton.setFont(customFont);
        contentPageButton.addMouseListener(this);

        contactPageButton = new JLabel("Contact");
        contactPageButton.setForeground(java.awt.Color.black);
        contactPageButton.setFont(customFont);
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

        JLabel title = new JLabel("ARTIFICIAL INTELLIGENCE");
        title.setForeground(new Color(0x0057cc));
        title.setFont(titleFont);

        JLabel header1 = new JLabel("JEOPARDY GAME");
        Font biggerFont = customFont.deriveFont(Font.PLAIN, (int) (frameDimension.getHeight()/18.325));
        header1.setFont(biggerFont);
        header1.setForeground(Color.black);

        startButton = new ImageIcon(getClass().getResource("/files/startButton.jpg"));
        Image startButtonImageResized = startButton.getImage().getScaledInstance((int) (frameDimension.getWidth()/6.2), (int) (frameDimension.getHeight()/15.7), Image.SCALE_DEFAULT);
        startButton = new ImageIcon(startButtonImageResized);

        startButtonClicked = new ImageIcon(getClass().getResource("/files/startButton_clicked.jpg"));
        // Image startButtonClickedImageResized = startButtonClicked.getImage().getScaledInstance(150, 41, Image.SCALE_SMOOTH);
        Image startButtonClickedImageResized = startButtonClicked.getImage().getScaledInstance((int) (frameDimension.getWidth()/6.2), (int) (frameDimension.getHeight()/15.7), Image.SCALE_SMOOTH);
        startButtonClicked = new ImageIcon(startButtonClickedImageResized);        

        startButtonLabel = new JLabel(startButton);
        startButtonLabel.addMouseListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        lowerPanel.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/24.5), 0, 0, 0);
        lowerPanel.add(header1, gbc);
        gbc.insets = new Insets((int) (frameDimension.getHeight()/14.7), 0, (int) (frameDimension.getHeight()/4.9), 0);
        gbc.gridy = 2;
        lowerPanel.add(startButtonLabel, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // // don't proceed if bg_image is null
        // if (bg_image == null) {
        //     // System.out.println("Background Image failed to Load");
        //     // return;
        // }


        // // proceed if bg_image is not null
        // // Graphics2D g2d = (Graphics2D) g;
        // // g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // // g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // // g2d.drawImage(bg_image, 0, 0, getWidth(), getHeight(), this);

        if(bg_gif != null){
            Graphics2D g2d = (Graphics2D) g;
            // Add rendering hints for better image quality when scaled
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Draw the GIF from position (0,0) and scale it to the panel's full width and height
            g2d.drawImage(bg_gif.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == startButtonLabel) {
            cardLayout.show(cardPanel, "Game Panel");
        }
        else if (e.getSource() == minimizeButtonLabel) {
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
        else if (e.getSource() == contactPageButton) {
            cardLayout.show(cardPanel, "Contact Page");
        }
        else if (e.getSource() == contentPageButton) {
            cardLayout.show(cardPanel, "Content Page");
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == startButtonLabel){
            startButtonLabel.setIcon(startButton);
        }
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
            homePageButton.setFont(customFont);
        }
        else if (e.getSource() == startButtonLabel) {
            startButtonLabel.setIcon(startButtonClicked);
        }
        else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButtonClicked);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButtonClicked);
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
            homePageButton.setFont(boldCustomFont);
        }
        else if (e.getSource() == startButtonLabel){
            startButtonLabel.setIcon(startButton);
        }
        else if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButton);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButton);
        }
    }
}
