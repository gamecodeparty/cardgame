/*
 * Or.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Or implements Expression {

  private final Expression[] operands;

  public Or(Expression... operands) {
    if (operands.length < 2)
      throw new RuntimeException("Wrong number of arguments: " + 0);
    this.operands = operands;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    for (Expression operand : operands) {
      Boolean valid = (Boolean) operand.evaluate(context).get();
      if (valid) {
        return new ImmutableValue<Boolean>(true);
      }
    }
    return new ImmutableValue<Boolean>(false);
  }

}