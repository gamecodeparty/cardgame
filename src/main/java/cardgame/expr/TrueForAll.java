/*
 * TrueForAll.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.Iterator;

public class TrueForAll implements Expression {

  private final Expression holder;
  private final Expression list;
  private final Expression body;

  public TrueForAll(Expression holder, Expression list, Expression body) {
    this.holder = holder;
    this.list = list;
    this.body = body;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    Value variable = holder.evaluate(context);
    Object savedValue = variable.get();
    
    Object values = list.evaluate(context).get();
    Iterator it = (values instanceof Iterable) ? ((Iterable) values).iterator()
        : (Iterator) values;

    while (it.hasNext()) {
      variable.set(it.next());
      Boolean valid = (Boolean) body.evaluate(context).get();
      if (!valid) {
        return new ImmutableValue<Boolean>(false);
      }
    }
    
    variable.set(savedValue);
    return new ImmutableValue<Boolean>(true);
  }

}