package cs3500.animator.model.commands;

import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.shapes.Shapes;

/**
 * Represents an action Move that moves the current shape to the given position.
 */
public class Move extends AbstractCommands {
  private final Posn2D position;

  /**
   * A constructor that represents the position of the current shape.
   *
   * @param model    the Shape that is being modified in terms of location
   * @param frame    the Interval in which the move occurs
   * @param position the position of the shape
   */
  public Move(Shapes model, Interval frame, Posn2D position) {
    super(model, frame, CommandType.MOVE);
    this.position = position;
  }

  /**
   * Executes a command on the shape it has, in this case, Move.
   */
  @Override
  public void follow() {
    if (!model.getHistory().containsKey(frame)) {
      initArr(model, frame);
    }
    model.changePosition(position);
    updateHistory(9, position.getX());
    updateHistory(10, position.getY());
    initKeyFrame(model, frame);
  }

}

