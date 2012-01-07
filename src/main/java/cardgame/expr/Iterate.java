/*
 * Iterate.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.Iterator;

public class Iterate implements Expression {

  private final Expression variable;
  private final Expression list;
  private final Expression body;
  private final Expression result;

  public Iterate(Expression variable, Expression list, Expression body) {
    this.variable = variable;
    this.list = list;
    this.body = body;
    this.result = null;
  }
  
  public Iterate(Expression variable, Expression list, Expression body,
      Expression result) {
    this.variable = variable;
    this.list = list;
    this.body = body;
    this.result = result;
  }
  
  @Override
  public Value evaluate(Context context) {
    Value last = null;
    
    Object object = list.evaluate(context).get();
    Iterator it = (object instanceof Iterable)
        ? ((Iterable) object).iterator() : (Iterator) object;
    Value holder = variable.evaluate(context);
    while (it.hasNext()) {
      holder.set(it.next());
      last = body.evaluate(context);
    }
    
    if (result != null) return result.evaluate(context);
    if (last != null) return last;
    return new ImmutableValue(null);
  }

}