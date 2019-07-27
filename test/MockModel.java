import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.commands.Command;
import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.model.shapes.Rect;
import cs3500.animator.model.shapes.Shapes;
import cs3500.animator.model.tools.Screen;

/**
 * A Mock Model to test the view to model functionality.
 */
public class MockModel implements AnimationModel {
  Appendable output;
  List<Shapes> los;

  /**
   * A mock model constructor.
   *
   * @param output the output source of all the messages appended.
   */
  public MockModel(Appendable output) {
    this.output = output;
    los = new ArrayList<>();
    los.add(new Rect("Goat Man"));
  }

  private void write(String s) {
    try {
      this.output.append(s);
    } catch (IOException e) {
      System.err.println("Judas no!");
    }
  }

  @Override
  public String getState() {
    return "this is just a mock man you got played";
  }

  @Override
  public void addShape(Shapes s) throws IllegalArgumentException {
    los.add(s);
    write(s.getName());
  }

  @Override
  public List<Shapes> getShapes() {
    return los;
  }

  @Override
  public void removeShape(Shapes s) throws IllegalArgumentException {
    los.remove(s);

    write(s.getName() + " got removed rip");
  }

  @Override
  public List<Command> getCommand() {
    return new ArrayList<>();
  }

  @Override
  public void removeCommand(Command c) throws IllegalArgumentException {
    System.err.println("I was walking down the Krentzman Quadrangl...");
  }

  @Override
  public void addCommand(Command c) throws IllegalArgumentException {
    System.err.println("Thank you NUPD");
  }

  @Override
  public List<IReadOnlyShapes> getAnimatedShapes(int tick) {
    return new ArrayList<>();
  }

  @Override
  public Screen getCanvas() {
    return new Screen(0, 0, 0, 0);
  }

  @Override
  public int getLastTime() {
    write("0");
    return 0;
  }

  @Override
  public List<IReadOnlyShapes> convertShapes() {
    return new ArrayList<>();
  }

  @Override
  public void addKeyFrameToShape(String name, int[] stuff) throws IllegalArgumentException {
    write(name + " has this motion");
  }

  @Override
  public void removeKeyFrameFromShape(String id, int key) throws IllegalArgumentException {
    write(id + " just lost a limb :(");
  }

  @Override
  public void editKeyFrame(String id, int key, int[] stuff) throws IllegalArgumentException {
    write(id + " is changing up his routin :D");
  }
}
