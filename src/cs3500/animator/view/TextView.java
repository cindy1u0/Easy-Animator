package cs3500.animator.view;

import java.util.List;

import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.model.shapes.ShapeType;

/**
 * A class that renders the text of the motions of all the shapes.
 */
public class TextView extends AbstractView {

  /**
   * A constructor that will later be used in the controller.
   */
  public TextView() {
    // nothing to be initialized.
  }

  /**
   * Renders the given output depends on with view the user is selecting.
   *
   * @param shapes given IReadOnlyShapes that are from the model
   */
  @Override
  public void render(List<IReadOnlyShapes> shapes) {
    this.text.append(String.format("canvas %s", this.canvas.toString()));

    for (IReadOnlyShapes sh : shapes) {
      this.text.append("shape ");
      this.text.append(sh.getName());
      if (sh.getShapeType() == ShapeType.OVAL) {
        this.text.append(" Oval\n");
      } else {
        this.text.append(" Rectangle\n");
      }
      this.text.append(sh.getShapeState());
      this.text.append("\n");
    }
  }

  /**
   * Adds all the IViewFeatures to the listeners to execute.
   *
   * @param vf given IViewFeatures
   * @throws UnsupportedOperationException if the given view doesn't support the listeners
   */
  @Override
  public void addListener(IViewFeatures vf) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Yeeehaw");
  }


}

