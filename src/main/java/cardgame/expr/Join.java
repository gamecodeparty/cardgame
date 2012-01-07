/*
 * Join.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.ArrayList;
import java.util.Collection;

public class Join implements Expression {

  private final Expression[] operands;

  public Join(Expression... operands) {
    this.operands = operands;
  }

  @Override
  public ImmutableValue<Collection> evaluate(Context context) {
    Collection list = new ArrayList(operands.length);
    for (Expression operand : operands) {
      list.add(operand.evaluate(context).get());
    }
    return new ImmutableValue<Collection>(list);
  }

}