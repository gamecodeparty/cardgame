/*
 * Seq.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import cardgame.model.Card;

public class Seq implements Expression {

  private final Expression a;
  private final Expression b;

  public Seq(Expression a, Expression b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    Object one = a.evaluate(context).get();
    Object other = b.evaluate(context).get();
    if (one instanceof Card) {
      return evaluateCards((Card) one, (Card) other);
    }
    return evaluateNumbers((Double) one, (Double) other);
  }

  private ImmutableValue<Boolean> evaluateCards(Card one, Card other) {
    boolean valid = one.getSuit().equals(other.getSuit());
    if (valid) {
      Integer oneValue = one.getIntValue();
      Integer otherValue = other.getIntValue();
      valid = oneValue.compareTo(otherValue - 1) == 0;
    }
    return new ImmutableValue<Boolean>(valid);
  }

  private ImmutableValue<Boolean> evaluateNumbers(Double one, Double other) {
    boolean valid = (one.intValue() + 1) == other.intValue();
    return new ImmutableValue<Boolean>(valid);
  }

}