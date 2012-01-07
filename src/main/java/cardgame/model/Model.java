/*
 * Model.java
 * 
 * 05/01/2012
 */
package cardgame.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cardgame.expr.Expression;

public class Model {

  private String title;
  private Dimension minSize;
  private Dimension preferredSize;
  private Expression init;

  private final List<Card> cards = new ArrayList<Card>();
  private final Map<String, Pile> piles = new HashMap<String, Pile>();
  private final Map<String, List<Pile>> tags = new HashMap<String, List<Pile>>();
  private final Map<String, Expression> goals = new HashMap<String, Expression>();
  private final Map<String, Action> actions = new HashMap<String, Action>();

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Dimension getMinSize() {
    return minSize;
  }

  public void setMinSize(Dimension minSize) {
    this.minSize = minSize;
  }

  public Dimension getPreferredSize() {
    return preferredSize;
  }

  public void setPreferredSize(Dimension preferredSize) {
    this.preferredSize = preferredSize;
  }

  public Expression getInit() {
    return init;
  }

  public void setInitialization(Expression init) {
    this.init = init;
  }

  public List<Card> getCards() {
    return cards;
  }

  public Map<String, Pile> getPiles() {
    return piles;
  }

  public Map<String, List<Pile>> getTags() {
    return tags;
  }

  public Map<String, Expression> getGoals() {
    return goals;
  }

  public Map<String, Action> getActions() {
    return actions;
  }
  
  public Memento createMemento() {
    Memento memento = new Memento();
    memento.title = getTitle();
    memento.minSize = getMinSize();
    memento.preferredSize = getPreferredSize();
    for (Card card : getCards())
      memento.cards.add(card.clone());
    for (Entry<String, Pile> entry : getPiles().entrySet())
      memento.piles.put(entry.getKey(), entry.getValue().clone());
    return memento;
  }
  
  public void restoreMemento(Memento memento) {
    setTitle(memento.title);
    setMinSize(memento.minSize);
    setPreferredSize(memento.preferredSize);

    getCards().clear();
    getCards().addAll(memento.cards);
    
    getPiles().clear();
    getPiles().putAll(memento.piles);
  }
  
  public final class Memento {
    
    // no memento deve ser registrado um clone de todos os atributos mutaveis
    // no entanto, apesar de possivel, as tags, as ações e as espressões não 
    // mudam durante o jogo.
    // TODO: talvez as listas poderiam ser trocadas por listas imutaveis para
    // reforçar esta regra.
    // por isto, apenas os atributos que realmente sao alterados durante o jogo
    // estao sendo registrados aqui
    
    private String title;
    private Dimension minSize;
    private Dimension preferredSize;
    private final List<Card> cards = new ArrayList<Card>();
    private final Map<String, Pile> piles = new HashMap<String, Pile>();
  }
}
