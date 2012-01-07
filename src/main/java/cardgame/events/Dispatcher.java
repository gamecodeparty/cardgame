/*
 * Dispatcher.java
 * 
 * 05/01/2012
 */
package cardgame.events;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class Dispatcher implements Listener {

  private List<Handler> handlers = new ArrayList<Handler>();

  private Dispatcher() {
  }
  
  public void listen(Listener listener) {
    handlers.add(new Handler(listener));
  }
  
  public void listen(String eventId, Listener listener) {
    handlers.add(new Handler(eventId, listener));
  }
  
  public void remove(Listener listener) {
    Iterator<Handler> it = handlers.iterator();
    while (it.hasNext()) {
      Handler handler = it.next();
      if (handler.equals(listener)) {
        it.remove();
      }
    }
  }
  
  public void remove(String eventId, Listener listener) {
    Iterator<Handler> it = handlers.iterator();
    while (it.hasNext()) {
      Handler handler = it.next();
      if (handler.equals(listener) && handler.equals(eventId)) {
        it.remove();
      }
    }
  }

  public void dispatchEvent(final Event event) {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        for (Handler handler : handlers) {
          if (handler.isListening(event)) {
            handler.getListener().onEvent(event);
          }
        }
      }
    };
    dispatch(runnable);
  }

  protected abstract void dispatch(Runnable runnable);

  @Override
  public void onEvent(Event event) {
    dispatchEvent(event);
  }

  private class Handler {
    
    private final boolean any;
    private final String eventId;
    private final Listener listener;
    
    public Handler(Listener listener) {
      this.any = true;
      this.eventId = "";
      this.listener = listener;
    }
    
    public Handler(String eventId, Listener listener) {
      this.any = false;
      this.eventId = eventId;
      this.listener = listener;
    }
    
    public Listener getListener() {
      return listener;
    }
    
    public boolean isListening(Event event) {
      return any || event.getId().equals(eventId);
    }
    
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Listener) return obj.equals(listener);
      if (obj instanceof String) return obj.equals(eventId);
      return super.equals(obj);
    }
  }

  public static class Synchronous extends Dispatcher {

    @Override
    public void dispatch(Runnable runnable) {
      try {
        EventQueue.invokeAndWait(runnable);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  public static class Asynchronous extends Dispatcher {

    @Override
    public void dispatch(Runnable runnable) {
      EventQueue.invokeLater(runnable);
    }
  }
}
