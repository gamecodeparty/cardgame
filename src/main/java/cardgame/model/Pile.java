/*
 * Pile.java
 * 
 * 05/01/2012
 */
package cardgame.model;

import cardgame.utils.Vector2D;

public class Pile extends PileBase<Card> implements Cloneable {

  public static String SQUARED = "squared";
  public static String FANNED_DOWN = "fanned-down";
  public static String FANNED_UP = "fanned-up";
  public static String FANNED_LEFT = "fanned-left";
  public static String FANNED_RIGHT = "fanned-right";

  private final Vector2D location;
  private final Vector2D orientation;
  
  private String behaviour;
  
  public Pile() {
    this(SQUARED, 0, 0);
  }

  public Pile(String behaviour) {
    this(behaviour, 0, 0);
  }

  public Pile(String behaviour, double x, double y) {
    this.location = new Vector2D();
    this.orientation = new Vector2D();
    setBehaviour(behaviour);
    setLocation(x, y);
  }

  public String getBehaviour() {
    return behaviour;
  }
  
  public Vector2D getOrientation() {
    return orientation;
  }
  
  public void setBehaviour(String value) {
    this.behaviour = value == null ? SQUARED : value;
    
    if (value.equals(FANNED_DOWN)) {
      this.orientation.setY(1);
    } else if (value.equals(FANNED_UP)) {
      this.orientation.setY(-1);
    } else if (value.equals(FANNED_LEFT)) {
      this.orientation.setX(-1);
    } else if (value.equals(FANNED_RIGHT)) {
      this.orientation.setX(1);
    } else {
      this.orientation.setVector(0, 0);
    }
  }

  public Vector2D getLocation() {
    return location;
  }

  public void setLocation(Vector2D value) {
    location.setVector(value);
  }

  public void setLocation(double x, double y) {
    location.setVector(x, y);
  }

  public double getX() {
    return getLocation().getX();
  }

  public void setX(double x) {
    setLocation(x, getY());
  }

  public double getY() {
    return getLocation().getY();
  }

  public void setY(double y) {
    setLocation(getX(), y);
  }
  
  @Override
  public Pile clone() {
    Pile clone = new Pile(getBehaviour());
    clone.setLocation(getLocation());
    clone.getStack().addAll(getStack());
    return clone;
  }
}
