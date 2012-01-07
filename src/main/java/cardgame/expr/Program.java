/*
 * Program.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Program implements Expression {

  private final Expression[] expressions;

  public Program(Expression... expressions) {
    this.expressions = expressions;
  }

  @Override
  public Value evaluate(Context context) {
    Value result = null;
    for (Expression expression : expressions) {
      result = expression.evaluate(context);
    }
    return result;
  }

}