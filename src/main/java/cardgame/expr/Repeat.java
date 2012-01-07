/*
 * Repeat.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Repeat implements Expression {

  private final Expression count;
  private final Expression body;
  private final Expression result;

  public Repeat(Expression count, Expression body) {
    this.count = count;
    this.body = body;
    this.result = null;
  }

  public Repeat(Expression count, Expression body, Expression result) {
    this.count = count;
    this.body = body;
    this.result = result;
  }

  @Override
  public Value evaluate(Context context) {
    Value last = null;

    Double times = (Double) count.evaluate(context).get();

    int n = times.intValue();
    for (int i = 0; i < n; i++) {
      last = body.evaluate(context);
    }

    if (result != null)
      return result.evaluate(context);
    if (last != null)
      return last;
    return new ImmutableValue(null);
  }

}