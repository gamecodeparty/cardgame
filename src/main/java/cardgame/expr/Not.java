/*
 * Not.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Not implements Expression {

  private final Expression expression;

  public Not(Expression expression) {
    this.expression = expression;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    Boolean value = (Boolean) expression.evaluate(context).get();
    return new ImmutableValue<Boolean>(!value);
  }

}