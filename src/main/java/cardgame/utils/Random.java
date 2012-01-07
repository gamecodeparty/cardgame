/*
 * Random.java
 * 
 * 05/01/2012
 */
package cardgame.utils;

public class Random {

  private static java.util.Random rnd = new java.util.Random();
  
  private Random() {
  }
  
  public void seed(long seed) {
    rnd.setSeed(seed);
  }
  
  public static double next() {
    return rnd.nextDouble();
  }
  
  public static double next(double length) {
    return rnd.nextDouble() * length;
  }
  
  public static int nextInt() {
    return rnd.nextInt();
  }
  
  public static int nextInt(int length) {
    return rnd.nextInt(length);
  }
  
  public static double nextRange(double start, double end) {
    double margin = end - start;
    return (rnd.nextDouble() * margin) + start;
  }
  
  public static int nextRangeInt(int start, int end) {
    int margin = end - start;
    return rnd.nextInt(margin) + start;
  }
}
