/*
 * Expression.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

public interface Expression {

  Value evaluate(Context context);
}
