package up.tac;

import javax.swing.*;

import up.tac.Resource.ResourceManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CustomPopupDialog extends JDialog {
    private JLabel messageLabel;
    private JButton confirmButton;
    private JButton noButton;
    private boolean confirmed = false;

    /**
     * Construct the dialog. Use {@link #showDialog()} to display and get result.
     * @param parent parent frame, may be null
     * @param title dialog title
     * @param message html or plain text message
     */
    public CustomPopupDialog(Frame parent, String title, String message) {
        super(parent, title, true);
        initUI(parent, message);
    }

    private void initUI(Frame parent, String message) {
        setLayout(new GridBagLayout());
        ResourceManager resourceManager = new ResourceManager();

        GridBagConstraints gbc = new GridBagConstraints();

        messageLabel = new JLabel(message, SwingConstants.CENTER);
        float resizing = 25.0f/message.length();
        System.out.println("Message Length: " + message.length() + " Resizing Factor: " + 25/message.length() + " New Font Size: " + (int) (18*resizing));
        if(message.length() < 25) {
            gbc.insets = new Insets(5, 0, 5, 0);
            messageLabel.setFont(resourceManager.getCousineBold().deriveFont(Font.PLAIN, 18));
        } else {
            gbc.insets = new Insets((int) (5 + Math.abs((resizing-1)*12)), 0, (int) (5 + Math.abs((resizing-1)*12)), 0);
            messageLabel.setFont(resourceManager.getCousineBold().deriveFont(Font.PLAIN, (int) (20*resizing)));
        }
        
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(messageLabel, gbc);

        confirmButton = new JButton("Confirm");
        confirmButton.setFont(resourceManager.getCousineRegular().deriveFont(Font.PLAIN, 15));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            confirmed = true;
            dispose();
            }
        });

        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setVerticalAlignment(SwingConstants.CENTER);

        noButton = new JButton("No");
        noButton.setFont(resourceManager.getCousineRegular().deriveFont(Font.PLAIN, 15));
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        buttonPanel.add(confirmButton);
        buttonPanel.add(noButton);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // allow ESC to cancel (equivalent to No)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    confirmed = false;
                    dispose();
                }
            }
        });
        setFocusable(true);
        setBackground(Color.lightGray);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(380, 140);
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    /**
     * Show dialog modally and return true if Confirm was clicked, false otherwise.
     */
    public boolean showDialog() {
        confirmed = false; // reset
        setVisible(true); // blocks
        return confirmed;
    }

    /**
     * Static convenience method.
     * @param parent parent frame (may be null)
     * @param title dialog title
     * @param message message (HTML allowed)
     * @return true if Confirm clicked, false otherwise
     */
    public static boolean showConfirm(Frame parent, String title, String message) {
        CustomPopupDialog d = new CustomPopupDialog(parent, title, message);
        return d.showDialog();
    }
}
