/*
 * Pow.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Pow implements Expression {

  private final Expression operand;
  private final Expression power;

  public Pow(Expression operand, Expression power) {
    this.operand = operand;
    this.power = power;
  }

  @Override
  public ImmutableValue<Double> evaluate(Context context) {
    Double value = (Double) operand.evaluate(context).get();
    Double raise = (Double) power.evaluate(context).get();
    Double result = Math.pow(value, raise);
    return new ImmutableValue<Double>(result);
  }

}