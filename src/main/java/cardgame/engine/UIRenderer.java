/*
 * UIRenderer.java
 * 
 * 05/01/2012
 */
package cardgame.engine;

import java.awt.Color;
import java.awt.Graphics2D;

import cardgame.assets.Assets;

public class UIRenderer implements Renderer {

  private boolean fpsEnabled;
  private long start;
  private int count;
  private double fps;

  public UIRenderer() {
  }

  public boolean isFpsEnabled() {
    return fpsEnabled;
  }

  public void setFpsEnabled(boolean value) {
    this.fpsEnabled = value;
  }

  public void render(Graphics2D g, int w, int h) {
    if (isFpsEnabled()) {
      renderFPS(g);
    }
  }

  private void renderFPS(Graphics2D g) {
    if ((++count & 15) == 0)
    {
      long time = System.currentTimeMillis() - start;
      long frameCost = time / count;
      
      count = 0;
      fps = 1000d / frameCost;
      start = System.currentTimeMillis();
    }
    
    g.setColor(Color.WHITE);
    g.setFont(Assets.getFont("standard").deriveFont(14f));
    g.drawString(String.format("%.0f FPS", fps), 10, 20);
  }
}
