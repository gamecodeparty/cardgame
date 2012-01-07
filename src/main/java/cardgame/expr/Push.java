/*
 * Push.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.Collection;

public class Push implements Expression {

  private final Expression value;
  private final Expression stack;

  public Push(Expression value, Expression stack) {
    this.value = value;
    this.stack = stack;
  }

  @Override
  public Value evaluate(Context context) {
    Value item = value.evaluate(context);

    Collection target = (Collection) stack.evaluate(context).get();
    target.add(item.get());

    return item;
  }

}