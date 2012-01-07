/*
 * Shuffle.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Shuffle implements Expression {

  private final Expression list;

  public Shuffle(Expression list) {
    this.list = list;
  }

  @Override
  public Value evaluate(Context context) {
    Value value = list.evaluate(context);

    Collection collection = (Collection) value.get();

    List shuffled = new ArrayList(collection);
    Collections.shuffle(shuffled);
    
    collection.clear();
    collection.addAll(shuffled);
    
    return value;
  }

}