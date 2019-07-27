package cs3500.animator.model.shapes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import cs3500.animator.model.tools.IntegerComparator;
import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;
import cs3500.animator.model.tools.TreeComparator;

import static cs3500.animator.model.shapes.ShapeType.OVAL;

/**
 * (CHANGE 1: We got rid of the appearing and disappearing time because it was not necessary for
 * this since the model will start moving at its first movement. We also added another invariant
 * method to check the added intervals do not overlap) (CHANGE 2: For the editor view
 * implementation, key frames were used and added as a field in this class for tweening rather than
 * intervals since they double upped the motion states.) An abstract class that represents a shape
 * model implementation. It contains all the intervals and corresponding list of comments, current
 * position, color, dimension, and stored description.
 */
public abstract class AbstractShapes implements Shapes {
  private double rotation;
  private String name;
  private Posn2D position;
  private RGB color;
  private Size size;
  private TreeMap<Interval, double[]> history;
  private TreeMap<Integer, double[]> keyFrame;
  private ShapeType st;

  /**
   * A constructor that initializes the current position, color, and dimension to the given ones.
   *
   * @param name  name
   * @param color the current color of the shape
   * @param size  the current size of the shape
   */
  protected AbstractShapes(double ro, String name, Posn2D position, RGB color, Size size,
                           ShapeType st) {
    ensureNull(position, color, size);
    this.rotation = ro;
    this.name = name;
    this.position = position;
    this.color = color;
    this.size = size;
    this.st = st;
    ensureSize();
    this.history = new TreeMap<>(new TreeComparator());
    this.keyFrame = new TreeMap<>(new IntegerComparator());
    checkOverlap();
  }

  /**
   * Ensures this class' dimensions are positive in order to be created as an actual shape.
   *
   * @throws IllegalArgumentException if either width or height of this dimension is non-positive
   */
  private void ensureSize() throws IllegalArgumentException {
    if (this.size.getHeight() <= 0 || this.size.getWidth() <= 0) {
      throw new IllegalArgumentException("Boi you can't make a shape with this");
    }
  }

  /**
   * Ensures the given objects are not instances of null.
   *
   * @param obs multiple objects that needs to be null checked
   * @throws IllegalArgumentException if any of the objects in {@code obs} are null
   */
  public static void ensureNull(Object... obs) throws IllegalArgumentException {
    for (Object ob : obs) {
      try {
        Objects.requireNonNull(ob);
      } catch (NullPointerException e) {
        throw new IllegalArgumentException("No null ples");
      }
    }
  }

  /**
   * Checks if there are overlapping intervals in the tree map.
   *
   * @throws IllegalArgumentException when there are overlapping intervals somewhere
   */
  @Override
  public void checkOverlap() throws IllegalArgumentException {
    Set<Interval> workList = new TreeSet<>(new TreeComparator());
    workList.addAll(this.history.keySet());
    List<Interval> keys = new ArrayList<>(workList);
    for (int i = 0; i < keys.size(); i++) {
      for (int j = 0; j < keys.size(); j++) {
        if (i != j && keys.get(j).doesOverlap(keys.get(i))) {
          throw new IllegalArgumentException("Two actions can't happen at the same time!");
        }
      }
    }
  }


  /**
   * Changes the current position to the other position, i.e. moves the shape from current position
   * to the given position.
   *
   * @param p the position that the shape is moving to
   */
  @Override
  public void changePosition(Posn2D p) throws IllegalArgumentException {
    ensureNull(p);
    this.position.setPosn(p);
  }

  /**
   * Changes the current color to the given color.
   *
   * @param color the color that the user wants to change it too
   */
  @Override
  public void changeColor(RGB color) throws IllegalArgumentException {
    ensureNull(color);
    this.color.setRGB(color);
  }

  /**
   * Changes the current dimension to the given dimension, i.e. shrinking the shape or enlarging the
   * shape.
   *
   * @param s the dimension that the user wants to change it too
   */
  @Override
  public void changeSize(Size s) throws IllegalArgumentException {
    ensureNull(s);
    this.size.setSize(s);
  }

  /**
   * Gets the x and y position of this shape.
   *
   * @return the position of the shape
   */
  @Override
  public Posn2D getPosition() {
    return this.position;
  }

  /**
   * Gets the RGB color of this shape.
   *
   * @return the color of the shape
   */
  @Override
  public RGB getColor() {
    return this.color;
  }

  /**
   * Gets the size in terms of width and height of this shape.
   *
   * @return the size of the shape
   */
  @Override
  public Size getSize() {
    return this.size;
  }

  /**
   * Gets the String name of this shape.
   *
   * @return the name of the shape
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Gets the type of the shape.
   *
   * @return the type of the shape
   */
  @Override
  public ShapeType getShapeType() {
    return this.st;
  }


  /**
   * @return rotation value
   */
  @Override
  public double getRotation() {
    return this.rotation;
  }


  /**
   * Gets the history of commands and intervals applied to this shape in a TreeMap.
   *
   * @return a TreeMap containing the commands and its intervals applied to this shape.
   */
  @Override
  public TreeMap<Interval, double[]> getHistory() {
    return this.history;
  }

  /**
   * Checks if the given Object is a Shape with the same name and type as this.
   *
   * @return true if {@code o} is the same instance, name, and shapetype as this shape
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Shapes)) {
      return false;
    }

    Shapes that = (Shapes) o;
    return this.name.equals(that.getName());
  }


  /**
   * Gives the hashcode of this shape in terms of its size, color, position, and visibility.
   *
   * @return the hashcode of this shape's properties.
   */
  @Override
  public int hashCode() {
    return Objects.hash(color, size, position, name, st);
  }

  /**
   * Retrieves one state of this shape given an interval.
   *
   * @param frame the Interval in which a command may have applied to this shape
   * @return a String describing the command applied to this shape
   */
  private String getOneState(Interval frame) {
    ensureNull(frame);
    double[] workList = this.history.get(frame);
    StringBuilder state = new StringBuilder("motion " + this.name + " ");
    for (double d : workList) {
      state.append(Math.round(d));
      state.append(" ");
    }
    return state.toString();
  }

  /**
   * Retrieves all the states this shape has gone through.
   *
   * @return a String representation of the commands applied to this shape and interval occurrences
   */
  @Override
  public String getShapeState() {
    StringBuilder sb = new StringBuilder();
    for (Interval in : this.history.keySet()) {
      sb.append(getOneState(in));
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Goes through this TreeMap to ensure any gaps between states are filled with states representing
   * this Shape is motionless at those intervals as well as filling states in between appearing time
   * and first movement, and in between last movement and disappearing time.
   */
  @Override
  public void checkGap() {
    Set<Interval> workList = new TreeSet<>(new TreeComparator());
    workList.addAll(this.history.keySet());
    List<Interval> keys = new ArrayList<>(workList);
    for (int i = 0; i < keys.size() - 1; i++) {
      double[] old = this.history.get(keys.get(i));
      double[] newIntervals = old.clone();
      if (keys.get(i).getEnding() < keys.get(i + 1).getStarting()) {
        newIntervals[0] = keys.get(i).getEnding();
        newIntervals[8] = keys.get(i + 1).getStarting();
        initState(newIntervals, old);
        this.history.put(new Interval(keys.get(i).getEnding(),
                keys.get(i + 1).getStarting()), newIntervals);
      }
    }
  }

  /**
   * Initializes the state {@code temp} size given an array and a boolean value. If boolean is true,
   * then {@code temp} first half of its array is set to {@code arr} second half and vice versa.
   *
   * @param temp the array to be initialized
   * @param arr  the array to be used for fill {@code temp} state partially
   */
  private void initState(double[] temp, double[] arr) {
    for (int i = 1; i < 8; i++) {
      temp[i] = arr[i + 8];
    }
  }

  private static IReadOnlyShapes newReference(double ro, Shapes model, Posn2D pos, RGB color,
                                              Size s) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(pos);
    Objects.requireNonNull(color);
    Objects.requireNonNull(s);
    if (model.getShapeType() == ShapeType.RECT) {
      return new Rect(ro, model.getName(), pos, color, s);
    } else {
      return new Oval(ro, model.getName(), pos, color, s);
    }
  }

  private IReadOnlyShapes getShapesAtInterval(int tick, int start, int end) {
    IReadOnlyShapes shapesToReturn;
    double[] initWork = this.keyFrame.get(start);
    double[] finalWork = this.keyFrame.get(end);
    int frame = end - start;
    double f1 = (double) (end - tick) / frame;
    double f2 = (double) (tick - start) / frame;
    // new position of the shape at the particular tick
    long speedX = Math.round(initWork[1] * f1 + finalWork[1] * f2);
    long speedY = Math.round(initWork[2] * f1 + finalWork[2] * f2);
    Posn2D newPos = new Posn2D(speedX, speedY);
    // new size of the shape at the particular tick
    long sizeX = Math.round(initWork[3] * f1 + finalWork[3] * f2);
    long sizeY = Math.round(initWork[4] * f1 + finalWork[4] * f2);
    Size newSize = new Size(sizeX, sizeY);
    // new color of the shape at the particular tick
    long redChange = Math.round(initWork[5] * f1 + finalWork[5] * f2);
    long greenChange = Math.round(initWork[6] * f1 + finalWork[6] * f2);
    long blueChange = Math.round(initWork[7] * f1 + finalWork[7] * f2);
    RGB newRGB = new RGB(redChange, greenChange, blueChange);

    double roChange = initWork[8] * f1 + finalWork[8] * f2;
    shapesToReturn = newReference(roChange, this, newPos, newRGB, newSize);

    return shapesToReturn;
  }

  private int getShapeFirstTime() {
    return this.history.firstKey().getStarting();
  }

  /**
   * Produces the last tick of the shape that disappears.
   *
   * @return the very last tick of all the shapes
   */
  @Override
  public int getShapeLastTime() {
    Set<Interval> workList = new TreeSet<>(new TreeComparator());
    workList.addAll(this.history.keySet());
    List<Interval> keys = new ArrayList<>(workList);
    return (this.history.size() > 0) ? keys.get(keys.size() - 1).getEnding() : 0;
  }

  /**
   * Gets the mutated shape at the certain tick.
   *
   * @param tick current tick
   * @return the shape with corresponding fields' values at that tick
   */
  @Override
  public IReadOnlyShapes getShapesAtTick(int tick) {
    Set<Integer> workList = new TreeSet<>(new IntegerComparator());
    workList.addAll(this.keyFrame.keySet());
    List<Integer> keys = new ArrayList<>(workList);
    IReadOnlyShapes shapesToReturn = null;
    for (int i = 0; i < keys.size() - 1; i++) {
      if (tick >= keys.get(i) && tick < keys.get(i + 1)) {
        shapesToReturn = getShapesAtInterval(tick, keys.get(i), keys.get(i + 1));
      }
    }
    return shapesToReturn;
  }

  /**
   * Reformats the shape's description according to the SVG Tag.
   *
   * @return return the SVG Tag of the shape
   */
  @Override
  public String getSVGTag(int tick) {
    StringBuilder sb = new StringBuilder();

    if (history.size() > 0) {
      sb.append(String.format("<%s id=\"%s\" %s fill=\"%s\" visibility=\"hidden\">\n",
              shapeString(), this.name, shapeProp(true),
              colorTag(true)));

      sb.append(String.format("<animate attributeType=\"xml\" begin=\"%1$,.2fs\" dur=\"%2$,.2fs\"" +
                      " attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n",
              getShapeFirstTime() / (double) tick, getShapeLastTime() / (double) tick));
      for (Interval i : history.keySet()) {
        sb.append(intervalTag(i, tick));
      }
    } else {
      sb.append(String.format("<%s id=\"%s\" %s fill=\"%s\" visibility=\"hidden\">\n",
              shapeString(), this.name, shapeProp(false),
              colorTag(false)));
    }
    sb.append(String.format("</%s>\n", shapeString()));
    return sb.toString();
  }

  private String intervalTag(Interval i, int tick) {
    Objects.requireNonNull(i);
    StringBuilder sb = new StringBuilder();
    String[] shapeUnits = getShapeUnits();

    for (int index = 0; index < 4; index++) {
      sb.append(partialChange(i, shapeUnits[index], index + 1, tick));
    }
    sb.append(partialChange(i, "fill", 5, tick));
    sb.append(partialChange(i, "rotate", 8, tick));

    return sb.toString();
  }

  private String partialChange(Interval i, String s, int index, int tick) {
    Objects.requireNonNull(i);
    Objects.requireNonNull(s);
    StringBuilder sb = new StringBuilder();
    int duration = i.getEnding() - i.getStarting();
    double[] states = history.get(i);

    if (s.equals("fill") && ((states[index] != states[index + 8])
            || (states[index + 1] != states[index + 9])
            || (states[index + 2] != states[index + 10]))) {
      sb.append(String.format("<animate attributeType=\"xml\" begin=\"%1$,.2fs\" dur=\"%2$,.2fs\"" +
                      " attributeName=\"fill\" from=\"%3$s\" to=\"%4$s\" fill=\"freeze\"/>\n",
              i.getStarting() / (double) tick, duration / (double) tick,
              intervalColor(i, true),
              intervalColor(i, false)));

    } else if (s.equals("rotate") && (states[index] != states[index + 8])) {
      sb.append(String.format("<animateTransform attributeType=\"xml\" begin=\"%1$,.2fs\" " +
                      "dur=\"%2$,.2fs\" attributeName=\"transform\" type=\"rotate\" " +
                      "from=\"%3$,.2f %4$,.2f %5$,.2f\" to=\"%6$,.2f %7$,.2f %8$,.2f\" fill=\"freeze\" />",
              i.getStarting() / (double) tick, duration / (double) tick, states[index],
              states[1] + (states[3] / 2), states[2] + (states[4] / 2), states[index + 8],
              states[9] + (states[11] / 2), states[10] + (states[12] / 2)));

    } else {
      if (states[index] != states[index + 8]) {
        sb.append(String.format("<animate attributeType=\"xml\" begin=\"%1$,.2fs\" dur=\"%2$," +
                        ".2fs\" attributeName=\"%3$s\" from=\"%4$,.2f\" to=\"%5$,.2f\" " +
                        "fill=\"freeze\"/>\n",
                i.getStarting() / (double) tick, duration / (double) tick, s, states[index],
                states[index + 8]));
      }
    }

    return sb.toString();
  }

  private String intervalColor(Interval i, boolean state) {
    Objects.requireNonNull(i);
    double[] work = history.get(i);

    return state ? String.format("rgb(%1$d,%2$d,%3$d)", (int) work[5], (int) work[6], (int) work[7])
            : String.format("rgb(%1$d,%2$d,%3$d)", (int) work[13], (int) work[14], (int) work[15]);
  }

  private String shapeProp(boolean b) {
    String[] shapeUnits = getShapeUnits();
    double[] firstState;
    double width;
    double height;
    double[] values;

    firstState = b ? history.get(history.firstKey()) : new double[]{};

    if (this.st.equals(OVAL)) {
      width = b ? firstState[3] / 2 : this.size.getWidth() / 2;
      height = b ? firstState[4] / 2 : this.size.getHeight() / 2;
    } else {
      width = b ? firstState[3] : this.size.getWidth();
      height = b ? firstState[4] : this.size.getHeight();
    }

    values = b ? new double[]{firstState[1], firstState[2]} :
            new double[]{this.position.getX(), this.position.getY()};

    return String.format("%1$s=\"%2$,.2f\" %3$s=\"%4$,.2f\" %5$s=\"%6$,.2f\" "
                    + "%7$s=\"%8$,.2f\"", shapeUnits[0], values[0],
            shapeUnits[1], values[1],
            shapeUnits[2], width,
            shapeUnits[3], height);

  }

  private String colorTag(boolean b) {
    int red;
    int green;
    int blue;

    if (b) {
      double[] firstState = history.get(history.firstKey());
      red = (int) firstState[5];
      green = (int) firstState[6];
      blue = (int) firstState[7];
    } else {
      red = (int) this.color.getRed();
      green = (int) this.color.getGreen();
      blue = (int) this.color.getBlue();
    }

    return "rgb(" + red + "," + green + "," + blue + ")";
  }

  /**
   * Gets the shape description.
   *
   * @return a string of the description of the shape
   */
  protected abstract String shapeString();

  /**
   * Provides the small SVG tags.
   *
   * @return an array of small SVG tags
   */
  protected abstract String[] getShapeUnits();

  private String getPartialKeyFrame(Integer in) {
    Objects.requireNonNull(in);
    StringBuilder sb = new StringBuilder();
    double[] work = this.keyFrame.get(in);
    sb.append(String.format("t=%d, pos=(%d,%d), size=(%d,%d), color=(%d,%d,%d)",
            (int) work[0], (int) work[1], (int) work[2], (int) work[3], (int) work[4],
            (int) work[5], (int) work[6], (int) work[7]));
    return sb.toString();
  }

  /**
   * Converts the keyframe into a String.
   *
   * @return the keyframe in a string form
   */
  public List<String> getKeyFrame() {
    Set<Integer> workList = new TreeSet<>(new IntegerComparator());
    workList.addAll(this.keyFrame.keySet());
    List<Integer> keys = new ArrayList<>(workList);
    List<String> work = new ArrayList<String>();
    for (Integer in : keys) {
      work.add(getPartialKeyFrame(in));
    }
    return work;
  }

  /**
   * Getter to get the frame field.
   *
   * @return the keyFrame field
   */
  public TreeMap<Integer, double[]> getFrames() {
    return this.keyFrame;
  }
}




