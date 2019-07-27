package cs3500.animator.model.shapes;

import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;

/**
 * An interface containing all the operations applied to a Shape.
 */
public interface Shapes extends IReadOnlyShapes {

  /**
   * Goes through this TreeMap to ensure any gaps between states are filled with states representing
   * this Shape is motionless at those intervals.
   */
  void checkGap();

  /**
   * Changes the current position to the other position, i.e. moves the shape from current position
   * to the given position.
   *
   * @param p the position that the shape is moving to
   */
  void changePosition(Posn2D p) throws IllegalArgumentException;

  /**
   * Changes the current color to the given color.
   *
   * @param c the color that the user wants to change it too
   */
  void changeColor(RGB c) throws IllegalArgumentException;

  /**
   * Changes the current dimension to the given dimension, i.e. shrinking the shape or enlarging the
   * shape.
   *
   * @param s the dimension that the user wants to change it too
   */
  void changeSize(Size s) throws IllegalArgumentException;

  /**
   * Checks if there are overlapping intervals in the tree map.
   */
  void checkOverlap() throws IllegalArgumentException;
}


