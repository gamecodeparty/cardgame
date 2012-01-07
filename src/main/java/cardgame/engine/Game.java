/*
 * Game.java
 * 
 * 05/01/2012
 */
package cardgame.engine;

import java.util.HashMap;
import java.util.Map;

import cardgame.model.ModelSampler;

public class Game {

  private static final Map<Object, Game> instances = new HashMap<Object, Game>();
  
  private final Engine engine;

  private Game() {
    engine = new Engine();
    engine.setEnhancedGraphicsEnabled(true);
    engine.setState(ModelSampler.sampleSpiderState());
//    engine.setFramesRate(60L);
  }

  public static Game getInstance(Object id) {
    synchronized (instances) {
      Game instance = instances.get(id);
      if (instance == null) {
        instance = new Game();
        instances.put(id, instance);
      }
      return instance;
    }
  }
  
  public String getTitle() {
    return "Any Card Game";
  }
  
  public Engine getEngine() {
    return engine;
  }
}
