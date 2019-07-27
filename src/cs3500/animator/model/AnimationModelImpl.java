package cs3500.animator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.animator.model.commands.Command;
import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.model.shapes.Oval;
import cs3500.animator.model.shapes.Rect;
import cs3500.animator.model.shapes.ShapeType;
import cs3500.animator.model.shapes.Shapes;
import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Screen;
import cs3500.animator.model.tools.Size;
import cs3500.animator.util.AnimationBuilder;

/**
 * (CHANGE 1: We used a builder class this time to construct the model instead of adding shapes and
 * commands each time as well as adding a few methods that are adaptable to the program) (CHANGE 2:
 * The model interface now includes operations relating to keyframes, so that tweening and motions
 * would be easily handled in cases where a view demands them more favorably than intervals e.g.
 * editor view). An interface of animation model that prints out the description of all the commands
 * of all the shapes. Each has starting state and ending state which include starting second,
 * position, color, and dimension.
 */
public class AnimationModelImpl implements AnimationModel {
  private List<Shapes> shapes;
  private List<Command> commands;
  private Screen canvas;

  /**
   * A constructor for AnimationModelImpl in of a list of shapes and commands.
   */
  public AnimationModelImpl(List<Shapes> shapes, List<Command> commands) {
    this.shapes = shapes;
    this.commands = commands;
    badOverlap();
    dupShapes();
    putCommandsInShapes();
    fillGaps();
    this.canvas = new Screen(0, 0, 700, 700);
  }

  /**
   * A constructor for AnimationModelImpl in of a list of shapes and commands.
   */
  public AnimationModelImpl(List<Shapes> shapes, List<Command> commands, Screen canvas) {
    this.shapes = shapes;
    this.commands = commands;
    badOverlap();
    dupShapes();
    putCommandsInShapes();
    fillGaps();
    this.canvas = canvas;
  }

  /**
   * A builder class that enables the program to read the existing files and convert the information
   * to an Animation model.
   */
  public static final class Builder implements AnimationBuilder<AnimationModel> {
    private final List<Shapes> shapes = new ArrayList<>();
    private final List<Command> commands = new ArrayList<>();
    private Screen canvas = new Screen(0, 0, 500, 500);

    /**
     * Builds an actual animation model.
     *
     * @return a modified animation model that has all the information converted from the file.
     */
    @Override
    public AnimationModel build() {
      return new AnimationModelImpl(shapes, commands, canvas);
    }

    /**
     * Specify the bounding box to be used for the animation.
     *
     * @param x      The leftmost x value
     * @param y      The topmost y value
     * @param width  The width of the bounding box
     * @param height The height of the bounding box
     * @return convertible builder so that it can be modified again
     */
    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      this.canvas = new Screen(x, y, width, height);
      return this;
    }

    /**
     * Adds a new shape to the growing document.
     *
     * @param name The unique name of the shape to be added. No shape with this name should already
     *             exist.
     * @param type The type of shape (e.g. "ellipse", "rectangle") to be added. The set of supported
     *             shapes is unspecified, but should include "ellipse" and "rectangle" as a
     *             minimum.
     * @return convertible builder so that it can be modified again
     */
    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type) {
      switch (type) {
        case "rectangle":
          this.shapes.add(new Rect(name));
          break;
        case "ellipse":
          this.shapes.add(new Oval(name));
          break;
        default:
          break;
      }
      return this;
    }

    /**
     * Adds a transformation to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t1   The start time of this transformation
     * @param x1   The initial x-position of the shape
     * @param y1   The initial y-position of the shape
     * @param w1   The initial width of the shape
     * @param h1   The initial height of the shape
     * @param r1   The initial red color-value of the shape
     * @param g1   The initial green color-value of the shape
     * @param b1   The initial blue color-value of the shape
     * @param t2   The end time of this transformation
     * @param x2   The final x-position of the shape
     * @param y2   The final y-position of the shape
     * @param w2   The final width of the shape
     * @param h2   The final height of the shape
     * @param r2   The final red color-value of the shape
     * @param g2   The final green color-value of the shape
     * @param b2   The final blue color-value of the shape
     * @return convertible builder so that it can be modified again
     */
    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
                                                      int h1, int r1, int g1, int b1, int t2,
                                                      int x2, int y2, int w2, int h2, int r2,
                                                      int g2, int b2) {
      double[] val = new double[18];
      val[0] = t1;
      val[1] = x1;
      val[2] = y1;
      val[3] = w1;
      val[4] = h1;
      val[5] = r1;
      val[6] = g1;
      val[7] = b1;
      val[8] = 0;
      val[9] = t2;
      val[10] = x2;
      val[11] = y2;
      val[12] = w2;
      val[13] = h2;
      val[14] = r2;
      val[15] = g2;
      val[16] = b2;
      val[17] = 0;
      double[] start = new double[]{t1, x1, y1, w1, h1, r1, g1, b1, 0};
      double[] end = new double[]{t2, x2, y2, w2, h2, r2, g2, b2, 0};
      for (Shapes sh : shapes) {
        if (sh.getName().equals(name)) {
          sh.getHistory().put(new Interval(t1, t2), val);
          sh.checkOverlap();
          if (!sh.getFrames().containsKey(t1)) {
            sh.getFrames().put(t1, start);
          }
          if (!sh.getFrames().containsKey(t2)) {
            sh.getFrames().put(t2, end);
          }
        }
      }
      return this;
    }

    /**
     * Adds a transformation to the growing document including rotation.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t1   The start time of this transformation
     * @param x1   The initial x-position of the shape
     * @param y1   The initial y-position of the shape
     * @param w1   The initial width of the shape
     * @param h1   The initial height of the shape
     * @param r1   The initial red color-value of the shape
     * @param g1   The initial green color-value of the shape
     * @param b1   The initial blue color-value of the shape
     * @param ro1  The initial rotation
     * @param t2   The end time of this transformation
     * @param x2   The final x-position of the shape
     * @param y2   The final y-position of the shape
     * @param w2   The final width of the shape
     * @param h2   The final height of the shape
     * @param r2   The final red color-value of the shape
     * @param g2   The final green color-value of the shape
     * @param b2   The final blue color-value of the shape
     * @param ro2  The final rotation
     * @return This {@link AnimationBuilder}
     */
    @Override
    public AnimationBuilder<AnimationModel> addMotionRotation(String name, int t1, int x1, int y1,
                                                              int w1, int h1, int r1, int g1,
                                                              int b1, int ro1, int t2, int x2,
                                                              int y2, int w2, int h2, int r2,
                                                              int g2, int b2, int ro2) {
      double[] val = new double[18];
      val[0] = t1;
      val[1] = x1;
      val[2] = y1;
      val[3] = w1;
      val[4] = h1;
      val[5] = r1;
      val[6] = g1;
      val[7] = b1;
      val[8] = ro1;
      val[9] = t2;
      val[10] = x2;
      val[11] = y2;
      val[12] = w2;
      val[13] = h2;
      val[14] = r2;
      val[15] = g2;
      val[16] = b2;
      val[17] = ro2;
      double[] start = new double[]{t1, x1, y1, w1, h1, r1, g1, b1, ro1};
      double[] end = new double[]{t2, x2, y2, w2, h2, r2, g2, b2, ro2};
      for (Shapes sh : shapes) {
        if (sh.getName().equals(name)) {
          sh.getHistory().put(new Interval(t1, t2), val);
          sh.checkOverlap();
          if (!sh.getFrames().containsKey(t1)) {
            sh.getFrames().put(t1, start);
          }
          if (!sh.getFrames().containsKey(t2)) {
            sh.getFrames().put(t2, end);
          }
        }
      }
      return this;
    }

    /**
     * Adds an individual keyframe to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t    The time for this keyframe
     * @param x    The x-position of the shape
     * @param y    The y-position of the shape
     * @param w    The width of the shape
     * @param h    The height of the shape
     * @param r    The red color-value of the shape
     * @param g    The green color-value of the shape
     * @param b    The blue color-value of the shape
     * @return convertible builder so that it can be modified again
     */
    @Override
    public AnimationBuilder<AnimationModel> addKeyframe(String name, int t, int x, int y, int w,
                                                        int h, int r, int g, int b) {
      double[] start = new double[]{t, x, y, w, h, r, g, b};
      for (Shapes sh : shapes) {
        if (sh.getName().equals(name)) {
          if (!sh.getFrames().containsKey(t)) {
            sh.getFrames().put(t, start);
          }
        }
      }
      return this;
    }
  }

  private void fillGaps() {
    for (Shapes sh : shapes) {
      sh.checkGap();
    }
  }

  private void badOverlap() throws IllegalArgumentException {
    for (int i = 0; i < commands.size(); i++) {
      for (int j = 0; j < commands.size(); j++) {
        if (i != j && commands.get(j).getFrame().doesOverlap(commands.get(i).getFrame())
                && commands.get(j).getType() == commands.get(i).getType()
                && commands.get(j).getShape().equals(commands.get(i).getShape())) {
          throw new IllegalArgumentException("Two actions can't happen at the same time!");
        }
      }
    }
  }

  private void dupShapes() throws IllegalArgumentException {
    for (int i = 0; i < shapes.size(); i++) {
      for (int j = 0; j < shapes.size(); j++) {
        if (i != j && shapes.get(i).equals(shapes.get(j))) {
          throw new IllegalArgumentException("Two of the same exact shapes can't exist");
        }
      }
    }
  }

  private void putCommandsInShapes() {
    for (Command command : commands) {
      command.follow();
    }
  }

  /**
   * Prints out the description of all the commands of all the shapes. Each has starting state and
   * ending state which include starting second, position, color, and dimension.
   *
   * @return String form of the description of the actions
   */
  @Override
  public String getState() {
    StringBuilder sb = new StringBuilder();
    for (Shapes sh : shapes) {
      if (sh.getHistory().keySet().size() == 0) {
        return "";
      }
      sb.append("shape ");
      sb.append(sh.getName());
      if (sh.getShapeType() == ShapeType.OVAL) {
        sb.append(" Oval\n");
      } else {
        sb.append(" Rectangle\n");
      }
      sb.append(sh.getShapeState());
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Adds {@code s} to this list of shapes only if the list does not already contain {@code s}.
   *
   * @param s shape that is going to be added
   * @throws IllegalArgumentException if {@code s} is already in the list of shapes.
   */
  @Override
  public void addShape(Shapes s) throws IllegalArgumentException {
    Objects.requireNonNull(s);
    for (Shapes sh : shapes) {
      if (s.equals(sh)) {
        throw new IllegalArgumentException("this shape already exists");
      }
    }
    this.shapes.add(s);
  }

  /**
   * Gets this list of shapes.
   *
   * @return the list of shapes in the animation
   */
  @Override
  public List<Shapes> getShapes() {
    return this.shapes;
  }

  /**
   * Removes the given shape {@code s} in this list of shapes and its commands if found.
   */
  @Override
  public void removeShape(Shapes s) throws IllegalArgumentException {
    Objects.requireNonNull(s);

    for (Shapes sh : this.shapes) {
      if (s.equals(sh)) {
        this.shapes.remove(s);
        break;
      }
    }
    for (Command c : commands) {
      if (c.getShape().equals(s)) {
        commands.remove(c);
      }
    }
  }

  /**
   * Gets this list of commands.
   *
   * @return the list of commands in this animation
   */
  @Override
  public List<Command> getCommand() {
    return this.commands;
  }

  /**
   * Removes the given command {@code c} in the list of commands if found.
   */
  @Override
  public void removeCommand(Command c) throws IllegalArgumentException {
    if (c == null) {
      throw new IllegalArgumentException("provided command cannot be read.");
    }
    for (Command co : commands) {
      if (co.equals(c)) {
        commands.remove(co);
      }
    }
  }

  /**
   * Adds {@code c} to this list of commands if it doesn't already exist there.
   *
   * @throws IllegalArgumentException if the command already exists in this list of commands.
   */
  @Override
  public void addCommand(Command c) throws IllegalArgumentException {
    if (c == null) {
      throw new IllegalArgumentException("provided command cannot be read.");
    }
    commands.add(c);
    badOverlap();
  }

  /**
   * Gets a list of shapes with mutated fields at the certain tick.
   *
   * @param tick current second
   * @return list of shapes that should be drawn on the canvas
   */
  @Override
  public List<IReadOnlyShapes> getAnimatedShapes(int tick) {
    List<IReadOnlyShapes> animate = new ArrayList<>();
    for (Shapes sh : shapes) {
      if (sh.getShapesAtTick(tick) != null) {
        animate.add(sh.getShapesAtTick(tick));
      }
    }
    return animate;
  }

  /**
   * Gets the canvas from the animation model.
   *
   * @return the canvas.
   */
  @Override
  public Screen getCanvas() {
    return canvas;
  }

  /**
   * Gets the last tick of all the shapes from the animation model.
   *
   * @return the last tick of all the shapes
   */
  @Override
  public int getLastTime() {
    int maxTime = (shapes.size() > 0) ? shapes.get(0).getShapeLastTime() : 0;
    for (int i = 1; i < this.shapes.size(); i++) {
      if (maxTime < shapes.get(i).getShapeLastTime()) {
        maxTime = shapes.get(i).getShapeLastTime();
      }
    }
    return maxTime;
  }

  /**
   * Gets this list of shapes in a form of IReadOnlyShapes.
   *
   * @return the list of shapes in the animation in a form of IReadOnlyShapes
   */
  @Override
  public List<IReadOnlyShapes> convertShapes() {
    return new ArrayList<>(shapes);
  }


  /**
   * adds a keyframe to the given shape.
   *
   * @param name  the name of the shape
   * @param stuff the information of the keyframe
   * @throws IllegalArgumentException if no shape is found
   */
  @Override
  public void addKeyFrameToShape(String name, int[] stuff) throws IllegalArgumentException {
    Objects.requireNonNull(name);
    try {
      new Size(stuff[3], stuff[4]);
      new RGB(stuff[5], stuff[6], stuff[7]);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
    double[] start = new double[]{stuff[0], stuff[1],
            stuff[2], stuff[3], stuff[4], stuff[5], stuff[6], stuff[7]};
    for (Shapes sh : shapes) {
      if (sh.getName().equals(name)) {
        if (!sh.getFrames().containsKey(stuff[0])) {
          sh.getFrames().put(stuff[0], start);
        } else {
          throw new IllegalArgumentException("the key frame can't be added.");
        }
      }
    }
  }

  /**
   * Removes a keyframe from the given shape.
   *
   * @param name  the given shape's name
   * @param key the keyframe that should be removed
   * @throws IllegalArgumentException if no shape or keyframe is found
   */
  @Override
  public void removeKeyFrameFromShape(String name, int key) throws IllegalArgumentException {
    Objects.requireNonNull(name);
    for (Shapes sh : shapes) {
      if (sh.getName().equals(name)) {
        sh.getFrames().remove(key);
      }
    }
  }

  /**
   * Edits the given keyframe.
   *
   * @param id    the name of the shape
   * @param key   the given keyframe
   * @param stuff the information of the keyframe
   * @throws IllegalArgumentException if the shape of keyframe is found
   */
  @Override
  public void editKeyFrame(String id, int key, int[] stuff) throws IllegalArgumentException {
    Objects.requireNonNull(id);
    try {
      new Size(stuff[3], stuff[4]);
      new RGB(stuff[5], stuff[6], stuff[7]);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
    for (Shapes sh : shapes) {
      if (sh.getName().equals(id)) {
        sh.getFrames().remove(key);
        sh.getFrames().put(stuff[0], new double[]{stuff[0], stuff[1],
                stuff[2], stuff[3], stuff[4], stuff[5], stuff[6], stuff[7]});
      }
    }
  }
}


