package cs3500.animator.view;

/**
 * A factory class that enables the user to pick whichever view they like.
 */
public class FactoryView {
  /**
   * a factory method that renders the corresponding view according to the view type and the tempo.
   */
  public static IView makeView(String type, int tempo) throws IllegalArgumentException {
    switch (type) {
      case "text":
        return new TextView();
      case "visual":
        return new VisualView(tempo);
      case "svg":
        return new SVGView(tempo);
      case "edit":
        return new EditorView(new VisualView(tempo));
      default:
        throw new IllegalArgumentException("Not a valid view");
    }
  }
}
