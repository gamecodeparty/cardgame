/*
 * ImmutableValue.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class ImmutableValue<T> implements Value {

  private final T value;

  public ImmutableValue(T value) {
    this.value = value;
  }

  public T get() {
    return value;
  }

  public void set(Object other) {
    throw new RuntimeException("Literal '" + value + "' cannot be modified");
  }
}
