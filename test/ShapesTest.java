import org.junit.Test;

import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;
import cs3500.animator.model.commands.ChangeColor;
import cs3500.animator.model.commands.Command;
import cs3500.animator.model.commands.Move;
import cs3500.animator.model.commands.Resize;
import cs3500.animator.model.shapes.Oval;
import cs3500.animator.model.shapes.Rect;
import cs3500.animator.model.shapes.ShapeType;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for all the methods in the shape class including printing status, getters etc.
 */
public class ShapesTest {
  private Rect rect1 = new Rect("R", new Posn2D(200, 200),
          new RGB(255, 0, 0), new Size(50, 100));
  private Oval oval1 = new Oval("C", new Posn2D(440, 70),
          new RGB(0, 0, 255), new Size(120, 60));

  private Command move1 = new Move(rect1, new Interval(10, 50), new Posn2D(300, 300));
  private Command move2 = new Move(rect1, new Interval(70, 100), new Posn2D(200, 200));

  private Command dimension1 = new Resize(rect1, new Interval(50, 70),
          new Size(25, 100));
  private Command dimension2 = new Resize(oval1, new Interval(100, 120),
          new Size(30, 30));

  private Command color1 = new ChangeColor(oval1, new Interval(50, 70),
          new RGB(0, 170, 85));
  private Command color2 = new ChangeColor(oval1, new Interval(6, 100),
          new RGB(0, 255, 0));

  @Test
  public void testGetShapeState() {
    assertEquals("", rect1.getShapeState());
    move1.follow();
    dimension1.follow();
    assertEquals("motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0 \n"
                    + "motion R 50 300 300 50 100 255 0 0 70 300 300 25 100 255 0 0 \n",
            rect1.getShapeState());
    String[] result = rect1.getShapeState().split("\n", 2);
    assertEquals(true, result[0].substring(35).equals(result[1].substring(9, 35)));
    assertEquals("", oval1.getShapeState());
    color1.follow();
    assertEquals("motion C 50 440 70 120 60 0 0 255 "
            + "70 440 70 120 60 0 170 85 \n", oval1.getShapeState());
  }

  @Test
  public void testCheckGapMiddle() {
    move2.follow();
    assertEquals("motion R 70 200 200 50 100 255 0 0 100 200 200 50 100 255 0 0 \n",
            rect1.getShapeState());
    rect1.checkGap();
    assertEquals("motion R 70 200 200 50 100 255 0 0 100 200 200 50 100 255 0 0 \n",
            rect1.getShapeState());
  }

  @Test
  public void testCheckGapBeginning() {
    color2.follow();
    assertEquals("motion C 6 440 70 120 60 0 0 255 100 440 70 120 60 0 255 0 \n",
            oval1.getShapeState());
    oval1.checkGap();
    assertEquals("motion C 6 440 70 120 60 0 0 255 100 440 70 120 60 0 255 0 \n",
            oval1.getShapeState());
  }

  @Test
  public void testCheckGapEnd() {
    dimension2.follow();
    assertEquals("motion C 100 440 70 120 60 0 0 255 120 440 70 30 30 0 0 255 \n",
            oval1.getShapeState());
    oval1.checkGap();
    assertEquals("motion C 100 440 70 120 60 0 0 255 120 440 70 30 30 0 0 255 \n",
            oval1.getShapeState());
  }

  @Test
  public void testUpdatesAndGetters() {
    assertEquals(200, rect1.getPosition().getX(), 0.1);
    assertEquals(200, rect1.getPosition().getY(), 0.1);
    rect1.changePosition(new Posn2D(3, 2));
    assertEquals(3, rect1.getPosition().getX(), 0.1);
    assertEquals(2, rect1.getPosition().getY(), 0.1);

    assertEquals(255, rect1.getColor().getRed(), 0.1);
    assertEquals(0, rect1.getColor().getGreen(), 0.1);
    assertEquals(0, rect1.getColor().getBlue(), 0.1);
    rect1.changeColor(new RGB(200, 30, 100));
    assertEquals(200, rect1.getColor().getRed(), 0.1);
    assertEquals(30, rect1.getColor().getGreen(), 0.1);
    assertEquals(100, rect1.getColor().getBlue(), 0.1);

    assertEquals(50, rect1.getSize().getWidth(), 0.1);
    assertEquals(100, rect1.getSize().getHeight(), 0.1);
    rect1.changeSize(new Size(30, 93));
    assertEquals(30, rect1.getSize().getWidth(), 0.1);
    assertEquals(93, rect1.getSize().getHeight(), 0.1);
  }

  @Test
  public void testGetters() {
    assertEquals(true, rect1.getName().equals("R"));
    assertEquals(true, oval1.getName().equals("C"));

    assertEquals(true, rect1.getShapeType().equals(ShapeType.RECT));
    assertEquals(true, oval1.getShapeType().equals(ShapeType.OVAL));

    move1.follow();
    dimension2.follow();
    assertEquals(true, rect1.getHistory().containsKey(new Interval(10, 50)));
    assertNotEquals(true, rect1.getHistory().containsKey(new Interval(50, 70)));
    assertEquals(true, oval1.getHistory().containsKey(new Interval(100, 120)));
    assertNotEquals(true, oval1.getHistory().containsKey(new Interval(6, 100)));
  }

  @Test
  public void testEquals() {
    assertNotEquals(true, rect1.equals(oval1));
    assertNotEquals(true, rect1.hashCode() == oval1.hashCode());

    assertEquals(true, rect1.equals(rect1));
    assertEquals(true, oval1.equals(oval1));
    assertEquals(true, rect1.equals(new Rect("R", new Posn2D(34, 23),
            new RGB(255, 0, 23), new Size(32, 12))));
    assertEquals(true, oval1.equals(new Oval("C", new Posn2D(234, 34),
            new RGB(0, 0, 2), new Size(34, 2))));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull() {
    rect1.changeSize(null);
    rect1.changeColor(null);
    rect1.changePosition(null);
  }

  @Test
  public void testLastTime() {
    assertEquals(rect1.getShapeLastTime(), 0);

    move1.follow();
    color1.follow();
    assertEquals(rect1.getShapeLastTime(), 50);

    move2.follow();
    color2.follow();
    dimension2.follow();
    assertEquals(oval1.getShapeLastTime(), 120);
  }

  @Test
  public void testAtTick() {
    assertEquals(rect1.getShapesAtTick(1), null);
    move1.follow();
    assertEquals(rect1.getShapesAtTick(20), new Rect("R", new Posn2D(250, 250),
            new RGB(255, 0, 0),
            new Size(50, 100)));
    assertEquals(rect1.getShapesAtTick(100), null);
    assertEquals(oval1.getShapesAtTick(-2), null);
    color1.follow();
    assertEquals(oval1.getShapesAtTick(60), new Oval("C", new Posn2D(440, 70),
            new RGB(0, 85, 170), new Size(120, 60)));
  }

  @Test
  public void testGetSVGTag() {
    assertEquals(rect1.getSVGTag(1), "<rect id=\"R\" x=\"200.00\" y=\"200.00\" " +
            "width=\"50.00\" height=\"100.00\" fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n" +
            "</rect>\n");
    assertEquals(oval1.getSVGTag(1), "<ellipse id=\"C\" cx=\"440.00\" cy=\"70.00\"" +
            " rx=\"60.00\" ry=\"30.00\" fill=\"rgb(0,0,255)\" visibility=\"hidden\">\n" +
            "</ellipse>\n");
    move1.follow();
    dimension1.follow();
    assertEquals(rect1.getSVGTag(1), "<rect id=\"R\" x=\"200.00\" y=\"200.00\"" +
            " width=\"50.00\" height=\"100.00\" fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"10.00s\" dur=\"70.00s\" " +
            "attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"10.00s\" dur=\"40.00s\"" +
            " attributeName=\"x\" from=\"200.00\" to=\"300.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"10.00s\" dur=\"40.00s\" " +
            "attributeName=\"y\" from=\"200.00\" to=\"300.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"50.00s\" dur=\"20.00s\"" +
            " attributeName=\"width\" from=\"50.00\" to=\"25.00\" fill=\"freeze\"/>\n" +
            "</rect>\n");

    move2.follow();
    color1.follow();
    assertEquals(oval1.getSVGTag(1), "<ellipse id=\"C\" cx=\"440.00\" cy=\"70.00\"" +
            " rx=\"60.00\" ry=\"30.00\" fill=\"rgb(0,0,255)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"50.00s\" dur=\"70.00s\"" +
            " attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"50.00s\" dur=\"20.00s\"" +
            " attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\"" +
            " fill=\"freeze\"/>\n" +
            "</ellipse>\n");
  }
}

