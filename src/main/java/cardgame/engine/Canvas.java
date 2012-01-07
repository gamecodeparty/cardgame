/*
 * Canvas.java
 * 
 * 03/01/2012
 */
package cardgame.engine;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_RENDERING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_RENDER_QUALITY;
import static java.awt.RenderingHints.VALUE_RENDER_SPEED;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public abstract class Canvas extends JPanel {

  private final Toolkit toolkit;

  private boolean enhancedGraphicsEnabled;

  public Canvas() {
    toolkit = Toolkit.getDefaultToolkit();
    setDoubleBuffered(true);
  }

  public abstract void render(Graphics2D g, int w, int h);

  public boolean isEnhancedGraphicsEnabled() {
    return enhancedGraphicsEnabled;
  }

  public void setEnhancedGraphicsEnabled(boolean enhancedGraphics) {
    this.enhancedGraphicsEnabled = enhancedGraphics;
  }

  private BufferedImage createBuffer(int width, int height) {
    BufferedImage buffer = new BufferedImage(width, height, TYPE_INT_ARGB);
    return buffer;
  }

  private Graphics2D createGraphics(BufferedImage buffer, int width, int height) {
    Graphics2D g2d = buffer.createGraphics();
    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, width, height);
    if (isEnhancedGraphicsEnabled()) {
      g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
      g2d.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY);
    } else if (isEnhancedGraphicsEnabled()) {
      g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
      g2d.setRenderingHint(KEY_RENDERING, VALUE_RENDER_SPEED);
    }
    return g2d;
  }

  public void paint(Graphics g) {
    super.paint(g);

    int w = getWidth();
    int h = getHeight();

    BufferedImage buffer = createBuffer(w, h);
    Graphics2D g2d = createGraphics(buffer, w, h);
    render(g2d, w, h);
    g2d.dispose();

    g.drawImage(buffer, 0, 0, null);
    toolkit.sync();
  }

}