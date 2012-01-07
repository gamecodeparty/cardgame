/*
 * Equals.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Equals implements Expression {

  private final Expression a;
  private final Expression b;

  public Equals(Expression a, Expression b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    Object one = a.evaluate(context).get();
    Object other = b.evaluate(context).get();
    boolean value = (other == null) ? (one == null) : other.equals(one);
    return new ImmutableValue<Boolean>(value);
  }

}