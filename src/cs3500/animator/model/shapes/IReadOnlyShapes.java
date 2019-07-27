package cs3500.animator.model.shapes;

import java.util.List;
import java.util.TreeMap;

import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;

/**
 * (CHANGE: This class is used to prevent mutation of the original shapes' data) An interface
 * IReadOnlyShapes to prevent mutation of the original shapes.
 */
public interface IReadOnlyShapes {
  /**
   * Retrieves all the states this shape has gone through.
   *
   * @return a String representation of the commands applied to this shape and interval occurrences
   */
  String getShapeState();

  /**
   * Gets the position of the shape.
   *
   * @return the position of the shape
   */
  Posn2D getPosition();

  /**
   * Gets the color of the shape.
   *
   * @return the color of the shape
   */
  RGB getColor();

  /**
   * Gets the size of the shape.
   *
   * @return the size of the shape
   */
  Size getSize();

  /**
   * Gets the name of the shape.
   *
   * @return the name of the shape
   */
  String getName();

  /**
   * Gets the type of the shape.
   *
   * @return the type of the shape
   */
  ShapeType getShapeType();

  /**
   * Gets the history of commands and intervals applied to this shape in a TreeMap.
   *
   * @return a TreeMap containing the commands and its intervals applied to this shape.
   */
  TreeMap<Interval, double[]> getHistory();


  /**
   * Checks if the given Object is a Shape with the same name and type as this.
   *
   * @return true if {@code o} is the same instance, name, and shapetype as this shape
   */
  boolean equals(Object o);

  /**
   * Gives the hashcode of this shape in terms of its size, color, position, and visibility.
   *
   * @return the hashcode of this shape's properties.
   */
  int hashCode();

  /**
   * Gets the mutated shape at the certain tick.
   *
   * @param tick current tick
   * @return the shape with corresponding fields' values at that tick
   */
  IReadOnlyShapes getShapesAtTick(int tick);

  /**
   * Reformats the shape's description according to the SVG Tag.
   *
   * @return return the SVG Tag of the shape
   */
  String getSVGTag(int tick);

  /**
   * Produces the last tick of the shape that disapears.
   *
   * @return the very last tick of all the shapes
   */
  int getShapeLastTime();

  /**
   * Converts the keyframe into a String.
   *
   * @return the keyframe in a string form
   */
  List<String> getKeyFrame();

  /**
   * Getter to get the frame field.
   *
   * @return the keyFrame field
   */
  TreeMap<Integer, double[]> getFrames();

  /**
   * @return rotation value
   */
  double getRotation();

}


