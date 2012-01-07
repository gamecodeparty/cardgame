/*
 * IfElse.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class IfElse implements Expression {

  private final Expression condition;
  private final Expression trueCase;
  private final Expression elseCase;

  public IfElse(Expression condition, Expression trueCase, Expression elseCase) {
    this.condition = condition;
    this.trueCase = trueCase;
    this.elseCase = elseCase;
  }

  @Override
  public Value evaluate(Context context) {
    Boolean success = (Boolean) condition.evaluate(context).get();
    return success ? trueCase.evaluate(context) : elseCase.evaluate(context);
  }

}