package cs3500.animator.model.tools;

/**
 * A class that represents the leftmost x value, the topmost y value, width, and height of the
 * canvas.
 */
public class Screen {
  private final int x;
  private final int y;
  private final int width;
  private final int height;

  /**
   * Specify the bounding box to be used for the animation.
   *
   * @param x      The leftmost x value
   * @param y      The topmost y value
   * @param width  The width of the bounding box
   * @param height The height of the bounding box
   */
  public Screen(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Gets this canvas' leftmost x.
   *
   * @return the leftmost x of this canvas
   */
  public int getLocX() {
    return this.x;
  }

  /**
   * Gets this canvas' topmost y.
   *
   * @return the topmost y of this canvas
   */
  public int getLocY() {
    return this.y;
  }

  /**
   * Gets this canvas' width.
   *
   * @return the width of this canvas
   */
  public int getCanvasW() {
    return this.width;
  }

  /**
   * Gets this canvas' height.
   *
   * @return the height of this canvas
   */
  public int getCanvasH() {
    return this.height;
  }

  /**
   * converts the canvas to a string form.
   *
   * @return string form of the canvas
   */
  @Override
  public String toString() {
    return this.x + " " + this.y + " " + this.width + " " + this.height + "\n";
  }
}

