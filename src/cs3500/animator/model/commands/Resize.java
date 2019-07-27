package cs3500.animator.model.commands;

import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.shapes.Shapes;
import cs3500.animator.model.tools.Size;

/**
 * Represents an action Resize that shrinks or enlarges the current shape.
 */
public class Resize extends AbstractCommands implements Command {
  private final Size size;

  /**
   * A constructor that represents the dimension of the current shape.
   *
   * @param model the Shape that is being modified in terms of size
   * @param frame the Interval in which the resizing occurs
   * @param size  the dimension of the shape
   */
  public Resize(Shapes model, Interval frame, Size size) {
    super(model, frame, CommandType.RESIZE);
    this.size = size;
  }

  /**
   * Executes a command on the shape it has, in this case, Resize.
   */
  @Override
  public void follow() {
    if (!model.getHistory().containsKey(frame)) {
      initArr(model, frame);
    }

    model.changeSize(size);
    updateHistory(11, size.getWidth());
    updateHistory(12, size.getHeight());
    initKeyFrame(model, frame);
  }
}

