/*
 * And.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class And implements Expression {

  private final Expression[] operands;

  public And(Expression... operands) {
    if (operands.length < 2)
      throw new RuntimeException("Wrong number of arguments: " + 0);
    this.operands = operands;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    for (Expression operand : operands) {
      Boolean valid = (Boolean) operand.evaluate(context).get();
      if (!valid) {
        return new ImmutableValue<Boolean>(false);
      }
    }
    return new ImmutableValue<Boolean>(true);
  }

}