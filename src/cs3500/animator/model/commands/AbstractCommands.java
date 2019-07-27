package cs3500.animator.model.commands;

import java.util.TreeMap;

import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.shapes.Shapes;

/**
 * An abstract class for the Command interface and its classes.
 */
public abstract class AbstractCommands implements Command {
  protected final Shapes model;
  protected final Interval frame;
  protected final int time;
  private final CommandType type;

  /**
   * An abstract constructor for Commands in terms of a shape model and a interval. The field {@code
   * time} is initialized in the constructor that is the length of the interval.
   *
   * @param model the shape this command is modifying
   * @param frame the interval in which the command is occurring
   */
  protected AbstractCommands(Shapes model, Interval frame, CommandType type) {
    this.model = model;
    this.frame = frame;
    this.time = frame.getEnding() - frame.getStarting();
    this.type = type;
  }

  /**
   * Gets this Command's shape.
   *
   * @return the shape the command is operated on.
   */
  @Override
  public Shapes getShape() {
    return this.model;
  }


  /**
   * Updates the shape's changed state given an index and a number to be put in.
   */
  protected void updateHistory(int index, double num) {
    model.getHistory().get(frame)[index] = num;
    model.getFrames().get(frame.getEnding())[index - 8] = num;
  }

  /**
   * Initializes an empty state based on the model's properties and the interval where the state is
   * being created.
   */
  protected static void initArr(Shapes model, Interval frame) {
    TreeMap<Interval, double[]> that = model.getHistory();
    TreeMap<Integer, double[]> keyFrame = model.getFrames();

    that.put(frame, new double[16]);
    that.get(frame)[0] = frame.getStarting();
    that.get(frame)[8] = frame.getEnding();

    double[] temp = new double[8];
    temp[1] = model.getPosition().getX();
    temp[2] = model.getPosition().getY();
    temp[3] = model.getSize().getWidth();
    temp[4] = model.getSize().getHeight();
    temp[5] = model.getColor().getRed();
    temp[6] = model.getColor().getGreen();
    temp[7] = model.getColor().getBlue();

    keyFrame.put(frame.getStarting(), temp);
    keyFrame.put(frame.getEnding(), temp);
    initState(model, frame, temp, 0);
    initState(model, frame, temp, 8);
  }

  /**
   * Initializes part of the model's state with an array stored inside and based on its interval.
   */
  private static void initState(Shapes model, Interval frame, double[] arr, int buffer) {
    for (int i = 1; i < 8; i++) {
      model.getHistory().get(frame)[i + buffer] = arr[i];
    }
  }

  /**
   * Initializes part of the model's state with an array stored inside and based on its interval.
   */
  protected static void initKeyFrame(Shapes model, Interval frame) {
    double[] work = model.getHistory().get(frame);
    model.getFrames().put(frame.getStarting(), new double[]{work[0], work[1], work[2], work[3],
            work[4], work[5], work[6], work[7]});
    model.getFrames().put(frame.getEnding(), new double[]{work[8], work[9], work[10], work[11],
            work[12], work[13], work[14], work[15]});
  }

  /**
   * Executes a command on the shape it has.
   */
  public abstract void follow();

  /**
   * Gets this Command's frame.
   *
   * @return the time frame in which the command is operated in.
   */
  @Override
  public Interval getFrame() {
    return this.frame;
  }

  /**
   * Gets the type of this Command.
   *
   * @return the type of command that is being executed.
   */
  @Override
  public CommandType getType() {
    return this.type;
  }

}

