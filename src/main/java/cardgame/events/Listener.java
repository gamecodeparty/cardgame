/*
 * Listener.java
 * 
 * 05/01/2012
 */
package cardgame.events;

public interface Listener<E extends Event> {

  void onEvent(E event);
}
