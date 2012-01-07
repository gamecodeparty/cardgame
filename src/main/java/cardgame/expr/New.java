/*
 * New.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class New implements Expression {

  private final Expression type;

  public New(Expression type) {
    this.type = type;
  }

  @Override
  public ImmutableValue evaluate(Context context) {
    try {
      Class clazz = (Class) type.evaluate(context).get();
      return new ImmutableValue(clazz.newInstance());
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }
  }

}