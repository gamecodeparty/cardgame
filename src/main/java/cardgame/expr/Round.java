/*
 * Round.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import cardgame.utils.Math2;

public class Round implements Expression {

  private final Expression operand;
  private final Expression precision;

  public Round(Expression operand) {
    this.operand = operand;
    this.precision = new Literal.Number(0d);
  }

  public Round(Expression operand, Expression precision) {
    this.operand = operand;
    this.precision = precision;
  }

  @Override
  public ImmutableValue<Double> evaluate(Context context) {
    Double a = (Double) operand.evaluate(context).get();
    Double b = (Double) precision.evaluate(context).get();
    Double result = Math2.round(a, b.intValue());
    return new ImmutableValue<Double>(result);
  }

}