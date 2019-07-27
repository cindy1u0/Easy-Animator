package cs3500.animator.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.model.shapes.Shapes;
import cs3500.animator.view.IView;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.view.IViewFeatures;

/**
 * (CHANGES: the controller implemented the operations of IViewFeatures for the implementation of
 * the EditorView, so that it would only be called on that view rather than text, visual, or svg.
 * Moreover, constructor was modified slightly by adding itself as a "listener" to the given view if
 * it was a EditorView.) A controller interface that enables the program to output the description
 * of the shapes, draws the animation on a canvas, write the SVG form, or edit the animations.
 */
public class ControllerImpl implements Controller, IViewFeatures {
  private final Timer timer;
  private int tick = 0;
  private final AnimationModel model;
  private final IView view;
  private boolean loop = false;

  /**
   * A constructor that does the operations to the specific model with the given view and tempo.
   *
   * @param model given Animation model to be made into animation
   * @param view  three views including text view, visual view, or SVG view
   */
  public ControllerImpl(AnimationModel model, IView view)
          throws IllegalArgumentException {
    Objects.requireNonNull(model, "Given model cannot be read");
    Objects.requireNonNull(view, "Given view cannot be read");
    this.model = model;
    this.view = view;
    try {
      view.addListener(this);
    } catch (UnsupportedOperationException e) {
      // do nothing
    }
    this.timer = new Timer((int) ((1.0 / view.getTempo()) * 1000), ((e) -> {
      List<IReadOnlyShapes> shapesToRender = model.getAnimatedShapes(tick++);
      view.render(shapesToRender);
      if (loop && tick > model.getLastTime()) {
        tick = 0;
      }
    }));
    view.setCanvas(model.getCanvas());
  }

  /**
   * The method that will render the text, draw the shapes, or write a SVG file.
   */
  @Override
  public void run(Appendable ap) throws IllegalStateException {
    Objects.requireNonNull(ap);
    List<IReadOnlyShapes> shapesToRender = model.convertShapes();
    view.render(shapesToRender);

    if (!view.hasListener()) {
      timer.start();
    }

    try {
      ap.append(view.getText());
    } catch (IOException e) {
      throw new IllegalStateException("cannot append to this output");
    }
  }

  /**
   * Starts the animations.
   */
  @Override
  public void start() {
    if (tick == 0) {
      timer.start();
    }
    if (tick > model.getLastTime()) {
      tick = 0;
    }
  }

  /**
   * Pauses the animations.
   */
  @Override
  public void paused() {
    timer.stop();
  }

  /**
   * Resumes the animations.
   */
  @Override
  public void resume() {
    if (tick > 0) {
      timer.start();
    }
  }

  /**
   * Restarts the animations.
   */
  @Override
  public void restart() {
    tick = 0;
    timer.start();
  }

  /**
   * Changes the speed of the animations to the given speed.
   *
   * @param value the given speed
   */
  @Override
  public void changeSpeed(int value) {
    this.timer.setDelay((int) ((1.0 / value * 1000)));
  }

  /**
   * Adds a shape to the animations.
   *
   * @param s the given shape
   * @throws IllegalArgumentException the shape can't be added due to duplicate shapes.
   */
  @Override
  public void addShape(Shapes s) throws IllegalArgumentException {
    model.addShape(s);
  }

  /**
   * Removes the given shape.
   *
   * @param id the given shape's name
   * @throws IllegalArgumentException if the given shape is not found.
   */
  @Override
  public void removeShape(String id) throws IllegalArgumentException {
    Shapes temp = null;
    for (Shapes s : model.getShapes()) {
      if (s.getName().equals(id)) {
        temp = s;
      }
    }
    model.removeShape(temp);
  }

  /**
   * Loops the animations.
   *
   * @param value indicates if the user wants to loop
   */
  @Override
  public void loop(boolean value) {
    loop = value;
    if (value && model.getLastTime() < tick) {
      timer.stop();
      tick = 0;
    }
  }

  /**
   * Adds a key frame to the given shape.
   *
   * @param idShape the given shape's name
   * @param work    the information of the keyframe
   * @throws IllegalArgumentException if the given shape is not found or the information of the
   *                                  keyframe is not valid
   */
  @Override
  public void addKeyFrame(String idShape, int[] work) throws IllegalArgumentException {
    Objects.requireNonNull(work);
    model.addKeyFrameToShape(idShape, work);
  }

  /**
   * Removes the keyframe from the given shape.
   *
   * @param id  the given shape's name
   * @param key the current selected key
   * @throws IllegalArgumentException if the given shape is not found or the information of the
   *                                  keyframe is not valid
   */
  @Override
  public void removeKeyFrame(String id, int key) throws IllegalArgumentException {
    model.removeKeyFrameFromShape(id, key);
  }

  /**
   * Edits the selected keyframe of a shape.
   *
   * @param id    the given shape's name
   * @param key   the current selected key
   * @param stuff the information of the keyframe
   * @throws IllegalArgumentException if the given shape is not found or the information of the
   *                                  keyframe is not valid
   */
  @Override
  public void editKeyFrame(String id, int key, int[] stuff) throws IllegalArgumentException {
    model.editKeyFrame(id, key, stuff);
  }

  /**
   * Produces a list of shapes from the model.
   *
   * @return a list of shapes
   */
  public List<IReadOnlyShapes> getControllerShape() {
    return model.convertShapes();
  }

  /**
   * Produces a list of keyframes associated with the given shape.
   *
   * @param id given shape's name
   * @return a list of keyframes associated with the shape
   */
  public List<String> getControllerMotion(String id) {
    List<String> work = new ArrayList<>();
    for (IReadOnlyShapes sh : model.convertShapes()) {
      if (sh.getName().equals(id)) {
        work.addAll(sh.getKeyFrame());
      }
    }
    return work;
  }


  /**
   * Sets the slider's max value to the model's last time and allows the user to scrub the
   * animation.
   *
   * @param slider the given slider
   * @param value  slider's current value
   */
  public void scrub(JSlider slider, int value) {
    Objects.requireNonNull(slider);
    slider.setMaximum(model.getLastTime() - 1);
    tick = value;
    timer.stop();
    List<IReadOnlyShapes> shapesToRender = model.getAnimatedShapes(value);
    view.render(shapesToRender);
  }
}


