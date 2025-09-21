package up.tac;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class MainFrameTest {
    @Test
    public void testMainFrame(){
        MainFrame mainFrame = new MainFrame();
        assertTrue(mainFrame.isVisible());
        assertTrue(mainFrame.isShowing());
    }
}
