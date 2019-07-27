package cs3500.animator.model;

import java.util.List;

import cs3500.animator.model.commands.Command;
import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.model.shapes.Shapes;
import cs3500.animator.model.tools.Screen;

/**
 * (CHANGE 1: We used a builder class this time to construct the model instead of adding shapes and
 * commands each time as well as adding a few methods that are adaptable to the program) (CHANGE 2:
 * The model interface now includes operations relating to keyframes, so that tweening and motions
 * would be easily handled in cases where a view demands them more favorably than intervals e.g.
 * editor view). An interface of animation model that prints out the description of all the commands
 * of all the shapes. Each has starting state and ending state which include starting second,
 * position, color, and dimension.
 */
public interface AnimationModel {
  /**
   * Prints out the description of all the commands of all the shapes. Each has starting state and
   * ending state which include starting second, position, color, and dimension.
   *
   * @return String form of the description of the actions
   */
  String getState();


  /**
   * Adds {@code s} to this list of shapes only if the list does not already contain {@code s}.
   *
   * @param s shape that is going to be added
   * @throws IllegalArgumentException if {@code s} is already in the list of shapes.
   */
  void addShape(Shapes s) throws IllegalArgumentException;

  /**
   * Gets this list of shapes.
   *
   * @return the list of shapes in the animation
   */
  List<Shapes> getShapes();

  /**
   * Removes the given shape {@code s} in this list of shapes and its commands if found.
   */
  void removeShape(Shapes s) throws IllegalArgumentException;


  /**
   * Gets this list of commands.
   *
   * @return the list of commands in this animation
   */
  List<Command> getCommand();

  /**
   * Removes the given command {@code c} in the list of commands if found.
   */
  void removeCommand(Command c) throws IllegalArgumentException;

  /**
   * Adds {@code c} to this list of commands if it doesn't already exist there.
   *
   * @throws IllegalArgumentException if the command already exists in this list of commands.
   */
  void addCommand(Command c) throws IllegalArgumentException;

  /**
   * Gets a list of shapes with mutated fields at the certain tick.
   *
   * @param tick current second
   * @return list of shapes that should be drawn on the canvas
   */
  List<IReadOnlyShapes> getAnimatedShapes(int tick);

  /**
   * Gets the canvas from the animation model.
   *
   * @return the canvas.
   */
  Screen getCanvas();

  /**
   * Gets the last tick of all the shapes from the animation model.
   *
   * @return the last tick of all the shapes
   */
  int getLastTime();

  /**
   * Gets this list of shapes in a form of IReadOnlyShapes.
   *
   * @return the list of shapes in the animation in a form of IReadOnlyShapes
   */
  List<IReadOnlyShapes> convertShapes();

  /**
   * adds a keyframe to the given shape.
   *
   * @param name  the name of the shape
   * @param stuff the information of the keyframe
   * @throws IllegalArgumentException if no shape is found
   */
  void addKeyFrameToShape(String name, int[] stuff) throws IllegalArgumentException;

  /**
   * Removes a keyframe from the given shape.
   *
   * @param id  the given shape's name
   * @param key the keyframe that should be removed
   * @throws IllegalArgumentException if no shape or keyframe is found
   */
  void removeKeyFrameFromShape(String id, int key) throws IllegalArgumentException;

  /**
   * Edits the given keyframe.
   *
   * @param id    the name of the shape
   * @param key   the given keyframe
   * @param stuff the information of the keyframe
   * @throws IllegalArgumentException if the shape of keyframe is found
   */
  void editKeyFrame(String id, int key, int[] stuff) throws IllegalArgumentException;
}


