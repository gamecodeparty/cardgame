package cardgame.expr;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cardgame.utils.ArrayIterator;

public class TokenIterator extends ArrayIterator<String> {

  private final String[] tokens;
  private final String[] strings;
  private int current;

  public TokenIterator(String expression) {
    List<String> strings = new ArrayList<String>();
    
    // destacando strings
    //   as strings sao removidas do texto, salvas da colecao de strings e 
    //   substituidas pelo padrao %0, onde 0 refere-se ao indice da strings na
    //   lista de arrays removidas
    String regex = "\\[([^\\[\\]]+)\\]";
    Matcher match = Pattern.compile(regex).matcher(expression);
    while (match.find(0)) {
      strings.add(match.group(1));
      expression = match.replaceFirst(String.format("(\\$%%%d)", strings.size() - 1));
      match.reset(expression);
    }
    
    // normalizando o texto
    //   numeros sao formatados como: (# 0)
    //   textos sao formatados como: ($ %0)
    //   variaveis sao formatadas como: (& X)
    //   e um espaço divide cada termo
    // por exemplo, o texto:
    //   (with [talz] var (value-of 10))
    // eh normalizado como:
    //   ( with ( $ %0 ) ( & var ) ( value-of ( # 10 ) ) )
    // lembrando que %0 é uma referencia ao indice da string na lista de stirngs
    expression = expression.replaceAll("[\\n\\t]", " ");
    expression = expression.replaceAll(" ((?:[0-9.-])+)", "(#$1)");
    expression = expression.replaceAll(" ((?:\\w|[_.-])+)", "(&$1)");
    expression = expression.replaceAll("([()])", " $1 ");
    expression = expression.replaceAll(" {2,}", " ");
    expression = expression.replaceAll("([$#&])", "$1 ");
    expression = expression.replaceAll("(^ | $)", "");
    
    this.tokens = expression.split(" ");
    this.strings = strings.toArray(new String[strings.size()]);
  }

  @Override
  public boolean hasNext() {
    return current < tokens.length;
  }

  @Override
  public String next() {
    String token = tokens[current++];
    if (token.startsWith("%")) {
      // as strings sao removidas da expressao e substituidas pelo padrao %0,
      // onde zero refere-se ao indice da string na lista de strings removidas.
      // aqui nos recuperamos esta string antes de retornar o token
      int index = Integer.parseInt(token.substring(1));
      token = strings[index];
    }
    return token;
  }

  @Override
  public void remove() {
    throw new RuntimeException("Remove operation not supported!");
  }
}
