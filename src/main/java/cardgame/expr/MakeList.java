/*
 * MakeList.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.ArrayList;
import java.util.Collection;

public class MakeList implements Expression {

  private final Expression start;
  private final Expression end;

  public MakeList(Expression start, Expression end) {
    this.start = start;
    this.end = end;
  }

  @Override
  public ImmutableValue<Collection<Double>> evaluate(Context context) {
    Double one = (Double) start.evaluate(context).get();
    Double other = (Double) end.evaluate(context).get();

    double a = Math.round(one);
    double b = Math.round(other);

    int size = (int)(b - a + 1);
    Collection<Double> list = new ArrayList<Double>(size);
    for (double i = a; i <= b; i++) {
      list.add(i);
    }

    return new ImmutableValue<Collection<Double>>(list);
  }

}