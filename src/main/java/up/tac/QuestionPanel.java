package up.tac;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class QuestionPanel extends JPanel implements MouseListener{
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Image bg_image;
    private JPanel upperPanel, lowerPanel;
    private JLabel title, timer, exitButtonLabel, minimizeButtonLabel, tempBackLabel;
    private Font customFont = new Font("Arial", Font.PLAIN, 21);
    private Font boldCustomFont = new Font("Arial", Font.BOLD, 21);
    private Font titleFont = new Font("Arial", Font.BOLD, 50);
    private ImageIcon exitButton, exitButtonClicked, minimizeButton, minimizeButtonClicked;
    private Dimension frameDimension;
    private javax.swing.Timer countdownTimer; 
    private int timeRemaining;

    public QuestionPanel(CardLayout cardLayout, JPanel cardPanel, Dimension frameDimension){
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

        title = new JLabel("Choose an Answer!");
        title.setForeground(java.awt.Color.lightGray);
        title.setFont(boldCustomFont);
        title.addMouseListener(this);

        timer = new JLabel("Timer: 0");
        timer.setForeground(java.awt.Color.lightGray);
        timer.setFont(boldCustomFont);


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
        gbc.insets = new Insets((int) (frameDimension.getHeight()/11), (int) (frameDimension.getWidth()/27.5), (int) (frameDimension.getHeight()/19.8), (int) (frameDimension.getWidth()/3)); 

        upperPanel.add(title, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/11), 0, (int) (frameDimension.getHeight()/19.8), (int) (frameDimension.getWidth()/15.5));
        
        upperPanel.add(timer, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/4.9), 0, (int) (frameDimension.getHeight()/5.8), (int) (frameDimension.getWidth()/110));

        upperPanel.add(minimizeButtonLabel, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets((int) (frameDimension.getHeight()/4.9), (int) (frameDimension.getWidth()/110), (int) (frameDimension.getHeight()/5.8), (int) (frameDimension.getWidth()/31.43));
        upperPanel.add(exitButtonLabel, gbc);


    }

    private void setLowerPanel() {
        lowerPanel = new JPanel();
        lowerPanel.setPreferredSize(new Dimension((int) frameDimension.getHeight(), (int) (frameDimension.getHeight()/2)));
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new GridBagLayout());

        tempBackLabel = new JLabel("Temporary Back Button");
        tempBackLabel.addMouseListener(this);
        tempBackLabel.setFont(boldCustomFont);

        lowerPanel.add(tempBackLabel);
       

    }

    public void setRemainingTime(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void startTimer() {
        tempBackLabel.setText("TEMPORARY BACK BUTTON");
        timer.setText("Timer: " + timeRemaining);
        
        countdownTimer = new javax.swing.Timer(1000, e -> {
            timeRemaining--; 

            if (timeRemaining >= 0) {
                if(timeRemaining < 10) {
                    timer.setText("Timer: 0" + timeRemaining);
                } else {
                    timer.setText("Timer: " + timeRemaining);
                }
            } else {
                countdownTimer.stop();
                handleTimeUp(); 
            }
        });
        countdownTimer.start();
    }

    private void handleTimeUp() {
        System.out.println("Time is up!");
        tempBackLabel.setText("TIME IS UP! GO BACK NOW!!!");
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
        else if(e.getSource() == tempBackLabel) {
            countdownTimer.stop();
            cardLayout.show(cardPanel, "Game Panel");
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
