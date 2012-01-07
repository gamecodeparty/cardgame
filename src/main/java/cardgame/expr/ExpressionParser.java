/*
 * ExpressionParser.java
 * 
 * 05/01/2012
 */
package cardgame.expr;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ExpressionParser {

  public static void main(String[] args) {
    new ExpressionParser().parse(
        " (with stack (new (type [stack]))" +
        "   (program" +
        "     (iterate n (.. 1 2) (push n stack))" +
        "     (print [1st pass])" +
        "     (print [ ] (peek stack))" +
        "     (print [ ] (peek stack))" +
        "     (print [ ] (peek stack))" +
        "   )" +
        " )"
        ).evaluate(new Context());
  }
  
  public Program parse(String definition) {
    Iterator<String> tokens = new TokenIterator(definition);
    
    Stack<Builder> stack = new Stack<Builder>();
    stack.push(new Builder("program"));
    while (tokens.hasNext()) {
      String token = tokens.next();
      if (token.equals("(")) {
        stack.push(new Builder(tokens.next()));
      } else if (token.equals(")")) {
        Expression expr = stack.pop().createExpression();
        stack.peek().add(expr);
      } else {
        stack.peek().add(token);
      }
    }
    
    return (Program) stack.pop().createExpression();
  }

  private static class Builder {

    final String function;
    final Queue<Object> parameters = new LinkedList<Object>();

    Builder(String function) {
      this.function = function;
    }

    void add(Object item) {
      parameters.add(item);
    }
    
    private Object poll() {
      Object value = parameters.poll();
      if (value == null)
        throw new RuntimeException("Wrong number of arguments: " + function);
      return value;
    }

    String removeLiteral() {
      return (String) poll();
    }

    Expression remove() {
      return (Expression) poll();
    }

    Expression[] removeAll() {
      Expression[] result = new Expression[parameters.size()];
      parameters.toArray(result);
      parameters.clear();
      return result;
    }

    boolean isEmpty() {
      return parameters.isEmpty();
    }
    
    int size() {
      return parameters.size();
    }

    Expression createExpression() {
      Expression e = null;

      if (function.equals("program")) {
        e = new Program(removeAll());
      } else if (function.equals("#")) {
        e = new Literal.Number(new Double(removeLiteral()));
      } else if (function.equals("$")) {
        e = new Literal.String(removeLiteral());
      } else if (function.equals("&")) {
        String literal = removeLiteral();
        if (literal.equals("true")) {
          e = new Literal.Boolean(true);
        } else if (literal.equals("false")) {
          e = new Literal.Boolean(false);
        } else if (literal.equals("null")) {
          e = new Literal.Null();
        } else {
          e = new Identifier(literal);
        }
      } else if (function.equals("print")) {
        e = new Print(removeAll());
      } else if (function.equals("+")) {
        e = new Addition(removeAll());
      } else if (function.equals("-")) {
        e = new Subtraction(removeAll());
      } else if (function.equals("*")) {
        e = new Multiplication(removeAll());
      } else if (function.equals("/")) {
        e = new Division(removeAll());
      } else if (function.equals("pow")) {
        e = new Pow(remove(), remove());
      } else if (function.equals("square")) {
        e = new Square(remove());
      } else if (function.equals("mod")) {
        e = new Mod(remove(), remove());
      } else if (function.equals("abs")) {
        e = new Abs(remove());
      } else if (function.equals("round")) {
        if (size() == 1) {
          e = new Round(remove());
        } else {
          e = new Round(remove(), remove());
        }
      } else if (function.equals("or")) {
        e = new Or(removeAll());
      } else if (function.equals("eq")) {
        e = new Equals(remove(), remove());
      } else if (function.equals("gt")) {
        e = new Greater(remove(), remove());
      } else if (function.equals("ge")) {
        e = new GreaterOrEquals(remove(), remove());
      } else if (function.equals("lt")) {
        e = new Lesser(remove(), remove());
      } else if (function.equals("le")) {
        e = new LesserOrEquals(remove(), remove());
      } else if (function.equals("with")) {
        e = new With(remove(), remove(), remove());
      } else if (function.equals("let")) {
        e = new Let(remove(), remove());
      } else if (function.equals("type")) {
        e = new Type(remove());
      } else if (function.equals("new")) {
        e = new New(remove());
      } else if (function.equals("value-of")) {
        e = new ValueOf(remove());
      } else if (function.equals("not")) {
        e = new Not(remove());
      } else if (function.equals("empty")) {
        e = new Empty(remove());
      } else if (function.equals("and")) {
        e = new And(removeAll());
      } else if (function.equals("true-for-all")) {
        e = new TrueForAll(remove(), remove(), remove());
      } else if (function.equals("concat")) {
        e = new Concat(removeAll());
      } else if (function.equals("contains")) {
        e = new Contains(remove(), remove());
      } else if (function.equals("is")) {
        e = new Is(remove(), remove());
      } else if (function.equals("join")) {
        e = new Join(removeAll());
      } else if (function.equals("list") || function.equals("..")) {
        e = new MakeList(remove(), remove());
      } else if (function.equals("shuffle")) {
        e = new Shuffle(remove());
      } else if (function.equals("if")) {
        e = new IfElse(remove(), remove(), remove());
      } else if (function.equals("seq")) {
        e = new Seq(remove(), remove());
      } else if (function.equals("while")) {
        if (size() == 2) {
          e = new While(remove(), remove());
        } else {
          e = new While(remove(), remove(), remove());
        }
      } else if (function.equals("iterate")) {
        if (size() == 3) {
          e = new Iterate(remove(), remove(), remove());
        } else {
          e = new Iterate(remove(), remove(), remove(), remove());
        }
      } else if (function.equals("repeat")) {
        if (size() == 2) {
          e = new Repeat(remove(), remove());
        } else {
          e = new Repeat(remove(), remove(), remove());
        }
      } else if (function.equals("push")) {
        e = new Push(remove(), remove());
      } else if (function.equals("pop")) {
        e = new Pop(remove());
      } else if (function.equals("peek")) {
        e = new Peek(remove());
      } else if (function.equals("matches")) {
        if (size() == 3) {
          e = new Matches(remove(), remove(), remove());
        } else if (size() == 4) {
          e = new Matches(remove(), remove(), remove(), remove());
        } else {
          e = new Matches(remove(), remove(), remove(), remove(), remove());
        }
      }

      if (e == null) {
        throw new RuntimeException("Token not recognized: " + function);
      }
      if (!isEmpty()) {
        throw new RuntimeException("Wrong number of parameters: " + function);
      }
      return e;
    }
  }
}
