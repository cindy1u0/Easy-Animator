package cs3500.animator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import cs3500.animator.model.tools.Screen;
import cs3500.animator.model.shapes.IReadOnlyShapes;

/**
 * An Abstract class for various types of views.
 */
public abstract class AbstractView extends JFrame implements IView {
  protected Screen canvas = new Screen(0, 0, 0, 0);
  protected List<IReadOnlyShapes> los = new ArrayList<IReadOnlyShapes>();
  protected final StringBuilder text;

  /**
   * An AbstractView constructor that initializes empty string builder to represent its text desc.
   */
  AbstractView() {
    this.text = new StringBuilder();
  }

  /**
   * Renders the given output depends on with view the user is selecting.
   *
   * @param shapes given IReadOnlyShapes that are from the model
   */
  @Override
  public abstract void render(List<IReadOnlyShapes> shapes);

  /**
   * setter that sets the default canvas to the given size.
   *
   * @param c given canvas size
   */
  @Override
  public void setCanvas(Screen c) {
    this.canvas = c;
  }

  /**
   * Converts the rendering information into a string.
   *
   * @return the output information in a string form
   */
  @Override
  public StringBuilder getText() {
    return this.text;
  }

  /**
   * Gets the tempo that the user passes in.
   *
   * @return the tempo
   */
  @Override
  public int getTempo() {
    return 0;
  }

  /**
   * Checks if the given view has a listener field.
   *
   * @return true if it has a listener field
   */
  public boolean hasListener() {
    return false;
  }

}

