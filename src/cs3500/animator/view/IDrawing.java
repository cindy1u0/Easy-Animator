package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.shapes.IReadOnlyShapes;

/**
 * A drawing interface that will draw all the shapes with corresponding in their positions, colors,
 * and sizes on a canvas.
 */
public interface IDrawing {
  /**
   * draws the shapes on the canvas with the corresponding animations.
   *
   * @param shapes Given IReadOnlyShapes that are able to be mutated
   */
  void draw(List<IReadOnlyShapes> shapes);
}
