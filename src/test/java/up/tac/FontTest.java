package up.tac;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FontTest {

  @Test
  public void testFontEnvironment() {
    assertDoesNotThrow(() -> {
      Font font = new Font("SansSerif", Font.PLAIN, 12);
      font.canDisplayUpTo("Hello");
    });
  }
}
