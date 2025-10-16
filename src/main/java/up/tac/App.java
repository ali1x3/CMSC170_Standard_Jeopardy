package up.tac;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import up.tac.controller.GameController;

/**
 * Main entry point for the AI Jeopardy application.
 */
public class App {

    public static void main(String[] args) {
        // Set up the FlatLaf look and feel for a modern UI
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF: " + ex.getMessage());
        }

        // Schedule the GUI creation and display for the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(GameController::new);
    }

    /**
     * A simple test method.
     * @param x an integer
     * @param y an integer
     * @return the sum of x and y
     */
    public int add(int x, int y) {
        return x + y;
    }
}
