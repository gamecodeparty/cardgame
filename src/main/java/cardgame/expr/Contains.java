/*
 * Contains.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.lang.reflect.Method;

public class Contains implements Expression {

  private final Expression element;
  private final Expression host;

  public Contains(Expression element, Expression host) {
    this.host = host;
    this.element = element;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    try {
      Object object = host.evaluate(context).get();
      Object target = element.evaluate(context).get();

      Method method = object.getClass().getMethod("contains", Object.class);
      boolean value = (Boolean) method.invoke(object, target);

      return new ImmutableValue<Boolean>(value);
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }
  }
}