/*
 * Literal.java
 *
 * 05/01/2012 
 */
package cardgame.expr;

public abstract class Literal<T> implements Expression {

  private final T literal;

  private Literal(T value) {
    this.literal = value;
  }

  @Override
  public ImmutableValue<T> evaluate(Context context) {
    return new ImmutableValue<T>(literal);
  }

  public static class Boolean extends Literal<java.lang.Boolean> {
    public Boolean(java.lang.Boolean value) {
      super(value);
    }
  }

  public static class Number extends Literal<Double> {
    public Number(Double value) {
      super(value);
    }
  }

  public static class String extends Literal<java.lang.String> {
    public String(java.lang.String value) {
      super(value);
    }
  }

  public static class Null extends Literal {
    public Null() {
      super(null);
    }
  }

  public static class Object extends Literal {
    public Object(Object value) {
      super(value);
    }
  }
}