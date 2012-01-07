/*
 * While.java
 * 
 * 05/01/2012
 */
package cardgame.expr;


public class While implements Expression {

  private final Expression condition;
  private final Expression body;
  private final Expression result;

  public While(Expression condition, Expression body) {
    this.condition = condition;
    this.body = body;
    this.result = null;
  }

  public While(Expression condition, Expression body, Expression result) {
    this.condition = condition;
    this.body = body;
    this.result = result;
  }
  
  @Override
  public Value evaluate(Context context) {
    
    Value last = null;
    while((Boolean) condition.evaluate(context).get()) {
      last = body.evaluate(context);
    }
    
    if (result != null) return result.evaluate(context);
    if (last != null) return last;
    return new ImmutableValue(null);
  }

}