/*
 * Square.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public class Square implements Expression {

  private final Expression operand;

  public Square(Expression operand) {
    this.operand = operand;
  }

  @Override
  public ImmutableValue<Double> evaluate(Context context) {
    Double value = (Double) operand.evaluate(context).get();
    Double result = Math.sqrt(value);
    return new ImmutableValue<Double>(result);
  }

}