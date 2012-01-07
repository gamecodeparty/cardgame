/*
 * GreaterOrEquals.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class GreaterOrEquals implements Expression {

  private final Expression a;
  private final Expression b;

  public GreaterOrEquals(Expression a, Expression b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    Comparable one = (Comparable) a.evaluate(context).get();
    Comparable other = (Comparable) b.evaluate(context).get();
    boolean value = one.compareTo(other) >= 0;
    return new ImmutableValue<Boolean>(value);
  }

}