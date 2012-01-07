/*
 * With.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class With implements Expression {

  private final Expression variable;
  private final Expression value;
  private final Expression body;

  public With(Expression variable, Expression value, Expression body) {
    this.variable = variable;
    this.value = value;
    this.body = body;
  }

  @Override
  public Value evaluate(Context context) {
    
    Value target = variable.evaluate(context);
    Object savedValue = target.get();
    
    target.set(value.evaluate(context).get());
    Value result = body.evaluate(context);
    
    target.set(savedValue);
    
    return result;
  }

}