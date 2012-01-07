/*
 * Matches.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.Iterator;

public class Matches implements Expression {

  private final Expression[] variables;
  private final Expression list;
  private final Expression body;

  public Matches(Expression var, Expression list, Expression body) {
    this.variables = new Expression[] { var };
    this.list = list;
    this.body = body;
  }

  public Matches(Expression var1, Expression var2, Expression list,
      Expression body) {
    this.variables = new Expression[] { var1, var2 };
    this.list = list;
    this.body = body;
  }

  public Matches(Expression var1, Expression var2, Expression var3,
      Expression list, Expression body) {
    this.variables = new Expression[] { var1, var2, var3 };
    this.list = list;
    this.body = body;
  }

  @Override
  public ImmutableValue<Boolean> evaluate(Context context) {
    
    // destacando variaveis
    Value[] values = new Value[variables.length];
    for (int i = 0; i < variables.length; i++) {
      Expression variable = variables[i];
      values[i] = variable.evaluate(context);
    }
    
    // destacando o iterador
    Object target = list.evaluate(context).get();
    Iterator it = (target instanceof Iterable)
        ? ((Iterable) target).iterator() : (Iterator) target;
    
    // aplicando o padrao
    while (it.hasNext()) {
      Object current = it.next();
      
      // avancando as variaveis
      for (int i = 0; i < (values.length - 1); i++) {
        values[i].set(values[i + 1].get());
      }
      values[values.length - 1].set(current);
      
      boolean valid = (Boolean) body.evaluate(context).get();
      if (!valid) {
        return new ImmutableValue<Boolean>(false);
      }
    }
    
    // esgotando as variaveis
    for (int j = 0; j < values.length - 1; j++) {
      
      // avancando as variaveis
      for (int i = 0; i < (values.length - 1); i++) {
        values[i].set(values[i + 1].get());
      }
      values[values.length - 1].set(null);
      
      boolean valid = (Boolean) body.evaluate(context).get();
      if (!valid) {
        return new ImmutableValue<Boolean>(false);
      }
    }
    
    return new ImmutableValue<Boolean>(true);
  }

}