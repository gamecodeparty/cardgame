/*
 * BackgroundRenderer.java
 * 
 * 05/01/2012
 */
package cardgame.engine;

import java.awt.Graphics2D;
import java.awt.Image;

import cardgame.assets.Assets;

public class BackgroundRenderer implements Renderer {

  private final Image background;

  public BackgroundRenderer() {
    this.background = Assets.getImage("background");
  }

  public void render(Graphics2D g, int w, int h) {
    g.drawImage(background, 0, 0, w, h, 0, 0,
        background.getWidth(null), background.getHeight(null), null);
    g.dispose();
  }
}
