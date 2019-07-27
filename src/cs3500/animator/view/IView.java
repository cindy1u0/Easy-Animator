package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.tools.Screen;
import cs3500.animator.model.shapes.IReadOnlyShapes;

/**
 * An IView interface that enables three different views including text view, canvas view, and SVG
 * view.
 */
public interface IView {
  /**
   * Renders the given output depends on with view the user is selecting.
   *
   * @param shapes given IReadOnlyShapes that are from the model
   */
  void render(List<IReadOnlyShapes> shapes);

  /**
   * setter that sets the default canvas to the given size.
   *
   * @param c given canvas size
   */
  void setCanvas(Screen c);

  /**
   * Converts the rendering information into a string.
   *
   * @return the output information in a string form
   */
  StringBuilder getText();

  /**
   * Gets the tempo that the user passes in.
   *
   * @return the tempo
   */
  int getTempo();

  /**
   * Adds all the IViewFeatures to the listeners to execute.
   *
   * @param vf given IViewFeatures
   * @throws UnsupportedOperationException if the given view doesn't support the listeners
   */
  void addListener(IViewFeatures vf) throws UnsupportedOperationException;

  /**
   * Checks if the given view has a listener field.
   *
   * @return true if it has a listener field
   */
  boolean hasListener();
}

