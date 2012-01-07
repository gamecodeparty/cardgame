/*
 * ValueOf.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class ValueOf implements Expression {

  private final Expression expression;

  public ValueOf(Expression expression) {
    this.expression = expression;
  }

  @Override
  public ImmutableValue evaluate(Context context) {
    return new ImmutableValue(expression.evaluate(context).get());
  }

}