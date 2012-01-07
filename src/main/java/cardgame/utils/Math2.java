package cardgame.utils;

public class Math2 {

  private Math2() {
  }

  public static double round(double value, int precision) {
    double factor = Math.pow(10, precision);
    return Math.round(value * factor) / factor;
  }
}
