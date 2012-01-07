/*
 * Print.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Print implements Expression {

  private final Expression[] operands;

  public Print(Expression... operands) {
    if (operands.length < 1)
      throw new RuntimeException("Wrong number of arguments: " + 0);
    this.operands = operands;
  }

  @Override
  public Value evaluate(Context context) {
    Value value = null;
    for (Expression operand : operands) {
      value = operand.evaluate(context);
      System.out.print(value.get());
      System.out.print(" ");
    }
    System.out.println();
    return value;
  }

}