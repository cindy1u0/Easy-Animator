package cs3500.animator.model.tools;

/**
 * A class that represents the rgb values of a color.
 */
public class RGB {
  private double red;
  private double green;
  private double blue;

  /**
   * A RGB constructor in terms of the red, green, and blue values of a color.
   */
  public RGB(double red, double green, double blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    validColor(Math.round(red), Math.round(green), Math.round(blue));
  }

  /**
   * Ensures the rgb values constitute a valid color.
   */
  private void validColor(double r, double g, double b) throws IllegalArgumentException {
    if (r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0) {
      throw new IllegalArgumentException("must be a valid rgb");
    }
  }

  /**
   * Gets the red value of this RGB color.
   *
   * @return the red value
   */
  public double getRed() {
    return this.red;
  }

  /**
   * Gets the green value of this RGB color.
   *
   * @return the green value
   */
  public double getGreen() {
    return this.green;
  }

  /**
   * Gets the blue value of this RGB color.
   *
   * @retrun the blue of this value.
   */
  public double getBlue() {
    return this.blue;
  }

  /**
   * Sets this RGB values to {@code that} rgb values.
   */
  public void setRGB(RGB that) {
    this.red = that.red;
    this.green = that.green;
    this.blue = that.blue;
  }
}

