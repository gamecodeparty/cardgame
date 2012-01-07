/*
 * ModelLoader.java
 * 
 * 05/01/2012
 */
package cardgame.model;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import cardgame.expr.ExpressionParser;
import cardgame.model.Action.Parameter;
import cardgame.model.Card.Suit;
import cardgame.model.Card.Value;

@SuppressWarnings("unchecked")
public class ModelLoader {
  
  public static void main(String[] args) throws IOException {
    new ModelLoader().build(ModelLoader.class.getResourceAsStream("spider.schema"));
  }
  
  public Model build(InputStream in) throws IOException {
    return build(new InputStreamReader(in));
  }
  
  public Model build(Reader reader) throws IOException {
    Model model = new Model();
    
    ExpressionParser parser = new ExpressionParser();
    
    Stack stack = new Stack();
    Iterator<String> it = new LineIterator(reader);
    
    while (it.hasNext()) {
      String line = it.next();
      String[] parts = line.split(" ");
      String cmd = parts[0];
      
      if (cmd.equals("schema")) {
        stack.push(model);
      }
      
      else if (cmd.equals("}")) {
        stack.pop();
      }
      
      else if (cmd.equals("title")) {
        ((Model)stack.peek()).setTitle(parts[1]);
      }
      
      else if (cmd.equals("minSize")) {
        ((Model)stack.peek()).setMinSize(makeDimension(parts));
      }
      
      else if (cmd.equals("preferredSize")) {
        ((Model)stack.peek()).setPreferredSize(makeDimension(parts));
      }
      
      else if (cmd.equals("cards")) {
        String suit = parts[1];
        String pattern = parts[2];
        List<Card> cards = ((Model)stack.peek()).getCards();
        if (pattern.equals("13-cards")) {
          for (int i = 1; i < Value.values().length; i++) {
            cards.add(new Card(Suit.parseSuit(suit), Value.values()[i]));
          }
        } else if (pattern.equals("10-cards")) {
          for (int i = 1; i < Value.values().length; i++) {
            if (i < 8 || i > 10)
            {
              cards.add(new Card(Suit.parseSuit(suit), Value.values()[i]));
            }
          }
        } else {
          throw new RuntimeException("Pattern not recognized: " + line);
        }
      }

      else if (cmd.equals("card")) {
        List<Card> cards = ((Model)stack.peek()).getCards();
        String suit = parts[1];
        String value = parts[2];
        cards.add(new Card(Suit.parseSuit(suit), Value.parseValue(value)));
      }
      
      else if (cmd.equals("pile")) {
        String id = parts[1];
        Pile pile = new Pile(parts[2]);
        pile.setBehaviour(parts[2]);
        pile.setX(Double.parseDouble(parts[3]));
        pile.setY(Double.parseDouble(parts[4]));
        ((Model)stack.peek()).getPiles().put(id, pile);
      }
      
      else if (cmd.equals("tag")) {
        String tag = parts[1];
        String pile = parts[2];
        Model target = ((Model)stack.peek());
        if (!target.getTags().containsKey(tag)) {
          target.getTags().put(tag, new ArrayList<Pile>());
        }
        target.getTags().get(tag).add(target.getPiles().get(pile));
      }
      
      else if (cmd.equals("goal")) {
        String id = parts[1];
        String expression = extractExpression(it);
        ((Model)stack.peek()).getGoals().put(id, parser.parse(expression));
      }
      
      else if (cmd.equals("action")) {
        String id = parts[1];
        Action action = new Action();
        ((Model)stack.peek()).getActions().put(id, action);
        stack.push(action);
      }
      
      else if (cmd.equals("param")) {
        String id = parts[1];
        Parameter parameter = new Parameter();
        ((Action)stack.peek()).getParameters().put(id, parameter);
        stack.push(parameter);
      }
      
      else if (cmd.equals("restriction")) {
        String expression = extractExpression(it);
        ((Parameter)stack.peek()).setExpression(parser.parse(expression));
      }
      
      else if (cmd.equals("condition")) {
        String expression = extractExpression(it);
        ((Action)stack.peek()).setCondition(parser.parse(expression));
      }
      
      else if (cmd.equals("effect")) {
        String expression = extractExpression(it);
        ((Action)stack.peek()).setEffect(parser.parse(expression));
      }
      
      else if (cmd.equals("init")) {
        String expression = extractExpression(it);
        ((Model)stack.peek()).setInitialization(parser.parse(expression));
      }
      
      else {
        throw new RuntimeException("Command not recognized: " + line);
      }
    }
    
    return model;
  }
  
  private String extractExpression(Iterator<String> it) {
    StringBuilder builder = new StringBuilder();
    while (it.hasNext())
    {
      String line = it.next();
      if (line.equals("}")) {
        return builder.toString();
      }
      builder.append(line).append("\n");
    }
    throw new RuntimeException("End of file not expected");
  }

  private Dimension makeDimension(String[] parts) {
    Dimension dimension = new Dimension();
    dimension.width = Integer.parseInt(parts[1]);
    dimension.height = Integer.parseInt(parts[2]);
    return dimension;
  }
  
  private class LineIterator implements Iterator<String> {

    private BufferedReader reader;
    private String buffer;

    LineIterator(Reader reader) {
      this.reader = new BufferedReader(reader);
      bufferize();
    }

    private void bufferize() {
      try {
        this.buffer = null;
        while (reader.ready()) {
          String line = reader.readLine();
          line = line.trim();
          line = line.replaceAll("  ", " ");
          if (line.length() > 0) {
            this.buffer = line;
            break;
          }
        }
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

    @Override
    public boolean hasNext() {
      return buffer != null;
    }

    @Override
    public String next() {
      String result = buffer;
      bufferize();
      return result;
    }

    @Override
    public void remove() {
      throw new RuntimeException("Not Supported");
    }
  }
}
