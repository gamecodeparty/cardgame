/*
 * Abs.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Abs implements Expression {

  private final Expression operand;

  public Abs(Expression operand) {
    this.operand = operand;
  }

  @Override
  public ImmutableValue<Double> evaluate(Context context) {
    Double value = (Double) operand.evaluate(context).get();
    Double result = value < 0 ? -value : value;
    return new ImmutableValue<Double>(result);
  }

}