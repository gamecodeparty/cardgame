/*
 * Mod.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Mod implements Expression {

  private final Expression dividend;
  private final Expression divider;

  public Mod(Expression operand, Expression divider) {
    this.dividend = operand;
    this.divider = divider;
  }

  @Override
  public ImmutableValue<Double> evaluate(Context context) {
    Double a = (Double) dividend.evaluate(context).get();
    Double b = (Double) divider.evaluate(context).get();

    if (b == 0) {
      throw new RuntimeException("Division by zero");
    }

    Double result = a % b;
    return new ImmutableValue<Double>(result);
  }

}