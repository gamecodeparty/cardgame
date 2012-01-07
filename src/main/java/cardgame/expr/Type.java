/*
 * Type.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.Stack;

import cardgame.model.Card;
import cardgame.model.Cell;
import cardgame.model.Pile;

public class Type implements Expression {

  private final Expression kind;

  public Type(Expression kind) {
    this.kind = kind;
  }

  @Override
  public ImmutableValue<Class> evaluate(Context context) {
    String type = kind.evaluate(context).get().toString();
    
    if (type.equals("card")) return new ImmutableValue<Class>(Card.class);
    if (type.equals("pile")) return new ImmutableValue<Class>(Pile.class);
    if (type.equals("cell")) return new ImmutableValue<Class>(Cell.class);
    
    // TODO: alterar para o stack customizado assim que ele for construido
    if (type.equals("stack")) return new ImmutableValue<Class>(Stack.class);
    
    throw new RuntimeException("Type not recognized: " + type);
  }

}