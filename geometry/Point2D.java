package math.geometry;

public class Point2D {
  private double x, y;
  
  /**
    * Creates a new point at the origin
    */
  public Point2D() {
    this(0, 0);
  }
  
  
  /**
    * Creates a point at the specified location
    * @param x the x-coordinate of the point
    * @param y the y-coordinate of the point
    */
  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }
}