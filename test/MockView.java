import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.model.shapes.Rect;
import cs3500.animator.model.tools.Screen;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewFeatures;

/**
 * A Mock View class to test view functionality/listeners.
 */
public class MockView implements IView {
  List<IViewFeatures> listeners;
  Readable input;

  /**
   * A mock view constructor.
   * @param input the readable input to be read as a command
   */
  public MockView(Readable input) {
    this.listeners = new ArrayList<>();
    this.input = input;
  }

  @Override
  public void render(List<IReadOnlyShapes> shapes) {
    Scanner scan = new Scanner(input);
    try {
      String cmd = scan.next();
      for (IViewFeatures feat : listeners) {
        switch (cmd) {
          case "Start":
            feat.start();
            break;
          case "Resume":
            feat.resume();
            break;
          case "Pause":
            feat.paused();
            break;
          case "Restart":
            feat.restart();
            break;
          case "Loop":
            feat.loop(true);
            break;
          case "removeShape":
            feat.removeShape("Goat Man");
            break;
          case "addShape":
            feat.addShape(new Rect("Goat Man"));
            break;
          case "addMotion":
            feat.addKeyFrame("Goat Man", new int[]{1, 2, 3, 4});
            break;
          case "removeMotion":
            feat.removeKeyFrame("Goat Man", 1);
            break;
          case "editMotion":
            feat.editKeyFrame("Goat Man", 0, null);
            break;
          default:
            // do nothing
        }
      }
    } catch (Exception e) {
      // wot in tarnation?
    }
  }

  @Override
  public void setCanvas(Screen c) {
    // do nothing
  }

  @Override
  public StringBuilder getText() {
    return new StringBuilder("Congratulations you found a string builder.");
  }

  @Override
  public int getTempo() {
    return 0;
  }

  @Override
  public void addListener(IViewFeatures vf) throws UnsupportedOperationException {
    this.listeners.add(vf);
  }

  @Override
  public boolean hasListener() {
    return true;
  }
}
