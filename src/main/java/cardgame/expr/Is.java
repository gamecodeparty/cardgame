/*
 * Is.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Is implements Expression {

  private final Expression target;
  private final Expression type;

  public Is(Expression target, Expression expectedType) {
    this.target = target;
    this.type = expectedType;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    Object object = target.evaluate(context).get();
    Class clazz = (Class) type.evaluate(context).get();
    boolean value = clazz.isInstance(object);
    return new ImmutableValue<Boolean>(value);
  }
}