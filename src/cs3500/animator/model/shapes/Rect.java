package cs3500.animator.model.shapes;


import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;

/**
 * A class representation of the Rectangle shape that inherits all methods from {@link
 * AbstractShapes}.
 */
public class Rect extends AbstractShapes {

  /**
   * A constructor of this Rect in terms of its name, position, color, size, and times of visibility
   * (appearance and disappearance).
   *
   * @param name     the unique String name of this rectangle
   * @param position the position this rectangle will be set to
   * @param color    the color of this rectangle in RGB values
   * @param size     the size of this rectangle
   */
  public Rect(String name, Posn2D position, RGB color, Size size) {
    super(0, name, position, color, size, ShapeType.RECT);
  }

  /**
   * A constructor of this Rect in terms of its name.
   *
   * @param name the unique String name of this rectangle
   */
  public Rect(String name) {
    super(0, name, new Posn2D(0, 0),
            new RGB(0, 0, 0),
            new Size(1, 1),
            ShapeType.RECT);
  }

  /**
   * A constructor of this Rect in terms of its name, position, color, size, and times of visibility
   * (appearance and disappearance).
   *
   * @param ro       rotation value
   * @param name     the unique String name of this rectangle
   * @param position the position this rectangle will be set to
   * @param color    the color of this rectangle in RGB values
   * @param size     the size of this rectangle
   */
  public Rect(double ro, String name, Posn2D position, RGB color, Size size) {
    super(ro, name, position, color, size, ShapeType.RECT);
  }


  @Override
  protected String shapeString() {
    return "rect";
  }

  @Override
  protected String[] getShapeUnits() {
    return new String[]{"x", "y", "width", "height"};
  }
}



