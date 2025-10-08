package up.tac;

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
import java.util.Objects;


public class GamePanel extends JPanel implements MouseListener{
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Image bg_image;
    private JPanel upperPanel, lowerPanel;
    private JLabel title, exitButtonLabel, minimizeButtonLabel, tempBackButton;
    private Font customFont = new Font("Arial", Font.PLAIN, 21);
    private Font boldCustomFont = new Font("Arial", Font.BOLD, 21);
    private Font titleFont = new Font("Arial", Font.BOLD, 50);
    private ImageIcon exitButton, exitButtonClicked, minimizeButton, minimizeButtonClicked;

    public GamePanel(CardLayout cardLayout, JPanel cardPanel){
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        bg_image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/files/gamePanel_bg.jpg"))).getImage();

        this.setLayout(new BorderLayout());

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/files/Cousine-Regular.ttf"));
            customFont = customFont.deriveFont(Font.PLAIN, 21);
            boldCustomFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/files/Cousine-Bold.ttf"));
            boldCustomFont = boldCustomFont.deriveFont(Font.BOLD, 21);
            titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/files/AnonymousPro-Bold.ttf"));
            titleFont = titleFont.deriveFont(Font.BOLD, 50);
        }
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        setUpperPanel();
        setLowerPanel();

        // add the upper and lower panels
        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.CENTER);
        add(new JPanel(new BorderLayout(10, 10)), BorderLayout.SOUTH); // Filler

    }

    private void setUpperPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        upperPanel = new JPanel();
        upperPanel.setOpaque(false);
        upperPanel.setPreferredSize(new Dimension(0, 200));
        upperPanel.setLayout(new GridBagLayout());

        title = new JLabel("You are currently playing!");
        title.setForeground(java.awt.Color.lightGray);
        title.setFont(boldCustomFont);
        title.addMouseListener(this);


        exitButton = new ImageIcon(getClass().getResource("/files/exitButton.jpg"));
        Image exitButtonResized = exitButton.getImage().getScaledInstance(34, 34, Image.SCALE_DEFAULT);
        exitButton = new ImageIcon(exitButtonResized);

        minimizeButton = new ImageIcon(getClass().getResource("/files/minimizeButton.jpg"));
        Image minimizeButtonResized = minimizeButton.getImage().getScaledInstance(34, 34, Image.SCALE_DEFAULT);
        minimizeButton = new ImageIcon(minimizeButtonResized);

        exitButtonClicked = new ImageIcon(getClass().getResource("/files/exitButton_clicked.jpg"));
        Image exitButtonClickedResized = exitButtonClicked.getImage().getScaledInstance(34, 34, Image.SCALE_DEFAULT);
        exitButtonClicked = new ImageIcon(exitButtonClickedResized);

        minimizeButtonClicked = new ImageIcon(getClass().getResource("/files/minimizeButton_clicked.jpg"));
        Image minimizeButtonClickedResized = minimizeButtonClicked.getImage().getScaledInstance(34, 34, Image.SCALE_DEFAULT);
        minimizeButtonClicked = new ImageIcon(minimizeButtonClickedResized);

        exitButtonLabel = new JLabel(exitButton);
        exitButtonLabel.addMouseListener(this);

        minimizeButtonLabel = new JLabel(minimizeButton);
        minimizeButtonLabel.addMouseListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 30, 100, 412);

        upperPanel.add(title, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 103, 6);

        upperPanel.add(minimizeButtonLabel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 7, 103, 30);
        upperPanel.add(exitButtonLabel, gbc);


    }

    private void setLowerPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        lowerPanel = new JPanel();
        lowerPanel.setPreferredSize(new Dimension(0, 400));
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("ADD GAME PANEL UI HERE");
        title.setForeground(new Color(0x0057cc));
        title.setFont(titleFont);

        tempBackButton = new JLabel("Temporary Back Button");
        Font smallerFont = customFont.deriveFont(Font.BOLD, 20); 
        tempBackButton.setFont(smallerFont);
        tempBackButton.addMouseListener(this);

        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        lowerPanel.add(title, gbc);

        gbc.insets = new Insets(10, 0, 150, 0);
        gbc.gridy = 1;
        lowerPanel.add(tempBackButton, gbc);


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
    } 

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == tempBackButton) {
            cardLayout.show(cardPanel, "Home Page");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButtonClicked);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButtonClicked);
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
    }
}
