/*
 * Empty.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.lang.reflect.Method;

public class Empty implements Expression {

  private final Expression expression;

  public Empty(Expression expression) {
    this.expression = expression;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    try {
      Object object = expression.evaluate(context).get();

      Method method = object.getClass().getMethod("isEmpty");
      boolean value = (Boolean) method.invoke(object);

      return new ImmutableValue<Boolean>(value);
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }
  }
}