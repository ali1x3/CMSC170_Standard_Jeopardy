package up.tac.Resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

/**
 * Future refactor for loading resources into the app.
 */

public class ResourceManager {
    protected static final String FILES_PATH = "files/";
    protected static final String KNOWLEDGEBASE_PATH = "knowledge_base/";
    private Cursor cursor;
    private HashMap<String, ImageIcon> imageIconHashMap;
    private Image logo;

    private Font cousineRegular, cousineBold, anonymousProBold;
    public ResourceManager() {
        IO.println("Loading resources...");
        loadResources();
        IO.println("Resources loaded successfully!");
    }

    private void loadResources() {
        loadCursor();
        try {
            loadFonts();
        } catch (IOException | FontFormatException e) {
            IO.println("ERROR: Fonts failed to load");
            throw new RuntimeException(e);
        }
        imageIconHashMap = new HashMap<>();
        loadImages();
    }

    private void loadFonts() throws IOException, FontFormatException {
        IO.println("Loading Fonts...");
        cousineRegular = Font.createFont(
            Font.TRUETYPE_FONT,
            getStreamFromFiles("Cousine-Regular.ttf")
        );
        cousineRegular = cousineRegular.deriveFont(Font.PLAIN, 21);
        cousineBold = Font.createFont(
            Font.TRUETYPE_FONT,
            getStreamFromFiles("Cousine-Bold.ttf")
        );
        cousineBold = cousineBold.deriveFont(Font.BOLD, 21);
        anonymousProBold = Font.createFont(
            Font.TRUETYPE_FONT,
            getStreamFromFiles("AnonymousPro-Bold.ttf")
        );
        anonymousProBold = anonymousProBold.deriveFont(Font.BOLD, 50);
        IO.println("Done loading fonts!");
    }

    private void loadImages() {
        IO.println("Loading image icons...");
        logo = Toolkit.getDefaultToolkit().getImage(
            getURLFromFiles("icon.png")
        );

        imageIconHashMap.put(
            "Contact Panel BG",
            new ImageIcon(getURLFromFiles("contactPage_bg.jpg"))
        );

        imageIconHashMap.put(
            "Description Panel BG",
            new ImageIcon(getURLFromFiles("descriptionPage_bg.jpg"))
        );

        imageIconHashMap.put(
            "Content Panel BG",
            new ImageIcon(getURLFromFiles("contentPage_bg.jpg"))
        );
        imageIconHashMap.put(
            "Main Panel BG",
            new ImageIcon(getURLFromFiles("mainPage_bg.jpg"))
        );
        imageIconHashMap.put(
            "Background AI GIF",
            new ImageIcon(getURLFromFiles("bg_img.gif"))
        );

        imageIconHashMap.put(
            "Leaderboards Panel BG",
            new ImageIcon(getURLFromFiles("leaderboards_bg.gif"))
        );
        imageIconHashMap.put(
            "Game Panel BG",
            new ImageIcon(getURLFromFiles("gamePanel_bg.jpg"))
        );
        imageIconHashMap.put(
            "Exit Button",
            new ImageIcon(getURLFromFiles("exitButton.jpg"))
        );
        imageIconHashMap.put(
            "Exit Button Clicked",
            new ImageIcon(getURLFromFiles("exitButton_clicked.jpg"))
        );
        imageIconHashMap.put(
            "Minimize Button",
            new ImageIcon(getURLFromFiles("minimizeButton.jpg"))
        );
        imageIconHashMap.put(
            "Minimize Button Clicked",
            new ImageIcon(getURLFromFiles("minimizeButton_clicked.jpg"))
        );
        imageIconHashMap.put(
            "Start Button",
            new ImageIcon(getURLFromFiles("startButton.jpg"))
        );
        imageIconHashMap.put(
            "Start Button Clicked",
            new ImageIcon(getURLFromFiles("startButton_clicked.jpg"))
        );
        imageIconHashMap.put(
            "End Button Clicked",
            new ImageIcon(getURLFromFiles("endButton_clicked.jpg"))
        );
        imageIconHashMap.put(
            "End Button",
            new ImageIcon(getURLFromFiles("endButton.jpg"))
        );
        imageIconHashMap.put(
            "Back Button",
            new ImageIcon(getURLFromFiles("backButton.jpg"))
        );
        imageIconHashMap.put(
            "Back Button Clicked",
            new ImageIcon(getURLFromFiles("backButton_Clicked.jpg"))
        );
        imageIconHashMap.put(
            "Game Button Wrong",
            new ImageIcon(getURLFromFiles("gameButtonWrong_bg.png"))
        );
        imageIconHashMap.put(
            "Game Button Correct",
            new ImageIcon(getURLFromFiles("gameButtonCorrect_bg.png"))
        );
        IO.println("Image Icons loaded successfully!");
    }

    private void loadCursor() {
        BufferedImage cursorImage;
        URL cursorURL = getURLFromFiles("cursor.png");
        try {
            cursorImage = ImageIO.read(cursorURL);
            cursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImage,
                new Point(0,0), "Custom Cursor"
            );
        }
        catch (IOException e) {
            IO.println("ERROR: Custom Cursor failed to load");
            throw new RuntimeException(e);
        }
    }

    public InputStream getStreamFromFiles(String file) {
        return Objects.requireNonNull(
            getClass().getResourceAsStream(
                "/" + FILES_PATH + file
            )
        );
    }

    public URL getURLFromFiles(String file) {
        return Objects.requireNonNull(
            getClass().getClassLoader().getResource(
                FILES_PATH + file
            )
        );
    }

    public URL getFromKnowledgeBase(String file) {
        URL url = getClass().getClassLoader().getResource(KNOWLEDGEBASE_PATH + file);
        if (url == null) {
            System.err.println("⚠️ Resource not found: " + KNOWLEDGEBASE_PATH + file);
        }
        return url;
    }
 

    public Cursor getCursor() {
        return cursor;
    }

    public Font getAnonymousProBold() {
        return anonymousProBold;
    }

    public Font getCousineBold() {
        return cousineBold;
    }

    public Font getCousineRegular() {
        return cousineRegular;
    }

    public Image getLogo() {
        return logo;
    }

    public ImageIcon getImageIcon(String key) {
        if(imageIconHashMap.get(key) == null){
            throw new RuntimeException("ERROR: Key is not in Image Icon Hashmap!");
        }
        return imageIconHashMap.get(key);
    }
}
