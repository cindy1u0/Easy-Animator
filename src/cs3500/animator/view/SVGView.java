package cs3500.animator.view;

import java.util.List;
import java.util.Objects;

import cs3500.animator.model.shapes.IReadOnlyShapes;

/**
 * A class that renders the SVG view of the motions of all the shapes.
 */
public class SVGView extends AbstractView {
  private final int tempo;

  /**
   * A constructor that will later be used in the controller.
   */
  public SVGView(int tempo) {
    this.tempo = tempo;
  }

  /**
   * Renders the given output depends on with view the user is selecting.
   *
   * @param shapes given IReadOnlyShapes that are from the model
   */
  @Override
  public void render(List<IReadOnlyShapes> shapes) {
    Objects.requireNonNull(shapes);
    text.append(String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\""
                    + " xmlns=\"http://www.w3.org/2000/svg\">\n",
            canvas.getCanvasW() + canvas.getLocX(),
            canvas.getCanvasH() + canvas.getLocY()));

    for (IReadOnlyShapes s : shapes) {
      text.append(s.getSVGTag(this.tempo));
    }

    text.append("</svg>");
  }

  /**
   * Adds all the IViewFeatures to the listeners to execute.
   *
   * @param vf given IViewFeatures
   * @throws UnsupportedOperationException if the given view doesn't support the listeners
   */
  @Override
  public void addListener(IViewFeatures vf) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("yeehaw");
  }

}

