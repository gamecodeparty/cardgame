/*
 * PileBase.java
 * 
 * 05/01/2012
 */
package cardgame.model;

import java.util.Iterator;
import java.util.Stack;

public abstract class PileBase<T> implements Iterable<T> {

  private final Stack<T> stack;
  
  public PileBase() {
    stack = new Stack<T>();  
  }
  
  protected Stack<T> getStack() {
    return stack;
  }
  
  public boolean isEmpty() {
    return stack.isEmpty();
  }
  
  public T peek() {
    return stack.peek();
  }
  
  public T pop() 
  {
    return stack.pop();
  }
  
  public void push(T item) {
    stack.push(item);
  }
  
  public void clear() {
    stack.clear();
  }
  
  public Iterator<T> iterator() {
    return stack.iterator();
  }
}
