/*
 * Pop.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class Pop implements Expression {

  private final Expression stack;

  public Pop(Expression stack) {
    this.stack = stack;
  }

  @Override
  public Value evaluate(Context context) {
    Object target = (Collection) stack.evaluate(context).get();
    Object value = null;
    if (target instanceof Queue) {
      Queue queue = (Queue) target;
      value = queue.poll();
    } else if (target instanceof List) {
      List list = (List) target;
      value = list.remove(list.size() - 1);
    } else {
      throw new RuntimeException("Invalid target: " + target);
    }
    return new ImmutableValue(value);
  }

}