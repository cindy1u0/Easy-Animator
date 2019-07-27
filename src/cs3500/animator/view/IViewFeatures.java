package cs3500.animator.view;

import java.util.List;

import javax.swing.*;

import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.model.shapes.Shapes;

/**
 * The interface IViewFeatures gives functionality that the editorView can use including run, pause,
 * resume, restart, and edit shapes etc.
 */
public interface IViewFeatures {
  /**
   * Starts the animations.
   */
  void start();

  /**
   * Pauses the animations.
   */
  void paused();

  /**
   * Resumes the animations.
   */
  void resume();

  /**
   * Restarts the animations.
   */
  void restart();

  /**
   * Changes the speed of the animations to the given speed.
   *
   * @param value the given speed
   */
  void changeSpeed(int value);

  /**
   * Adds a shape to the animations.
   *
   * @param s the given shape
   * @throws IllegalArgumentException the shape can't be added due to duplicate shapes.
   */
  void addShape(Shapes s) throws IllegalArgumentException;

  /**
   * Removes the given shape.
   *
   * @param id the given shape's name
   * @throws IllegalArgumentException if the given shape is not found.
   */
  void removeShape(String id) throws IllegalArgumentException;

  /**
   * Loops the animations.
   *
   * @param value indicates if the user wants to loop
   */
  void loop(boolean value);

  /**
   * Adds a key frame to the given shape.
   *
   * @param idShape the given shape's name
   * @param stuff   the information of the keyframe
   * @throws IllegalArgumentException if the given shape is not found or the information of the
   *                                  keyframe is not valid
   */
  void addKeyFrame(String idShape, int[] stuff) throws IllegalArgumentException;

  /**
   * Removes the keyframe from the given shape.
   *
   * @param id  the given shape's name
   * @param key the current selected key
   * @throws IllegalArgumentException if the given shape is not found or the information of the
   *                                  keyframe is not valid
   */
  void removeKeyFrame(String id, int key) throws IllegalArgumentException;

  /**
   * Edits the selected keyframe of a shape.
   *
   * @param id    the given shape's name
   * @param key   the current selected key
   * @param stuff the information of the keyframe
   * @throws IllegalArgumentException if the given shape is not found or the information of the
   *                                  keyframe is not valid
   */
  void editKeyFrame(String id, int key, int[] stuff) throws IllegalArgumentException;

  /**
   * Produces a list of shapes from the model.
   *
   * @return a list of shapes
   */
  List<IReadOnlyShapes> getControllerShape();

  /**
   * Produces a list of keyframes associated with the given shape.
   *
   * @param id given shape's name
   * @return a list of keyframes associated with the shape
   */
  List<String> getControllerMotion(String id);

  /**
   * Sets the slider's max value to the model's last time and allows the user to scrub the
   * animation.
   *
   * @param slider the given slider
   * @param value  slider's current value
   */
  void scrub(JSlider slider, int value);
}

