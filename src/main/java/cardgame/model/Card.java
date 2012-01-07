/*
 * Card.java
 * 
 * 05/01/2012
 */
package cardgame.model;

public class Card implements Cloneable {

  public static enum Suit {
    SPADES,
    HEARTS,
    CLUBS,
    DIAMONDS;

    public static Suit parseSuit(String string) {
      if (string.equals("spades")) return SPADES;
      if (string.equals("hearts")) return HEARTS;
      if (string.equals("clubs")) return CLUBS;
      if (string.equals("diamonds")) return DIAMONDS;
      throw new RuntimeException("Suit not recognized: " + string);
    }
  }

  public static enum Value {
    JOKER,
    ACE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING;

    static String[] images = {
        "?", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K",
        };
    
    public static Value parseValue(String string) {
      for (int i = 0; i < images.length; i++) {
        if (images[i].equals(string)) {
          return values()[i];
        }
      }
      throw new RuntimeException("Card value not recognized: " + string);
    }
    
    @Override
    public String toString() {
      return images[ordinal()];
    }
  }

  private final Suit suit;
  private final Value value;
  
  private boolean facedUp;
  
  public Card() {
    this(Suit.SPADES, Value.ACE);
  }

  public Card(Suit suit, Value value) {
    this.suit = suit;
    this.value = value;
  }

  public Suit getSuit() {
    return suit;
  }

  public Value getValue() {
    return value;
  }

  public int getIntValue() {
    return value.ordinal();
  }

  public void setFacedUp(boolean value) {
    this.facedUp = value;
  }

  public boolean isFacedUp() {
    return facedUp;
  }
  
  @Override
  public Card clone() {
    Card clone = new Card(getSuit(), getValue());
    clone.setFacedUp(isFacedUp());
    return clone;
  }
}
