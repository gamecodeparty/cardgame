/*
 * Event.java
 * 
 * 05/01/2012
 */
package cardgame.events;

public class Event {

  private final String id;
  private final Object source;

  public Event(String id, Object source) {
    this.id = id;
    this.source = source;
  }

  public String getId() {
    return id;
  }

  public Object getSource() {
    return source;
  }

}
