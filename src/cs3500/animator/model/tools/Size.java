package cs3500.animator.model.tools;

/**
 * A class that represents size in terms of width and height.
 */
public class Size {
  private double width;
  private double height;

  /**
   * A Size constructor in terms of width of height.
   */
  public Size(double width, double height) {
    this.width = width;
    this.height = height;
    ensurePos(width, height);
  }

  /**
   * Ensures the given numbers are positive.
   *
   * @throws IllegalArgumentException if the values are non-positive.
   */
  private static void ensurePos(double... d) throws IllegalArgumentException {
    for (double dub : d) {
      if (dub < 0) {
        throw new IllegalArgumentException("Can only be positive");
      }
    }
  }


  /**
   * Gets this size's width.
   *
   * @return the width of this size
   */
  public double getWidth() {
    return this.width;
  }

  /**
   * Gets this size's height.
   */
  public double getHeight() {
    return this.height;
  }

  /**
   * Sets this size to {@code that}'s size.
   */
  public void setSize(Size that) {
    this.width = that.width;
    this.height = that.height;
  }
}

