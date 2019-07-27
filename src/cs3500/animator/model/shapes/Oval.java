package cs3500.animator.model.shapes;

import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;

/**
 * Represents the Oval shape as a class and inherits all methods from {@link AbstractShapes}.
 */
public class Oval extends AbstractShapes {

  /**
   * An Oval constructor in terms of a name, position, color, radius, and times of appearance and
   * disappearance.
   *
   * @param name     the unique String name of this oval
   * @param position the position this oval will be set to
   * @param color    the color of this oval using RGB values
   * @param radius   the radius of this oval
   */
  public Oval(String name, Posn2D position, RGB color, Size radius) {
    super(0, name, position, color, radius, ShapeType.OVAL);
  }

  /**
   * An Oval constructor in terms of a name.
   *
   * @param name the unique String name of this oval
   */
  public Oval(String name) {
    super(0, name, new Posn2D(0, 0),
            new RGB(0, 0, 0),
            new Size(1, 1),
            ShapeType.OVAL);
  }

  /**
   * An Oval constructor in terms of a name, position, color, radius, and times of appearance and
   * disappearance.
   *
   * @param ro       rotation value
   * @param name     the unique String name of this oval
   * @param position the position this oval will be set to
   * @param color    the color of this oval using RGB values
   * @param radius   the radius of this oval
   */
  public Oval(double ro, String name, Posn2D position, RGB color, Size radius) {
    super(ro, name, position, color, radius, ShapeType.OVAL);
  }


  @Override
  protected String shapeString() {
    return "ellipse";
  }

  @Override
  protected String[] getShapeUnits() {
    return new String[]{"cx", "cy", "rx", "ry"};
  }
}



