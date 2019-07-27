package cs3500.animator.model.tools;

/**
 * A class that represents 2D positions.
 */
public class Posn2D {
  private double x;
  private double y;

  /**
   * A Posn2D constructor in terms of x and y positions.
   */
  public Posn2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets this position's x position.
   *
   * @return the x-position of this Posn2D
   */
  public double getX() {
    return this.x;
  }

  /**
   * Gets this position's y position.
   *
   * @return the y-position of this Posn2D
   */
  public double getY() {
    return this.y;
  }

  /**
   * Sets this position to {@code that} position.
   *
   * @param that the position this Posn2D will be set to
   */
  public void setPosn(Posn2D that) {
    this.x = that.x;
    this.y = that.y;
  }
}

