/*
 * Peek.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class Peek implements Expression {

  private final Expression stack;

  public Peek(Expression stack) {
    this.stack = stack;
  }

  @Override
  public Value evaluate(Context context) {
    Object target = (Collection) stack.evaluate(context).get();
    Object value = null;
    if (target instanceof Queue) {
      Queue queue = (Queue) target;
      value = queue.peek();
    } else if (target instanceof List) {
      List list = (List) target;
      value = list.get(list.size() - 1);
    } else {
      throw new RuntimeException("Invalid target: " + target);
    }
    return new ImmutableValue(value);
  }

}