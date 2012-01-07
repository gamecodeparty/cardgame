/*
 * Concat.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.math.BigDecimal;

public class Concat implements Expression {

  private final Expression[] operands;

  public Concat(Expression... operands) {
    if (operands.length < 2)
      throw new RuntimeException("Wrong number of arguments: " + 0);
    this.operands = operands;
  }

  @Override
  public ImmutableValue<String> evaluate(Context context) {
    StringBuilder builder = new StringBuilder();
    for (Expression operand : operands) {
      Object value = operand.evaluate(context).get();
      if (value instanceof Double) {
        BigDecimal number = new BigDecimal(value.toString());
        number = number.setScale(4, BigDecimal.ROUND_FLOOR);
        value = number.compareTo(BigDecimal.ZERO) == 0
            ? value = "0" : number.stripTrailingZeros();
      }
      builder.append(value);
    }
    return new ImmutableValue<String>(builder.toString());
  }

}