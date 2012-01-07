/*
 * ApplicationEvent.java
 * 
 * 05/01/2012
 */
package cardgame;

import cardgame.events.Event;

public class ApplicationEvent extends Event {

  public static final String STATE_CHANGE = "stateChange";

  public ApplicationEvent(String id, Application source) {
    super(id, source);
  }
  
  @Override
  public Application getSource() {
    return (Application) super.getSource();
  }
}
