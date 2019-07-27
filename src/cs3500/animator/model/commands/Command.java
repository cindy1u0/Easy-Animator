package cs3500.animator.model.commands;

import cs3500.animator.model.shapes.Shapes;
import cs3500.animator.model.tools.Interval;

/**
 * The Command interface that represents the various operations on the model.
 */
public interface Command {

  /**
   * Executes a command on the shape it has.
   */
  void follow();

  /**
   * Gets this Command's shape.
   *
   * @return the shape the command is operated on.
   */
  Shapes getShape();

  /**
   * Gets this Command's frame.
   *
   * @return the time frame in which the command is operated in.
   */
  Interval getFrame();

  /**
   * Gets the type of this Command.
   *
   * @return the type of command that is being executed.
   */
  CommandType getType();
}

