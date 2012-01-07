/*
 * Action.java
 * 
 * 05/01/2012
 */
package cardgame.model;

import java.util.HashMap;
import java.util.Map;

import cardgame.expr.Expression;

public class Action {

  private Expression condition;
  private Expression effect;
  private final Map<String, Parameter> parameters = new HashMap<String, Parameter>();

  public Map<String, Parameter> getParameters() {
    return parameters;
  }

  public Expression getCondition() {
    return condition;
  }

  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  public Expression getEffect() {
    return effect;
  }

  public void setEffect(Expression effect) {
    this.effect = effect;
  }
  
  public void apply(Model model) {
    // TODO: aplicar a acao em cima do modelo indicado
  }
  
  public static class Parameter {

    private Expression expression;

    public void setExpression(Expression expression) {
      this.expression = expression;
    }

    public Expression getExpression() {
      return expression;
    }
  }
}
