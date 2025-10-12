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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import up.tac.Resource.ResourceManager;


public class QuestionPanel extends JPanel implements MouseListener{
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Image bg_image;
    private JPanel upperPanel, lowerPanel;
    private JLabel title, timer, exitButtonLabel, minimizeButtonLabel, tempBackLabel, choice1, choice2, choice3, choice4;
    private JLabel correctChoice;
    private Font customFont = new Font("Arial", Font.PLAIN, 21);
    private Font boldCustomFont = new Font("Arial", Font.BOLD, 21);
    private Font titleFont = new Font("Arial", Font.BOLD, 50);
    private ImageIcon exitButton, exitButtonClicked, minimizeButton, minimizeButtonClicked, choiceButton, choiceClickedButton, choiceCorrectButton, choiceWrongButton;
    private Dimension frameDimension;
    private javax.swing.Timer countdownTimer; 
    private int timeRemaining;
	private ResourceManager resourceManager;
	private ImageIcon backButton, backButtonClicked;
	private JLabel backButtonLabel;
	private QuestionButton clickedQuestionButton;

    public QuestionPanel(CardLayout cardLayout, JPanel cardPanel, Dimension frameDimension, ResourceManager resourceManager){
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.frameDimension = frameDimension;
        this.resourceManager = resourceManager;
        
        bg_image = new ImageIcon(getClass().getResource("/files/gamePanel_bg.jpg")).getImage();

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

    public void initializePanel(QuestionButton questionButton) {
        this.clickedQuestionButton = questionButton;
        initializeQuestion();
        initializeChoices();
    }

    private void initializeQuestion() {
        // TODO: this should read from the question bank and set the question
    }

    private void initializeChoices() {
        // TODO: this should read from the question bank and set the choices, as well as set JLabel correctChoice to the correct choice
        correctChoice = choice2; // this is temporary for testing purposes
        choice1.setIcon(choiceButton);
        choice2.setIcon(choiceButton);
        choice3.setIcon(choiceButton);
        choice4.setIcon(choiceButton);
        choice1.addMouseListener(this);
        choice2.addMouseListener(this);
        choice3.addMouseListener(this);
        choice4.addMouseListener(this);
        backButtonLabel.setIcon(backButtonClicked);
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

        tempBackLabel = new JLabel("HEURISTIC ALGORITHMS FOR 200");
        tempBackLabel.addMouseListener(this);
        tempBackLabel.setFont(titleFont.deriveFont(Font.BOLD, (int) frameDimension.getHeight()/25));
        tempBackLabel.setForeground(Color.GRAY);

        // TODO: refactor all dis shitt to use resource manager
        ImageIcon questionNormalIcon = new ImageIcon(getClass().getResource("/files/questionIcon.png"));
        Image questionNormalIconResized = questionNormalIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/10), (int) (frameDimension.getHeight()/10), Image.SCALE_DEFAULT);
        
        ImageIcon questionLabelNormalIcon = new ImageIcon(getClass().getResource("/files/questionLabel_bg.png"));
        Image questionLabelNormalIconResized = questionLabelNormalIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/1.12), (int) (frameDimension.getHeight()/6.5), Image.SCALE_DEFAULT);
        
        ImageIcon choiceNormalIcon = new ImageIcon(getClass().getResource("/files/choiceButton_bg.png"));
        Image choiceNormalIconResized = choiceNormalIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/5.15), (int) (frameDimension.getHeight()/2.3), Image.SCALE_DEFAULT);
        choiceButton = new ImageIcon(choiceNormalIconResized);

        ImageIcon choiceRolloverlIcon = new ImageIcon(getClass().getResource("/files/choiceButtonClicked_bg.png"));
        Image choiceRolloverlIconResized = choiceRolloverlIcon.getImage()
        .getScaledInstance(
        (int) (frameDimension.getWidth()/5.15),
        (int) (frameDimension.getHeight()/2.3),
        Image.SCALE_DEFAULT);
        choiceClickedButton = new ImageIcon(choiceRolloverlIconResized);

        ImageIcon choiceCorrectIcon = new ImageIcon(getClass().getResource("/files/choiceButtonCorrect_bg.png"));
        Image choiceCorrectIconResized = choiceCorrectIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/5.15), (int) (frameDimension.getHeight()/2.3), Image.SCALE_DEFAULT);
        choiceCorrectIcon = new ImageIcon(choiceCorrectIconResized);
        choiceCorrectButton = new ImageIcon(choiceCorrectIconResized); // note to myself, use this for green -- aliba

        ImageIcon choiceWrongIcon = new ImageIcon(getClass().getResource("/files/choiceButtonWrong_bg.png"));
        Image choiceWrongIconResized = choiceWrongIcon.getImage().getScaledInstance((int) (frameDimension.getWidth()/5.15), (int) (frameDimension.getHeight()/2.3), Image.SCALE_DEFAULT);
        choiceWrongIcon = new ImageIcon(choiceWrongIconResized);
        choiceWrongButton = new ImageIcon(choiceWrongIconResized); // note to myself, use this for red -- aliba

        backButton = new ImageIcon(getClass().getResource("/files/endButton.jpg"));
        Image endButtonImageResized = backButton.getImage().getScaledInstance((int) (frameDimension.getWidth()/7.5), (int) (frameDimension.getHeight()/19), Image.SCALE_DEFAULT);
        backButton = new ImageIcon(endButtonImageResized);

        backButtonClicked = new ImageIcon(getClass().getResource("/files/endButton_clicked.jpg"));
        Image endButtonClickedImageResized = backButtonClicked.getImage().getScaledInstance((int) (frameDimension.getWidth()/7.5), (int) (frameDimension.getHeight()/19), Image.SCALE_AREA_AVERAGING);
        backButtonClicked = new ImageIcon(endButtonClickedImageResized);

        backButtonLabel = new JLabel(backButtonClicked);
        // I GOT SUPER lazy here so here's some AI SLOPPPPPPPPPPPPPPP
        // ...
        // Image questionLabelNormalIconResized is available here
        // ...

        JTextArea questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setText("The fitness number, f(n), is the cost associated with a node in the A* algorithm. It is calculated as the sum of g(n) (the cost spent reaching the node from the start) and h(n) (the estimated cost of the cheapest path from the node to the goal). Therefore, f(n) = g(n) + h(n).");

        // ⭐️ REMOVE the hardcoded setPreferredSize
        // questionArea.setPreferredSize(new Dimension(800, 100)); // <-- REMOVE THIS

        // Set it to wrap lines (these are correct)
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setForeground(Color.BLACK);

        // Set your custom font (this is correct)
        questionArea.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/40)));
        questionArea.setEditable(false); 
        questionArea.setOpaque(false); // Text area is transparent
        questionArea.setForeground(Color.BLACK);

        // --- Question Area Container Setup ---

        // 1. Create a container for the question text and background image
        JPanel questionAreaContainer = new JPanel(new GridBagLayout()); // Use GridBagLayout for centering
        questionAreaContainer.setOpaque(false); 

        // 2. Create a JLabel to hold the background image
        JLabel questionBgLabel = new JLabel(new ImageIcon(questionLabelNormalIconResized));

        // 3. Set a slight border/padding on the JTextArea to keep text off the image edges
        questionArea.setBorder(BorderFactory.createEmptyBorder(
            (int)(frameDimension.getHeight()/60), // Top padding
            (int)(frameDimension.getWidth()/40),  // Left padding
            (int)(frameDimension.getHeight()/35), // Bottom padding
            (int)(frameDimension.getWidth()/40)   // Right padding
        )); 

        // 4. Set the JTextArea's preferred size to match the inner area of the image label
        // We must set the JTextArea's size relative to the image size for wrapping to occur correctly.
        questionArea.setPreferredSize(new Dimension(
            (int) (frameDimension.getWidth()/1.15) - (int)(frameDimension.getWidth()/210),  // Image width - horizontal padding
            (int) (frameDimension.getHeight()/6.5) - (int)(frameDimension.getHeight()/40) // Image height - vertical padding
        ));


        // 5. Place the JTextArea inside the image label (which uses GridBagLayout now)
        questionBgLabel.setLayout(new GridBagLayout());
        questionBgLabel.add(questionArea, new GridBagConstraints()); // Add with default constraints for centering

        // 6. Add the combined question element (image + text) to the main container
        questionAreaContainer.add(questionBgLabel); // Add the image label to the container


        choice1 = new JLabel(choiceButton);
        choice1.setText("Choice 1");
        choice1.setHorizontalTextPosition(JLabel.CENTER);
        choice1.setVerticalTextPosition(JLabel.CENTER);
        choice1.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/40)));
        choice1.addMouseListener(this);

        choice2 = new JLabel(choiceButton);
        choice2.setText("Choice 2");
        choice2.setHorizontalTextPosition(JLabel.CENTER);
        choice2.setVerticalTextPosition(JLabel.CENTER);
        choice2.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/40)));
        choice2.addMouseListener(this);

        choice3 = new JLabel(choiceButton);
        choice3.setText("Choice 3");
        choice3.setHorizontalTextPosition(JLabel.CENTER);
        choice3.setVerticalTextPosition(JLabel.CENTER);
        choice3.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/40)));
        choice3.addMouseListener(this);

        choice4 = new JLabel(choiceButton);
        choice4.setText("Choice 4");
        choice4.setHorizontalTextPosition(JLabel.CENTER);
        choice4.setVerticalTextPosition(JLabel.CENTER);
        choice4.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/40)));
        choice4.addMouseListener(this);
        // ... (Choice labels created) ...

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(
            (int) (frameDimension.getHeight()/50), // top spacing
            (int) (frameDimension.getWidth()/110), // left spacing
            (int) (frameDimension.getHeight()/50), // bottom spacing
            (int) (frameDimension.getWidth()/110)  // right spacing
        ); 


        // --- 1. Question Area Container (Row 0) ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4; // Span all 4 columns
        gbc.insets = new Insets((int) (frameDimension.getHeight()/25), 0, (int) (frameDimension.getHeight()/30), 0);
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; // Already set above

        lowerPanel.add(questionAreaContainer, gbc); 

        gbc.insets = new Insets(0,0,0,0);
        // --- 2. Choice Buttons (Row 1) ---
        gbc.weighty = 0.0; // Choices don't take extra vertical space
        gbc.gridwidth = 1; // Reset width to 1 for individual buttons
        gbc.gridy = 1;     // Move to the second row
        gbc.insets = new Insets(0, (int) (frameDimension.getWidth()/55), 0, (int) (frameDimension.getWidth()/55));
        // Choice 1
        gbc.gridx = 0;
        lowerPanel.add(choice1, gbc);

        // Choice 2
        gbc.gridx = 1;
        lowerPanel.add(choice2, gbc);

        // Choice 3
        gbc.gridx = 2;
        lowerPanel.add(choice3, gbc);

        // Choice 4
        gbc.gridx = 3;
        lowerPanel.add(choice4, gbc);


        // --- 3. Header/Back Label (Row 2) ---
        gbc.gridx = 0;
        gbc.gridy = 2; // Move to the third row
        gbc.gridwidth = 3; // Span all 4 columns
        gbc.insets = new Insets((int) (frameDimension.getHeight()/45), 0, (int) (frameDimension.getHeight()/30), 0);; // Give this row the remaining 50% of available vertical space
        gbc.anchor = GridBagConstraints.NORTH; // Anchor it to the top of its cell

        lowerPanel.add(tempBackLabel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Anchor it to the top of its cell
        lowerPanel.add(backButtonLabel, gbc);
    }

    public void setRemainingTime(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void startTimer() {
        tempBackLabel.setText("HEURISTIC ALGORITHMS FOR 200");
        timer.setText("Timer: " + timeRemaining);

        // this timer is so ass but it works --aliba
        countdownTimer = new javax.swing.Timer(1000, e -> { 
            timeRemaining--; 

            if (timeRemaining > 0) {
                if(timeRemaining < 10) {
                    timer.setText("Timer: 0" + timeRemaining);
                } else {
                    timer.setText("Timer: " + timeRemaining);
                }
            } else {
                timer.setText("Timer: 00");
                countdownTimer.stop();
                handleTimeUp(); 
                showCorrectAnswer();
                disableChoiceButtons();
                cardLayout.show(cardPanel, "Game Panel");
                AudioPlayer.playBGM("/files/BGM_game_panel.wav");
            }
        });
        countdownTimer.start();
    }

    private void handleTimeUp() {
        System.out.println("Time is up!");
        tempBackLabel.setText("TIME IS UP! GO BACK NOW!!!");
    }

    private void showCorrectAnswer() {
        correctChoice.setIcon(choiceCorrectButton);
    }

    private void showCorrectAndWrongAnswer(JLabel userChoice) {
        correctChoice.setIcon(choiceCorrectButton);
        userChoice.setIcon(choiceWrongButton);
    }

    private void disableChoiceButtons() {
        choice1.removeMouseListener(this);
        choice2.removeMouseListener(this);
        choice3.removeMouseListener(this);
        choice4.removeMouseListener(this);
    }

    private void enableBackButton() {
        backButtonLabel.addMouseListener(this);
        backButtonLabel.setIcon(backButton);
    }

    private void processAnswer(JLabel userChoice) {
        if (userChoice == correctChoice){
            showCorrectAnswer();
            AudioPlayer.play("/files/SFX_correct_answer.wav", false);
        }
        else {
            showCorrectAndWrongAnswer(userChoice);
            AudioPlayer.play("/files/SFX_wrong_answer.wav", false);
        }
        disableChoiceButtons();
        enableBackButton();
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
            AudioPlayer.stop();
        }
        else if (e.getSource() == exitButtonLabel) {
            System.out.println("Exit Button Pressed");
            System.exit(0);
        } 
        else if(e.getSource() == backButtonLabel) {
            countdownTimer.stop();
            cardLayout.show(cardPanel, "Game Panel");
            AudioPlayer.stop();
        }
        
    } 

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == choice1) {
            choice1.setIcon(choiceClickedButton);
        }
        else if (e.getSource() == choice2) {
            choice2.setIcon(choiceClickedButton);
        }
        else if (e.getSource() == choice3) {
            choice3.setIcon(choiceClickedButton);
        }
        else if (e.getSource() == choice4) {
            choice4.setIcon(choiceClickedButton);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == choice1) {
            choice1.setIcon(choiceButton);
            processAnswer(choice1);
        }
        else if (e.getSource() == choice2) {
            choice2.setIcon(choiceButton);
            processAnswer(choice2);
        }
        else if (e.getSource() == choice3) {
            choice3.setIcon(choiceButton);
            processAnswer(choice3);
        }
        else if (e.getSource() == choice4) {
            choice4.setIcon(choiceButton);
            processAnswer(choice4);
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == exitButtonLabel) {
            exitButtonLabel.setIcon(exitButtonClicked);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButtonClicked);
        } 

        if (!(e.getSource() == exitButtonLabel || e.getSource() == minimizeButtonLabel || e.getSource() == exitButtonLabel || e.getSource() == title)) {
            AudioPlayer.play("/files/SFX_button_1.wav", false);
        } 
        else if (e.getSource() != title) {
            AudioPlayer.play("/files/SFX_button_2.wav", false);
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
