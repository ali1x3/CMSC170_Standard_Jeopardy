package up.tac;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.UIManager;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
          try {
            // Option 1: Light theme
            FlatLightLaf.setup();

            // Option 2: Dark theme
            // UIManager.setLookAndFeel(new FlatDarkLaf());

            // Option 3: IntelliJ themes (FlatLaf IntelliJ extension needed)
            // UIManager.setLookAndFeel(new FlatDarculaLaf());

        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        javax.swing.SwingUtilities.invokeLater(MainFrame::new);
        System.out.println( "Hello World!" );
    }
    
    public int add(int x, int y) {
        return x + y;
    }
}
