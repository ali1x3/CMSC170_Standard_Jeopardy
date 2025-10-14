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
import java.awt.image.BufferedImage;
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
    private JLabel title, timer, backButtonLabel, minimizeButtonLabel, tempBackLabel, choice1, choice2, choice3, choice4;
    private JLabel correctChoice;
    private Font customFont = new Font("Arial", Font.PLAIN, 21);
    private Font boldCustomFont = new Font("Arial", Font.BOLD, 21);
    private Font titleFont = new Font("Arial", Font.BOLD, 50);
    private ImageIcon backButton, backButtonClicked, minimizeButton, minimizeButtonClicked, choiceButton, choiceClickedButton, choiceCorrectButton, choiceWrongButton;
    private Dimension frameDimension;
    private javax.swing.Timer countdownTimer; 
    private int timeRemaining;
	private ResourceManager resourceManager;
	//private ImageIcon backButton, backButtonClicked;
	private QuestionButton clickedQuestionButton;
    private GamePanel gamePanel; //lord forgive me
    private boolean backEnabled = true;
    private String question, correctAnswer;
    private String[] choices;
	private JTextArea questionArea;
    private BufferedImage robotImage, questionIcon;
    private boolean biggerRobot;

    public QuestionPanel(CardLayout cardLayout, JPanel cardPanel, Dimension frameDimension, ResourceManager resourceManager){
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.frameDimension = frameDimension;
        this.resourceManager = resourceManager;
        
        bg_image = new ImageIcon(getClass().getResource("/files/gamePanel_bg.jpg")).getImage();
        // java.net.URL questionIconUrl = getClass().getResource("/files/questionPanel_icon.png");
        // if (questionIconUrl != null) {
        //     try {
        //         questionIcon = javax.imageio.ImageIO.read(questionIconUrl);
        //     } catch (IOException e) {
        //         questionIcon = null;
        //         System.err.println("Failed to read questionPanel_icon.png: " + e.getMessage());
        //     }
        // } else {
        //     questionIcon = null;
        //     System.err.println("Resource not found: /files/questionPanel_icon.png");
        // }
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

    public void setRobotIMG(BufferedImage robotImage, boolean biggerRobot){
        this.biggerRobot = biggerRobot;
        this.robotImage = robotImage;
    }
    private void getGamePanel() {
    }

    public void initializePanel(QuestionButton questionButton) {
        this.clickedQuestionButton = questionButton;
        initializeQuestion();
        initializeChoices();
    }

    private void initializeQuestion() {
        question = clickedQuestionButton.getJeopardyQuestion().getQuestion();
        // compute available area from the same math used when creating the questionArea
        int preferredW = (int) (frameDimension.getWidth()/1.15) - (int)(frameDimension.getWidth()/210);
        int preferredH = (int) (frameDimension.getHeight()/6.5) - (int)(frameDimension.getHeight()/40);
        int horizPadding = (int)(frameDimension.getWidth()/40);
        int vertTop = (int)(frameDimension.getHeight()/60);
        int vertBottom = (int)(frameDimension.getHeight()/35);
        int availW = Math.max(50, preferredW - horizPadding * 2);
        int availH = Math.max(30, preferredH - (vertTop + vertBottom));

        int chosenSize = computeBestFontSizeForWrappedText(question, titleFont, availW, availH);
        questionArea.setFont(titleFont.deriveFont(Font.BOLD, chosenSize));
        questionArea.setText(question);
    }

    /**
     * Pick the largest font size (between min and start) such that the wrapped text fits within maxWidth/maxHeight.
     */
    private int computeBestFontSizeForWrappedText(String text, Font baseFont, int maxWidth, int maxHeight) {
        if (text == null) text = "";
        int startSize = (int) (frameDimension.getHeight() / 40);
        int minSize = 10;

        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(1,1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();

        // Try sizes from start down to min
        for (int size = startSize; size >= minSize; size--) {
            Font f = baseFont.deriveFont(Font.BOLD, size);
            g2.setFont(f);
            java.awt.FontMetrics fm = g2.getFontMetrics(f);

            // estimate wrapped lines by greedy word-wrapping
            String[] words = text.split("\\s+");
            int lines = 1;
            int curW = 0;
            for (String w : words) {
                if (w.length() == 0) continue;
                int wWidth = fm.stringWidth(w + " ");
                if (curW + wWidth > maxWidth) {
                    lines++;
                    curW = wWidth;
                    if (wWidth > maxWidth) { // single word too long -> will exceed, keep reducing size
                        curW = wWidth; // still count it
                    }
                } else {
                    curW += wWidth;
                }
            }

            int totalHeight = lines * fm.getHeight();
            if (totalHeight <= maxHeight) {
                g2.dispose();
                return size;
            }
        }
        g2.dispose();
        return minSize;
    }


    private void initializeChoices() {
        choices = clickedQuestionButton.getJeopardyQuestion().getOptions();
        correctAnswer = clickedQuestionButton.getJeopardyQuestion().getCorrectAnswer();
        System.out.println(correctAnswer);

        JLabel[] choiceLabels = {choice1, choice2, choice3, choice4};

    int imageWidth = choiceButton.getIconWidth();
    int horizontalPadding = (int) (frameDimension.getWidth() / 80.0);
    // use a reduced fraction of the image width so text wraps inside the button nicely
    int textWidth = Math.max(50, (int) (imageWidth * 0.78) - (horizontalPadding * 2));

        // find longest plain-text choice
        String longest = "";
        if (choices != null) {
            for (String s : choices) if (s != null && s.length() > longest.length()) longest = s;
        }

        if (longest == null) longest = "";

        // start from a reasonable font size and decrease until all choices fit both width and height
        int startSize = (int) (frameDimension.getHeight() / 40);
        int minSize = 10;
        int chosenSize = Math.max(minSize, startSize);

        int iconHeight = choiceButton.getIconHeight();
        int verticalPadding = Math.max(4, (int) (frameDimension.getHeight() / 80.0));
        int textAvailHeight = Math.max(24, iconHeight - (verticalPadding * 2));

        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(1,1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();

        outer: for (int size = startSize; size >= minSize; size--) {
            java.awt.Font testFont = titleFont.deriveFont(Font.BOLD, size);
            g2.setFont(testFont);
            java.awt.FontMetrics fm = g2.getFontMetrics(testFont);

            // check every choice (use empty string if missing)
            for (int i = 0; i < choiceLabels.length; i++) {
                String s = (choices != null && i < choices.length && choices[i] != null) ? choices[i] : "";
                // greedy wrap to count lines
                String[] words = s.split("\\s+");
                int lines = 1;
                int curW = 0;
                for (String w : words) {
                    if (w.length() == 0) continue;
                    int wWidth = fm.stringWidth(w + " ");
                    if (curW + wWidth > textWidth) {
                        lines++;
                        curW = wWidth;
                        if (wWidth > textWidth) {
                            // single word wider than allowed, treat as overflow (will try smaller size)
                            // but continue counting
                        }
                    } else {
                        curW += wWidth;
                    }
                }

                int totalHeight = lines * fm.getHeight();
                if (totalHeight > textAvailHeight) {
                    // this size is too big for this choice, try smaller size
                    continue outer;
                }
            }

            // all choices fit at this size
            chosenSize = size;
            break;
        }
        g2.dispose();

        java.awt.Font finalChoiceFont = titleFont.deriveFont(Font.BOLD, chosenSize);

        this.correctChoice = null;
        for (int i = 0; i < choiceLabels.length; i++) {
            JLabel lbl = choiceLabels[i];
            String choiceText = (choices != null && i < choices.length) ? choices[i] : "";

            // keep using HTML to wrap multi-line, but apply measured font for sizing
            String formattedText = "<html><body style='width: " + textWidth + "px; text-align: center;'>" +
                                    (choiceText == null ? "" : escapeHtml(choiceText)) +
                                   "</body></html>";
            lbl.setText(formattedText);
            lbl.setFont(finalChoiceFont);

            if (choiceText != null && choiceText.equals(correctAnswer)) {
                this.correctChoice = lbl;
            }
            lbl.setIcon(choiceButton);
            lbl.removeMouseListener(this); // ensure no duplicate
            lbl.addMouseListener(this);
        }

        // disable the back button until an answer is processed
        backButtonLabel.setIcon(backButtonClicked);
        backEnabled = false;
        revalidate();
    }

    // minimal HTML-escaping for content inserted into the label HTML
    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
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


        backButton = resourceManager.getImageIcon("Back Button");
        Image backButtonResized = backButton.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        backButton = new ImageIcon(backButtonResized);

        minimizeButton = resourceManager.getImageIcon("Minimize Button");
        Image minimizeButtonResized = minimizeButton.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        minimizeButton = new ImageIcon(minimizeButtonResized);

        backButtonClicked = resourceManager.getImageIcon("Back Button Clicked");
        Image backButtonClickedResized = backButtonClicked.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        backButtonClicked = new ImageIcon(backButtonClickedResized);

        minimizeButtonClicked = resourceManager.getImageIcon("Minimize Button Clicked");
        Image minimizeButtonClickedResized = minimizeButtonClicked.getImage().getScaledInstance((int) frameDimension.getWidth()/25, (int) frameDimension.getWidth()/25, Image.SCALE_DEFAULT);
        minimizeButtonClicked = new ImageIcon(minimizeButtonClickedResized);

        backButtonLabel = new JLabel(backButton);
        backButtonLabel.addMouseListener(this);

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
        upperPanel.add(backButtonLabel, gbc);


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

        // backButton = new ImageIcon(getClass().getResource("/files/endButton.jpg"));
        // Image endButtonImageResized = backButton.getImage().getScaledInstance((int) (frameDimension.getWidth()/7.5), (int) (frameDimension.getHeight()/19), Image.SCALE_DEFAULT);
        // backButton = new ImageIcon(endButtonImageResized);

        // backButtonClicked = new ImageIcon(getClass().getResource("/files/endButton_clicked.jpg"));
        // Image endButtonClickedImageResized = backButtonClicked.getImage().getScaledInstance((int) (frameDimension.getWidth()/7.5), (int) (frameDimension.getHeight()/19), Image.SCALE_AREA_AVERAGING);
        // backButtonClicked = new ImageIcon(endButtonClickedImageResized);

        // backButtonLabel = new JLabel(backButtonClicked);
        // I GOT SUPER lazy here so here's some AI SLOPPPPPPPPPPPPPPP
        // ...
        // Image questionLabelNormalIconResized is available here
        // ...

        questionArea = new JTextArea();
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

        choice2 = new JLabel(choiceButton);
        choice2.setText("Choice 2");
        choice2.setHorizontalTextPosition(JLabel.CENTER);
        choice2.setVerticalTextPosition(JLabel.CENTER);
        choice2.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/40)));

        choice3 = new JLabel(choiceButton);
        choice3.setText("Choice 3");
        choice3.setHorizontalTextPosition(JLabel.CENTER);
        choice3.setVerticalTextPosition(JLabel.CENTER);
        choice3.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/40)));

        choice4 = new JLabel(choiceButton);
        choice4.setText("Choice 4");
        choice4.setHorizontalTextPosition(JLabel.CENTER);
        choice4.setVerticalTextPosition(JLabel.CENTER);
        choice4.setFont(titleFont.deriveFont(Font.BOLD, (int) (frameDimension.getHeight()/40)));
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
        gbc.gridwidth = 4; // Span all 4 columns
        gbc.insets = new Insets((int) (frameDimension.getHeight()/45), 0, (int) (frameDimension.getHeight()/30), 0);; // Give this row the remaining 50% of available vertical space
        gbc.anchor = GridBagConstraints.CENTER; // Anchor it to the top of its cell

        lowerPanel.add(tempBackLabel, gbc);

    }

    public void setRemainingTime(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void startTimer() {
        String module = clickedQuestionButton.getModule();
        module = module.replace("_", " ");
        module = module.replace("m", "M");
        String classificaion = clickedQuestionButton.getClassifcation();
        classificaion = classificaion.replace("/", " ");
        classificaion = classificaion.replace(".csv", "");
        classificaion = classificaion.substring(0, 2).toUpperCase() + classificaion.substring(2);
        String value = "" + clickedQuestionButton.getQuestionValue();
        tempBackLabel.setText(module + classificaion + " for " + value + " points");
        timer.setText("Timer: " + timeRemaining);

        // this timer is so ass but it works --aliba
        countdownTimer = new javax.swing.Timer(1000, e -> { 
            timeRemaining--; 

            if (timeRemaining > 0) {
                if(timeRemaining < 10) {
                    timer.setText("Timer: 0" + timeRemaining);
                } 
                else if (timeRemaining == 10) {
                    timer.setText("Timer: " + timeRemaining);
                    AudioPlayer.play("/files/AI_voice_timer.wav", true);
                }
                else {
                    timer.setText("Timer: " + timeRemaining);
                }
            } else {
                timer.setText("Timer: 00");
                countdownTimer.stop();
                handleTimeUp(); 
                showCorrectAnswer();
                disableChoiceButtons();
                gamePanel = (GamePanel) cardPanel.getComponent(3); 
                gamePanel.clickedButtonWrong();
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
        // enable the back button (allow clicks) and update icon
        backEnabled = true;
        backButtonLabel.setIcon(backButton);
    }


    private void processAnswer(JLabel userChoice) {
        gamePanel = (GamePanel) cardPanel.getComponent(3); 

        if (userChoice == correctChoice){
            showCorrectAnswer();
            AudioPlayer.play("/files/SFX_correct_answer.wav", false);
            gamePanel.onCorrectAnswer();
            gamePanel.clickedButtonCorrect();
        }
        else {
            showCorrectAndWrongAnswer(userChoice);
            AudioPlayer.play("/files/SFX_wrong_answer.wav", false);
            gamePanel.clickedButtonWrong();
        }

        // gamePanel.disableClickedButton(); 

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
    public void paint(Graphics g) {
        // paint the component and its children first
        super.paint(g);

        if (robotImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int drawH, drawW;

            if (!biggerRobot) {
                drawH = (int) frameDimension.getHeight()/11; 
                drawW = (int) ((double) robotImage.getWidth() * drawH / robotImage.getHeight());
            } else {
                drawH = (int) frameDimension.getHeight()/7; 
                drawW = (int) ((double) robotImage.getWidth() * drawH / robotImage.getHeight());
            }
            


            g2d.drawImage(robotImage, (int) (frameDimension.getWidth()/27.5), (int) (frameDimension.getHeight()/6.10833333333), drawW, drawH, this);
            g2d.dispose();
        } else {
            System.out.println(" Robot Image Null");
        }
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
        else if(e.getSource() == backButtonLabel) {
            // only respond if back button is enabled
            if (!backEnabled) return;
            countdownTimer.stop();
            cardLayout.show(cardPanel, "Game Panel");
            AudioPlayer.stop();
            AudioPlayer.playBGM("/files/BGM_game_panel.wav");
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
        JFrame parentFrame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
                if (e.getSource() == choice1) {
                    choice1.setIcon(choiceButton);
                     if (CustomPopupDialog.showConfirm(parentFrame,
                        "Confirmation",
                        "Is this your final answer?")) processAnswer(choice1);
                }
                else if (e.getSource() == choice2) {
                    choice2.setIcon(choiceButton);
                    if (CustomPopupDialog.showConfirm(parentFrame,
                        "Confirmation",
                        "Is this your final answer?")) processAnswer(choice2);
                }
                else if (e.getSource() == choice3) {
                    choice3.setIcon(choiceButton);
                    if (CustomPopupDialog.showConfirm(parentFrame,
                        "Confirmation",
                        "Is this your final answer?")) processAnswer(choice3);
                }
                else if (e.getSource() == choice4) {
                    choice4.setIcon(choiceButton);
                    if (CustomPopupDialog.showConfirm(parentFrame,
                        "Confirmation",
                        "Is this your final answer?")) processAnswer(choice4);
                }

        
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == backButtonLabel) {
            // only respond if back button is enabled
            if (!backEnabled) return;
            backButtonLabel.setIcon(backButtonClicked);
        }
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButtonClicked);
        } 

        if (!(e.getSource() == minimizeButtonLabel || e.getSource() == title)) {
            AudioPlayer.play("/files/SFX_button_1.wav", false);
        } 
        else if (e.getSource() != title) {
            AudioPlayer.play("/files/SFX_button_2.wav", false);
        } else if (e.getSource() == backButtonLabel) {
            if (!backEnabled) return;
            AudioPlayer.play("/files/SFX_button_1.wav", false);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == backButtonLabel) {
            if (!backEnabled) return;
            backButtonLabel.setIcon(backButton);
        } 
        else if (e.getSource() == minimizeButtonLabel) {
            minimizeButtonLabel.setIcon(minimizeButton);
        } 
    }
}
