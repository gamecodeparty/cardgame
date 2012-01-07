/*
 * Engine.java
 * 
 * 05/01/2012
 */
package cardgame.engine;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import cardgame.model.Model;

public class Engine extends Canvas implements Runnable {

  private final List<Renderer> renderers;

  private Model model;

  private Thread thread;
  private long frameRate = 30L;

  public Engine() {
    this.renderers = new ArrayList<Renderer>();
    this.renderers.add(new BackgroundRenderer());
    this.renderers.add(new StateRenderer());
    this.renderers.add(new UIRenderer());
  }

  public Model getState() {
    return model;
  }

  public void setState(Model value) {
    this.model = value;
    getRenderer(StateRenderer.class).setState(value);
  }

  public long getFramesRate() {
    return frameRate;
  }

  public void setFramesRate(long value) {
    this.frameRate = value;
  }

  public List<Renderer> getRenderers() {
    return renderers;
  }

  @SuppressWarnings("unchecked")
  public <T extends Renderer> T getRenderer(Class<T> type) {
    for (Renderer renderer : renderers) {
      if (renderer.getClass().equals(type)) {
        return (T) renderer;
      }
    }
    return null;
  }

  public void start() {
    if (thread == null) {
      thread = new Thread(this, "Engine");
      thread.setPriority(Thread.MIN_PRIORITY);
      thread.start();
    }
  }

  public synchronized void stop() {
    if (thread != null) {
      thread.interrupt();
    }
    thread = null;
    notifyAll();
  }

  public void run() {
    Thread current = Thread.currentThread();

    while (thread == current && !isShowing() || getSize().width == 0) {
      try {
        Thread.sleep(200L);
      } catch (InterruptedException e) {
      }
    }

    long frameCost = 1000L / frameRate;
    while (thread == current) {
      repaint();
      try {
        long checkpoint = System.currentTimeMillis();

        long waitTime = checkpoint + frameCost - System.currentTimeMillis();
        if (waitTime > 0) {
          Thread.sleep(waitTime);
        }
      } catch (InterruptedException e) {
      }
    }
    thread = null;
  }

  @Override
  public void render(Graphics2D g, int w, int h) {
    for (Renderer renderer : getRenderers()) {
      renderer.render((Graphics2D) g.create(), w, h);
    }
  }

}