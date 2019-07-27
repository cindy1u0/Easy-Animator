import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;
import cs3500.animator.model.commands.Command;
import cs3500.animator.model.shapes.Oval;
import cs3500.animator.model.shapes.Rect;
import cs3500.animator.model.commands.ChangeColor;
import cs3500.animator.model.commands.Move;
import cs3500.animator.model.commands.Resize;
import cs3500.animator.model.shapes.Shapes;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for all the methods in animation model including add shape, add command, remove shape,
 * remove command, print model status, etc.
 */
public class AnimationModelImplTest {

  /**
   * Rest all the shapes so that it doesn't have any commands at first.
   *
   * @param shapes shapes whose history need to be reset
   */
  private void reset(Shapes... shapes) {
    for (Shapes s : shapes) {
      s.getHistory().clear();
    }
  }

  private Shapes rect1 = new Rect("R", new Posn2D(200, 200),
          new RGB(255, 0, 0), new Size(50, 100));
  private Shapes rect2 = new Rect("dave", new Posn2D(0, 0),
          new RGB(0, 255, 0), new Size(10, 10));
  private Shapes oval1 = new Oval("C", new Posn2D(440, 70),
          new RGB(0, 0, 255), new Size(120, 60));
  private Shapes oval2 = new Oval("wi", new Posn2D(3, 5),
          new RGB(100, 39, 100), new Size(30, 30));

  private Command move1 = new Move(rect1, new Interval(10, 50), new Posn2D(300, 300));
  private Command move2 = new Move(rect1, new Interval(70, 100), new Posn2D(200, 200));
  private Command move3 = new Move(rect2, new Interval(1, 5), new Posn2D(60, 80));
  private Command move4 = new Move(oval1, new Interval(20, 50), new Posn2D(440, 250));
  private Command move5 = new Move(oval1, new Interval(50, 70), new Posn2D(440, 370));
  private Command move6 = new Move(oval2, new Interval(7, 10), new Posn2D(20, 30));

  private Command dimension1 = new Resize(rect1, new Interval(51, 70),
          new Size(25, 100));
  private Command dimension2 = new Resize(rect2, new Interval(5, 10),
          new Size(5, 5));
  private Command dimension3 = new Resize(oval2, new Interval(1, 7),
          new Size(80, 100));
  private Command dimension4 = new Resize(oval2, new Interval(10, 20),
          new Size(30, 30));

  private Command color1 = new ChangeColor(rect2, new Interval(71, 100),
          new RGB(200, 0, 255));
  private Command color2 = new ChangeColor(oval1, new Interval(50, 70),
          new RGB(0, 170, 85));
  private Command color3 = new ChangeColor(oval1, new Interval(70, 80),
          new RGB(0, 255, 0));

  private ArrayList<Shapes> shapeList1 = new ArrayList<>(Arrays.asList(rect1, oval1));
  private ArrayList<Shapes> shapeList2 = new ArrayList<>(Arrays.asList(rect1, rect2));
  private ArrayList<Shapes> shapeList3 = new ArrayList<>(Arrays.asList(rect1, oval1, oval2));
  private ArrayList<Shapes> shapeList4 = new ArrayList<>(Arrays.asList(oval2));
  private ArrayList<Shapes> shapeList5 = new ArrayList<>(Arrays.asList());

  private ArrayList<Command> commandList1 = new ArrayList<>(Arrays.asList(move1, dimension1, move2,
          move4, move5, color2, color3));
  private ArrayList<Command> commandList2 = new ArrayList<>(Arrays.asList(move1, move2, move3,
          color1, dimension2));
  private ArrayList<Command> commandList3 = new ArrayList<>(Arrays.asList(move2, dimension3,
          color1));
  private ArrayList<Command> commandList4 = new ArrayList<>(Arrays.asList(dimension4, move6));
  private ArrayList<Command> commandList5 = new ArrayList<>(Arrays.asList());

  private AnimationModel a1 = new AnimationModelImpl(shapeList1, commandList1);
  private AnimationModel a2 = new AnimationModelImpl(shapeList2, commandList2);
  private AnimationModel a3 = new AnimationModelImpl(shapeList3, commandList3);
  private AnimationModel a4 = new AnimationModelImpl(shapeList4, commandList4);
  private AnimationModel a5 = new AnimationModelImpl(shapeList5, commandList5);

  @Test
  public void testGetDescription() {
    assertEquals("", a5.getState());
    assertEquals("shape R Rectangle\n" +
            "motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0 \n" +
            "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0 \n" +
            "motion R 51 300 300 50 100 255 0 0 70 300 300 25 100 255 0 0 \n" +
            "motion R 70 300 300 25 100 255 0 0 100 200 200 25 100 255 0 0 \n" +
            "\n" +
            "shape C Oval\n" +
            "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255 \n" +
            "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85 \n" +
            "motion C 70 440 370 120 60 0 170 85 80 440 370 120 60 0 255 0 \n" +
            "\n", a1.getState());
    assertEquals("shape R Rectangle\n" +
            "motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0 \n" +
            "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0 \n" +
            "motion R 51 300 300 50 100 255 0 0 70 300 300 25 100 255 0 0 \n" +
            "motion R 70 300 300 25 100 255 0 0 100 200 200 25 100 255 0 0 \n" +
            "\n" +
            "shape dave Rectangle\n" +
            "motion dave 1 0 0 10 10 0 255 0 5 60 80 10 10 0 255 0 \n" +
            "motion dave 5 60 80 10 10 200 0 255 10 60 80 5 5 200 0 255 \n" +
            "motion dave 10 60 80 5 5 200 0 255 71 60 80 5 5 200 0 255 \n" +
            "motion dave 71 60 80 10 10 0 255 0 100 60 80 10 10 200 0 255 \n" +
            "\n", a2.getState());
    assertEquals("shape R Rectangle\n" +
            "motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0 \n" +
            "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0 \n" +
            "motion R 51 300 300 50 100 255 0 0 70 300 300 25 100 255 0 0 \n" +
            "motion R 70 300 300 25 100 255 0 0 100 200 200 25 100 255 0 0 \n" +
            "\n" +
            "shape C Oval\n" +
            "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255 \n" +
            "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85 \n" +
            "motion C 70 440 370 120 60 0 170 85 80 440 370 120 60 0 255 0 \n" +
            "\n" +
            "shape wi Oval\n" +
            "motion wi 1 3 5 30 30 100 39 100 7 3 5 80 100 100 39 100 \n" +
            "motion wi 7 3 5 30 30 100 39 100 10 20 30 30 30 100 39 100 \n" +
            "motion wi 10 3 5 80 100 100 39 100 20 3 5 30 30 100 39 100 \n" +
            "\n", a3.getState());
    assertEquals("shape wi Oval\n" +
            "motion wi 1 3 5 30 30 100 39 100 7 3 5 80 100 100 39 100 \n" +
            "motion wi 7 3 5 30 30 100 39 100 10 20 30 30 30 100 39 100 \n" +
            "motion wi 10 3 5 80 100 100 39 100 20 3 5 30 30 100 39 100 \n" +
            "\n", a4.getState());
  }

  @Test
  public void testNoCommandOrNoShapes() {
    reset(rect1, rect2, oval1, oval2);
    shapeList5.add(oval2);
    assertEquals("", a5.getState());
    shapeList5.remove(rect1);
    commandList5.add(move2);
    assertEquals("", a5.getState());
  }

  @Test
  public void testAddShapes() {
    reset(rect1, rect2, oval1, oval2);
    assertEquals(2, shapeList1.size());
    assertEquals(false, a1.getShapes().contains(oval2));
    a1.addShape(oval2);
    assertEquals(3, shapeList1.size());
    assertEquals(true, a1.getShapes().contains(oval2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddDupShapes() {
    reset(rect1, rect2, oval1, oval2);
    assertEquals(2, shapeList1.size());
    a1.addShape(oval1);
  }

  @Test
  public void testGetShapes() {
    reset(rect1, rect2, oval1, oval2);
    assertEquals(shapeList3, a3.getShapes());
    assertEquals(3, a3.getShapes().size());
  }

  @Test
  public void testRemoveShapes() {
    reset(rect1, rect2, oval1, oval2);
    assertEquals(true, a3.getShapes().contains(oval1));
    a3.removeShape(oval1);
    assertEquals(false, a3.getShapes().contains(oval1));
  }

  @Test
  public void testGetCommands() {
    reset(rect1, rect2, oval1, oval2);
    assertEquals(commandList3, a3.getCommand());
    assertEquals(3, a3.getCommand().size());
  }

  @Test
  public void testRemoveCommand() {
    reset(rect1, rect2, oval1, oval2);
    assertEquals(true, a2.getCommand().contains(color1));
    a2.removeCommand(color1);
    assertEquals(false, a2.getCommand().contains(color1));
  }

  @Test
  public void testAddCommand() {
    reset(rect1, rect2, oval1, oval2);
    assertEquals(false, a5.getCommand().contains(dimension1));
    a5.addCommand(dimension1);
    assertEquals(true, a5.getCommand().contains(dimension1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapCommand() {
    a1.addCommand(new Move(rect1, new Interval(10, 49), new Posn2D(20, 30)));
    a1.addCommand(new Move(rect1, new Interval(30, 70), new Posn2D(1, 10)));
    a1.addCommand(new Resize(rect1, new Interval(40, 52),
            new Size(25, 100)));
    a1.addCommand(new ChangeColor(oval1, new Interval(75, 100),
            new RGB(0, 255, 0)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull() {
    a1.addCommand(null);
    a2.addShape(null);
    a1.removeCommand(null);
    a3.removeShape(null);
  }

}
