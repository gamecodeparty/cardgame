/*
 * StateRenderer.java
 * 
 * 05/01/2012
 */
package cardgame.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import cardgame.assets.Assets;
import cardgame.model.Card;
import cardgame.model.Pile;
import cardgame.model.Model;
import cardgame.utils.Vector2D;

public class StateRenderer implements Renderer {

  private Model model;
  private Rectangle2D bounds;
  
  private final double cardWidth = 1d;
  private final double cardHeight = 1.4;
  private final double marginWidth = cardWidth;
  private final double marginHeight = cardHeight;
  
  private class Context
  {
    Graphics2D g;
    double w;
    double h;
    double scale;
  }
  
  public void setState(Model model) {
    this.model = model;
    this.bounds = null;
  }

  public Model getState() {
    return model;
  }
  
  public Rectangle2D getBounds() {
    if (bounds == null && getState() != null) {
      double minx = Double.MAX_VALUE;
      double miny = Double.MAX_VALUE;
      double maxx = 0;
      double maxy = 0;
      
      for (Pile pile : getState().getPiles().values()) {
        if (pile.getX() < minx) minx = pile.getX();
        if (pile.getY() < miny) miny = pile.getY();
        if (pile.getX() > maxx) maxx = pile.getX();
        if (pile.getY() > maxy) maxy = pile.getY();
      }
      
      bounds = new Rectangle2D.Double(minx, miny, maxx - minx, maxy - miny);
    }
    return bounds;
  }

  public void render(Graphics2D g, int w, int h) {
    if (getState() == null) {
      return;
    }
    
    // obtendo parametros
    Context ctx = new Context();
    ctx.g = g;
    ctx.w = w;
    ctx.h = h;
    ctx.scale = computeScale(getBounds(), w, h);
    
    centerGraphics(ctx);
    renderPiles(ctx);
  }
  
  private void centerGraphics(Context ctx) {
    Rectangle2D bounds = getBounds();
    Rectangle2D area = new Rectangle2D.Double(
        bounds.getX() * ctx.scale,
        bounds.getY() * ctx.scale,
        bounds.getWidth() * ctx.scale,
        bounds.getHeight() * ctx.scale);
    double xOffset = (ctx.w - area.getMaxX()) / 2;
    double yOffset = (ctx.h - area.getMaxY()) / 2;
    ctx.g.translate(xOffset, yOffset);
  }
  
  private void renderPiles(Context ctx) {
    for (Pile pile : getState().getPiles().values()) {
      if (pile.isEmpty()) {
        renderCell(ctx, pile.getX(), pile.getY());
      } else if (pile.getBehaviour().equals(Pile.SQUARED)) {
        renderSquaredPile(ctx, pile);
      } else {
        renderFannedPile(ctx, pile);
      }
    }
  }
  
  private void renderSquaredPile(Context ctx, Pile pile) {
    renderCard(ctx, pile.peek(), pile.getX(), pile.getY());
  }
  
  private void renderFannedPile(Context ctx, Pile pile) {
    Vector2D largeOffset = pile.getOrientation().multiply(0.30);
    Vector2D smallOffset = pile.getOrientation().multiply(0.08);

    Vector2D location = pile.getLocation();
    for (Card card : pile) {
      renderCard(ctx, card, location.getX(), location.getY());
      location = location.add(card.isFacedUp() ? largeOffset : smallOffset);
    }
  }
  
  private void renderCell(Context ctx, double x, double y) {
    x -= cardWidth / 2;
    y -= cardHeight / 2;
    ctx.g.setColor(new Color(0x20ffffff, true));
    ctx.g.fillRoundRect(
        (int) (x * ctx.scale),
        (int) (y * ctx.scale),
        (int) (cardWidth * ctx.scale),
        (int) (cardHeight * ctx.scale),
        (int) (0.1 * ctx.scale),
        (int) (0.1 * ctx.scale));
    ctx.g.setColor(Color.GREEN.darker().darker());
    ctx.g.drawRoundRect(
        (int) (x * ctx.scale),
        (int) (y * ctx.scale),
        (int) (cardWidth * ctx.scale),
        (int) (cardHeight * ctx.scale),
        (int) (0.1 * ctx.scale),
        (int) (0.1 * ctx.scale));
  }
  
  private void renderCard(Context ctx, Card card, double x, double y) {
    x -= cardWidth / 2;
    y -= cardHeight / 2;
    
    String key = "back";
    if (card.isFacedUp()) {
      key = String.format("%s-%s",
          card.getSuit().name().toLowerCase(), card.getValue().toString());
    }
    Image image = Assets.getImage(key);
    
    ctx.g.setColor(card.isFacedUp() ? Color.WHITE : new Color(0xAFC6E9));
    ctx.g.fillRoundRect(
        (int) (x * ctx.scale),
        (int) (y * ctx.scale),
        (int) (cardWidth * ctx.scale),
        (int) (cardHeight * ctx.scale),
        (int) (0.1 * ctx.scale),
        (int) (0.1 * ctx.scale));
    
    ctx.g.drawImage(image, 
        (int) (x * ctx.scale),
        (int) (y * ctx.scale),
        (int) (cardWidth * ctx.scale),
        (int) (cardHeight * ctx.scale),
        null);
    
    ctx.g.setColor(Color.BLACK);
    ctx.g.drawRoundRect(
        (int) (x * ctx.scale),
        (int) (y * ctx.scale),
        (int) (cardWidth * ctx.scale),
        (int) (cardHeight * ctx.scale),
        (int) (0.1 * ctx.scale),
        (int) (0.1 * ctx.scale));
  }
  
  private double computeScale(Rectangle2D rect, double w, double h) {
    double wMargin = marginWidth * 2;
    double hMargin = marginHeight * 2;
    
    double wRect = rect.getWidth() + wMargin;
    double hRect = rect.getHeight() + hMargin;
    
    double wScale = (w / wRect);
    double hScale = (h / hRect);
    
    return wScale < hScale ? wScale : hScale;
  }
}
