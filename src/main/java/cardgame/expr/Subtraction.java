/*
 * Subtraction.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Subtraction implements Expression {

  private final Expression[] operands;

  public Subtraction(Expression... operands) {
    if (operands.length < 2)
      throw new RuntimeException("Wrong number of arguments: " + 0);
    this.operands = operands;
  }

  @Override
  public ImmutableValue<Double> evaluate(Context context) {
    Double value = (Double) operands[0].evaluate(context).get();
    for (int i = 1; i < operands.length; i++) {
      value -= (Double) operands[i].evaluate(context).get();
    }
    return new ImmutableValue<Double>(value);
  }

}