import org.junit.Test;

import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;
import cs3500.animator.model.commands.ChangeColor;
import cs3500.animator.model.commands.CommandType;
import cs3500.animator.model.commands.Move;
import cs3500.animator.model.commands.Resize;
import cs3500.animator.model.shapes.Oval;
import cs3500.animator.model.shapes.Rect;
import cs3500.animator.model.shapes.Shapes;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for all the methods ini the command class including follow, getters etc.
 */
public class CommandTest {

  private Shapes rect1 = new Rect("R", new Posn2D(200, 200),
          new RGB(255, 0, 0), new Size(50, 100));
  private Shapes oval1 = new Oval("C", new Posn2D(440, 70),
          new RGB(0, 0, 255), new Size(120, 60));

  private Move move1 = new Move(rect1, new Interval(10, 50), new Posn2D(300, 300));
  private Move move2 = new Move(rect1, new Interval(70, 100), new Posn2D(200, 200));

  private Resize dimension1 = new Resize(rect1, new Interval(51, 70),
          new Size(25, 100));
  private Resize dimension2 = new Resize(oval1, new Interval(10, 20),
          new Size(30, 30));

  private ChangeColor color1 = new ChangeColor(oval1, new Interval(50, 70),
          new RGB(0, 170, 85));
  private ChangeColor color2 = new ChangeColor(oval1, new Interval(70, 80),
          new RGB(0, 255, 0));

  @Test
  public void testFollow() {
    assertEquals("", rect1.getShapeState());
    move1.follow();
    assertEquals("motion R 10 200 200 50 100 "
            + "255 0 0 50 300 300 50 100 255 0 0 \n", rect1.getShapeState());
    dimension1.follow();
    assertEquals("motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0 \n"
                    + "motion R 51 300 300 50 100 255 0 0 70 300 300 25 100 255 0 0 \n",
            rect1.getShapeState());
    assertEquals("", oval1.getShapeState());
    color1.follow();
    assertEquals("motion C 50 440 70 120 60 0 0 255 "
            + "70 440 70 120 60 0 170 85 \n", oval1.getShapeState());
  }

  @Test
  public void testGetters() {
    assertEquals(true, move2.getShape().equals(rect1));
    assertEquals(true, dimension2.getShape().equals(oval1));
    assertEquals(true, color2.getShape().equals(oval1));

    assertEquals(true, move2.getFrame().equals(new Interval(70, 100)));
    assertEquals(true, dimension2.getFrame().equals(new Interval(10, 20)));
    assertEquals(true, color2.getFrame().equals(new Interval(70, 80)));

    assertEquals(true, move2.getType().equals(CommandType.MOVE));
    assertEquals(true, dimension2.getType().equals(CommandType.RESIZE));
    assertEquals(true, color1.getType().equals(CommandType.PAINT));
  }
}

