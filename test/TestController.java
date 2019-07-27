import java.io.IOException;
import java.util.List;

import javax.swing.*;

import cs3500.animator.controller.Controller;
import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.model.shapes.Shapes;
import cs3500.animator.view.IView;
import cs3500.animator.view.IViewFeatures;

/**
 * A Mock Controller class to test view to controller functionality.
 */
public class TestController implements Controller, IViewFeatures {
  private final Appendable appendable;
  private final String str = "How did you even get here?";

  /**
   * A mock controller constructor.
   * @param view the type of view
   * @param appendable the source of outputting messages
   */
  public TestController(IView view, Appendable appendable) {
    view.addListener(this);
    this.appendable = appendable;
  }

  @Override
  public void run(Appendable ap) throws IllegalStateException {
    System.err.println("run like the wind bullseye");
  }

  @Override
  public void start() {
    try {
      appendable.append("Woah you pressed start!");
    } catch (IOException e) {
      System.err.println("Jesus, I brought you myr");
    }
  }

  @Override
  public void paused() {
    try {
      appendable.append("Congrats you paused!");
    } catch (IOException e) {
      System.err.println("Oh thank you Judas");
    }
  }

  @Override
  public void resume() {
    try {
      appendable.append("Awh why'd you resume?");
    } catch (IOException e) {
      System.err.println("myr.. DUR");
    }
  }

  @Override
  public void restart() {
    try {
      appendable.append("Please don't restart.");
    } catch (IOException e) {
      System.err.println("Judas no!");
    }
  }

  @Override
  public void changeSpeed(int value) {
    try {
      appendable.append("Why the change in speed?");
    } catch (IOException e) {
      System.err.println("I wonder if you actually read through these mock messages lol");
    }
  }

  @Override
  public void addShape(Shapes s) throws IllegalArgumentException {
    try {
      appendable.append("Wowee you added a shape!");
    } catch (IOException e) {
      System.err.println("shoutout to the TAs for being great this summer! :D");
    }
  }

  @Override
  public void removeShape(String id) throws IllegalArgumentException {
    try {
      appendable.append("Goodbye shape! Hello blank walls!");
    } catch (IOException e) {
      System.err.println(str);
    }
  }

  @Override
  public void loop(boolean value) {
    try {
      appendable.append("Is it actually looping tho?");
    } catch (IOException e) {
      System.err.println(str);
    }
  }

  @Override
  public void addKeyFrame(String idShape, int[] stuff) throws IllegalArgumentException {
    try {
      appendable.append("Go the gym for them key motions!");
    } catch (IOException e) {
      System.err.println(str);
    }
  }

  @Override
  public void removeKeyFrame(String id, int key) throws IllegalArgumentException {
    try {
      appendable.append("Awh man you chose not to go to the gym?");
    } catch (IOException e) {
      System.err.println(str);
    }
  }

  @Override
  public void editKeyFrame(String id, int key, int[] stuff) throws IllegalArgumentException {
    try {
      appendable.append("It's good practice to change up routine.");
    } catch (IOException e) {
      System.err.println(str);
    }
  }

  @Override
  public List<IReadOnlyShapes> getControllerShape() {
    try {
      appendable.append("Attempting to grab nothing");
    } catch (IOException e) {
      System.err.println(str);
    }
    return null;
  }

  @Override
  public List<String> getControllerMotion(String id) {
    try {
      appendable.append("Attempting to record last night's Knick's game");
    } catch (IOException e) {
      System.err.println(str);
    }
    return null;
  }

  @Override
  public void scrub(JSlider slider, int value) {

  }
}
