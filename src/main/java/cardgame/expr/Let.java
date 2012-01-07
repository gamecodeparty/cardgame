/*
 * Let.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Let implements Expression {

  private final Expression variable;
  private final Expression value;

  public Let(Expression variable, Expression value) {
    this.variable = variable;
    this.value = value;
  }

  @Override
  public Value evaluate(Context context) {
    Value target = variable.evaluate(context);
    target.set(value.evaluate(context).get());
    return target;
  }

}