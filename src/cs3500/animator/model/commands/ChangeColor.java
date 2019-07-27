package cs3500.animator.model.commands;


import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.shapes.Shapes;


/**
 * Represents an action ChangeColor that changes the current shape's color to the given color.
 */
public class ChangeColor extends AbstractCommands implements Command {
  private final RGB color;

  /**
   * A constructor that represents the color of the current shape.
   *
   * @param model the Shape whose color is being changed
   * @param frame the Interval of the color change occurring
   * @param color the color of the shape
   */
  public ChangeColor(Shapes model, Interval frame, RGB color) {
    super(model, frame, CommandType.PAINT);
    this.color = color;
  }

  /**
   * Executes a command on the shape it has, in this case, ChangeColor.
   */
  @Override
  public void follow() {
    if (!model.getHistory().containsKey(frame)) {
      initArr(model, frame);
    }

    model.changeColor(color);
    updateHistory(13, color.getRed());
    updateHistory(14, color.getGreen());
    updateHistory(15, color.getBlue());
    initKeyFrame(model, frame);

  }
}

