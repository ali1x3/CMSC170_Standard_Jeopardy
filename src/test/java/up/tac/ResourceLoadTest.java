package up.tac;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import up.tac.Resource.ResourceManager;

import java.awt.*;

public class ResourceLoadTest {
    @Test
    public void TestResourceLoad() {
        assertDoesNotThrow(() -> {
            ResourceManager testload = new ResourceManager();
            Cursor c = testload.getCursor();
        });
    }
}
