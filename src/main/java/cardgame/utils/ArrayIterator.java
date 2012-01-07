/*
 * ArrayIterator.java
 * 
 * 05/01/2012
 */
package cardgame.utils;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {

  private final T[] array;
  private int current;

  public ArrayIterator(T... array) {
    this.array = array;
  }

  @Override
  public boolean hasNext() {
    return current < array.length;
  }

  @Override
  public T next() {
    return array[current++];
  }

  @Override
  public void remove() {
    throw new RuntimeException("Remove operation not supported!");
  }
}
