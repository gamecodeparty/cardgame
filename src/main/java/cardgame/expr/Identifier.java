/*
 * Identifier.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Identifier implements Expression {

  private final String name;

  public Identifier(String name) {
    this.name = name;
  }

  @Override
  public MutableValue<?> evaluate(Context context) {
    return new MutableValue(context, name);
  }

}