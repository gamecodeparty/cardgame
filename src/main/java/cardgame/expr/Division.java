/*
 * Division.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Division implements Expression {

  private final Expression[] operands;

  public Division(Expression... operands) {
    if (operands.length < 2)
      throw new RuntimeException("Wrong number of arguments: " + 0);
    this.operands = operands;
  }

  @Override
  public ImmutableValue<Double> evaluate(Context context) {
    Double value = (Double) operands[0].evaluate(context).get();
    for (int i = 1; i < operands.length; i++) {
      double operator = (Double) operands[i].evaluate(context).get();
      if (operator == 0) {
        throw new RuntimeException("Division by zero");
      }
      value /= operator;
    }
    return new ImmutableValue<Double>(value);
  }

}