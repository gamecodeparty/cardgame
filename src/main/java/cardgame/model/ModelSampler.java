/*
 * ModelSampler.java
 * 
 * 05/01/2012
 */
package cardgame.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cardgame.expr.ExpressionParser;
import cardgame.model.Card.Suit;
import cardgame.model.Card.Value;
import cardgame.utils.Random;

public class ModelSampler {

  private ModelSampler() {
  }

  public static Model sampleSpiderState() {
    Model model = new Model();
    
    ExpressionParser parser = new ExpressionParser();
    
    model.setTitle("Spider");
    model.setMinSize(new Dimension(320, 240));
    model.setPreferredSize(new Dimension(800, 600));

    for (int i = 0; i < 8; i++) {
      for (int j = 1; j < Value.values().length; j++) {
        Value value = Value.values()[j];
        model.getCards().add(new Card(Suit.SPADES, value));
      }
    }
    
    model.getPiles().put("stock", new Pile("squared", 9.9, 5.0));
    model.getPiles().put("tableau0", new Pile("fanned-down", 0.0, 0.0));
    model.getPiles().put("tableau1", new Pile("fanned-down", 1.1, 0.0));
    model.getPiles().put("tableau2", new Pile("fanned-down", 2.2, 0.0));
    model.getPiles().put("tableau3", new Pile("fanned-down", 3.3, 0.0));
    model.getPiles().put("tableau4", new Pile("fanned-down", 4.4, 0.0));
    model.getPiles().put("tableau5", new Pile("fanned-down", 5.5, 0.0));
    model.getPiles().put("tableau6", new Pile("fanned-down", 6.6, 0.0));
    model.getPiles().put("tableau7", new Pile("fanned-down", 7.7, 0.0));
    model.getPiles().put("tableau8", new Pile("fanned-down", 8.8, 0.0));
    model.getPiles().put("tableau9", new Pile("fanned-down", 9.9, 0.0));
    model.getPiles().put("foundation0", new Pile("squared", 0.0, 5.0));
    model.getPiles().put("foundation1", new Pile("squared", 1.1, 5.0));
    model.getPiles().put("foundation2", new Pile("squared", 2.2, 5.0));
    model.getPiles().put("foundation3", new Pile("squared", 3.3, 5.0));
    model.getPiles().put("foundation4", new Pile("squared", 4.4, 5.0));
    model.getPiles().put("foundation5", new Pile("squared", 5.5, 5.0));
    model.getPiles().put("foundation6", new Pile("squared", 6.6, 5.0));
    model.getPiles().put("foundation7", new Pile("squared", 7.7, 5.0));
    
    model.getTags().put("tableau", new ArrayList<Pile>());
    model.getTags().get("tableau").add(model.getPiles().get("tableau0"));
    model.getTags().get("tableau").add(model.getPiles().get("tableau1"));
    model.getTags().get("tableau").add(model.getPiles().get("tableau2"));
    model.getTags().get("tableau").add(model.getPiles().get("tableau3"));
    model.getTags().get("tableau").add(model.getPiles().get("tableau4"));
    model.getTags().get("tableau").add(model.getPiles().get("tableau5"));
    model.getTags().get("tableau").add(model.getPiles().get("tableau6"));
    model.getTags().get("tableau").add(model.getPiles().get("tableau7"));
    model.getTags().get("tableau").add(model.getPiles().get("tableau8"));
    model.getTags().get("tableau").add(model.getPiles().get("tableau9"));
    
    model.getTags().put("fountations", new ArrayList<Pile>());
    model.getTags().get("fountations").add(model.getPiles().get("fountation0"));
    model.getTags().get("fountations").add(model.getPiles().get("fountation1"));
    model.getTags().get("fountations").add(model.getPiles().get("fountation2"));
    model.getTags().get("fountations").add(model.getPiles().get("fountation3"));
    model.getTags().get("fountations").add(model.getPiles().get("fountation4"));
    model.getTags().get("fountations").add(model.getPiles().get("fountation5"));
    model.getTags().get("fountations").add(model.getPiles().get("fountation6"));
    model.getTags().get("fountations").add(model.getPiles().get("fountation7"));
    
    // distribuindo cartas
    List<Card> work = new ArrayList<Card>();
    work.addAll(model.getCards());
    Stack<Card> shuffled = new Stack<Card>();
    while (!work.isEmpty()) {
      int index = Random.nextInt(work.size());
      shuffled.push(work.remove(index));
    }
    work.clear();
    
    // cartas da area de jogo
    for (Pile pile : model.getTags().get("tableau")) {
      for (int i = 0; i < 5; i++) {
        pile.push(shuffled.pop());
      }
    }
    for (Pile pile : new Pile[] {
        model.getPiles().get("tableau0"),
        model.getPiles().get("tableau1"),
        model.getPiles().get("tableau2"),
        model.getPiles().get("tableau3"),
        }) {
      pile.push(shuffled.pop());
    }
    for (Pile pile : model.getTags().get("tableau")) {
      pile.peek().setFacedUp(true);
    }
    
    // cartas da area de compra
    while (!shuffled.isEmpty()) {
      model.getPiles().get("stock").push(shuffled.pop());
    }
    
    // validação de fim de jogo
    model.getGoals().put("standard", parser.parse(
        "(true-for-all n tags.fountations (not (empty n)))"
        ));
    
    // montar actions
    
    model.setInitialization(parser.parse(
        "(with (new stack) work " +
          "(program " +
            "(iterate n cards (push n work)) " +
            "(shuffle work) " +
            "(iterate n tags.tableau (repeat 5 (push (pop work) n))) " +
            "(iterate n (join tableau0 tableau1 tableau2 tableau3) (push (pop work) n)) " +
            "(while (not (empty work)) (push (pop work) stock)) " +
          ") " +
        ") "
        ));
    
    return model;
  }
}
