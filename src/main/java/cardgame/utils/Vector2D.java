/*
 * Vector2D.java
 * 
 * 05/01/2012
 */
package cardgame.utils;

import java.awt.geom.Point2D;

public class Vector2D {

  private double x;
  private double y;

  public Vector2D() {
  }

  public Vector2D(double x, double y) {
    setVector(x, y);
  }

  public Vector2D(Vector2D value) {
    setVector(value);
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }
  
  public void setVector(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public void setVector(Vector2D value) {
    this.x = value.x;
    this.y = value.y;
  }
  
  public Vector2D multiply(double value) {
    return new Vector2D(x * value, y * value);
  }
  
  public Vector2D add(Vector2D value) {
    return new Vector2D(x + value.x, y + value.y);
  }
  
  // TODO: Implementar outras operações matemáticas com vetores

  public Point2D getPoint2D() {
    return new Point2D.Double(x, y);
  }

  public void setPoint2D(Point2D value) {
    x = value.getX();
    y = value.getY();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj != null && obj instanceof Vector2D) {
      Vector2D other = (Vector2D) obj;
      return x == other.x && y == other.y;
    }
    return false;
  }
}
