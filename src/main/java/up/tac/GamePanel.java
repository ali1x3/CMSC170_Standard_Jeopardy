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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


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
    private Dimension frameDimension;

    public GamePanel(CardLayout cardLayout, JPanel cardPanel, Dimension frameDimension){
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.frameDimension = frameDimension;
        
        bg_image = new ImageIcon(getClass().getResource("/files/gamePanel_bg.jpg")).getImage();

        this.setLayout(new BorderLayout());

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/files/Cousine-Regular.ttf"));
            customFont = customFont.deriveFont(Font.PLAIN, (int) frameDimension.getHeight()/25);
            boldCustomFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/files/Cousine-Bold.ttf"));
            boldCustomFont = boldCustomFont.deriveFont(Font.BOLD, (int) frameDimension.getHeight()/25);
            titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/files/AnonymousPro-Bold.ttf"));
            titleFont = titleFont.deriveFont(Font.BOLD, (int) frameDimension.getHeight()/10);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

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


        exitButton = new ImageIcon(getClass().getResource("/files/exitButton.jpg"));
        Image exitButtonResized = exitButton.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        exitButton = new ImageIcon(exitButtonResized);

        minimizeButton = new ImageIcon(getClass().getResource("/files/minimizeButton.jpg"));
        Image minimizeButtonResized = minimizeButton.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        minimizeButton = new ImageIcon(minimizeButtonResized);

        exitButtonClicked = new ImageIcon(getClass().getResource("/files/exitButton_clicked.jpg"));
        Image exitButtonClickedResized = exitButtonClicked.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        exitButtonClicked = new ImageIcon(exitButtonClickedResized);

        minimizeButtonClicked = new ImageIcon(getClass().getResource("/files/minimizeButton_clicked.jpg"));
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

        JLabel title = new JLabel("TRACKER UI HERE");
        title.setForeground(new Color(0x0057cc));
        title.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/48.9)));
        
        Border border = BorderFactory.createLineBorder(Color.black, 3);

        Border padding = BorderFactory.createEmptyBorder((int) (frameDimension.getWidth()/137.5), (int) (frameDimension.getWidth()/137.5), (int) (frameDimension.getWidth()/137.5), (int) (frameDimension.getWidth()/137.5));

        Border combinedBorder = BorderFactory.createCompoundBorder(border, padding);

        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension((int) (frameDimension.getWidth()/5.5), (int) (frameDimension.getHeight()/1.5)));
        rightPanel.setBackground(new Color(0xd1d3d4));
        rightPanel.setBorder(combinedBorder);
        rightPanel.add(title);


        tempBackButton = new JLabel("Temporary Back Button");
        Font smallerFont = customFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/52.36)); 
        tempBackButton.setFont(smallerFont);
        tempBackButton.addMouseListener(this);

        rightPanel.add(tempBackButton);

        
        
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
        }


        leftPanel.setOpaque(true);
        rightPanel.setOpaque(true);

        JPanel anotherFillerPanel = new JPanel();
        anotherFillerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

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
