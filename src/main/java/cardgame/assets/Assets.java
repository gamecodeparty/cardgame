/*
 * Assets.java
 * 
 * 05/01/2012
 */
package cardgame.assets;

import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Assets {

  private static final Map<String, Image> images = new HashMap<String, Image>();
  private static final Map<String, Font> fonts = new HashMap<String, Font>();

  public static Image getImage(String key) {
    try {
      Image image = images.get(key);
      if (image == null) {
        String filename = String.format("%s.png", key);
        URL url = Assets.class.getResource(filename);
        image = new ImageIcon(url).getImage();
        images.put(key, image);
      }
      return image;
    } catch (Throwable th) {
      throw new RuntimeException("Exception retrieving image under key: " + key, th);
    }
  }

  public static Font getFont(String key) {
    try {
      if (key == "standard")
        key = "RevueBT";

      Font font = fonts.get(key);
      if (font == null) {
        String filename = String.format("/cardgame/assets/%s.ttf", key);
        URL url = Assets.class.getResource(filename);
        font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
        font = font.deriveFont(12f);
        fonts.put(key, font);
      }
      return font;
    } catch (Throwable th) {
      throw new RuntimeException("Exception retrieving font under key: " + key, th);
    }
  }
}
