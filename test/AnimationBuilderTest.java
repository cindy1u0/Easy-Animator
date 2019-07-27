import org.junit.Test;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;
import cs3500.animator.model.shapes.Oval;
import cs3500.animator.model.shapes.Shapes;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the class AnimationBuilder.
 */
public class AnimationBuilderTest {

  @Test
  public void testBuild() {
    AnimationModel model = new AnimationModelImpl.Builder().build();
    assertEquals(0, model.getShapes().size());
    assertEquals(0, model.getCommand().size());
    assertEquals(500, model.getCanvas().getCanvasH());
    assertEquals(500, model.getCanvas().getCanvasW());
    assertEquals(0, model.getCanvas().getLocX());
    assertEquals(0, model.getCanvas().getLocY());
  }

  @Test
  public void testSetBounds() {
    AnimationModel model = new AnimationModelImpl.Builder()
            .setBounds(3, 3, 500, 300).build();
    assertEquals(0, model.getShapes().size());
    assertEquals(0, model.getCommand().size());
    assertEquals(300, model.getCanvas().getCanvasH());
    assertEquals(500, model.getCanvas().getCanvasW());
    assertEquals(3, model.getCanvas().getLocX());
    assertEquals(3, model.getCanvas().getLocY());
    assertEquals("3 3 500 300\n", model.getCanvas().toString());
  }

  @Test
  public void testDeclareShapes() {
    AnimationModel model = new AnimationModelImpl.Builder()
            .declareShape("R", "rectangle").build();
    assertEquals(1, model.getShapes().size());
    assertEquals(0, model.getCommand().size());
    assertEquals("R", model.getShapes().get(0).getName());
    assertEquals(500, model.getCanvas().getCanvasH());
    assertEquals(500, model.getCanvas().getCanvasW());
    assertEquals(0, model.getCanvas().getLocX());
    assertEquals(0, model.getCanvas().getLocY());
  }

  @Test
  public void testAddMotion() {
    AnimationModel model = new AnimationModelImpl.Builder()
            .declareShape("R", "rectangle")
            .addMotion("R", 1, 200, 200, 50, 100, 255, 0, 0,
                    10, 300, 300, 25, 100, 0, 0, 0).build();
    assertEquals(1, model.getShapes().size());
    assertEquals(0, model.getCommand().size());
    assertEquals(200, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[1], 0.1);
    assertEquals(200, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[2], 0.1);
    assertEquals(50, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[3], 0.1);
    assertEquals(100, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[4], 0.1);
    assertEquals(255, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[5], 0.1);
    assertEquals(0, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[6], 0.1);
    assertEquals(0, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[7], 0.1);
    assertEquals(300, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[9], 0.1);
    assertEquals(300, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[10], 0.1);
    assertEquals(25, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[11], 0.1);
    assertEquals(100, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[12], 0.1);
    assertEquals(0, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[13], 0.1);
    assertEquals(0, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[14], 0.1);
    assertEquals(0, model.getShapes().get(0).getHistory()
            .get(new Interval(1, 10))[15], 0.1);
  }

  @Test
  public void testAddKeyframe() {
    AnimationModel model = new AnimationModelImpl.Builder()
            .declareShape("R", "rectangle")
            .addKeyframe("R", 2, 3, 2, 4, 2, 5, 6, 3).build();
    assertEquals(1, model.getShapes().size());
    assertEquals(0, model.getCommand().size());
    assertEquals("R", model.getShapes().get(0).getName());
    assertEquals(500, model.getCanvas().getCanvasH());
    assertEquals(500, model.getCanvas().getCanvasW());
    assertEquals(0, model.getCanvas().getLocX());
    assertEquals(0, model.getCanvas().getLocY());
  }

  @Test
  public void testBuilderDraw() {
    AnimationModel model = new AnimationModelImpl.Builder()
            .declareShape("R", "rectangle").declareShape("C", "ellipse")
            .addMotion("R", 10, 200, 200, 50, 100, 255, 0, 0,
                    50, 300, 300, 50, 100, 255, 0, 0)
            .addMotion("R", 50, 300, 300, 50, 100, 255, 0, 0
                    , 51, 300, 300, 50, 100, 255, 0, 0)
            .addMotion("C", 20, 440, 70, 120, 60, 0, 0, 255,
                    50, 440, 250, 120, 60, 0, 0, 255)
            .addMotion("C", 50, 440, 250, 120, 60, 0, 0, 255,
                    70, 440, 370, 120, 60, 0, 170, 85).build();
    assertEquals(70, model.getLastTime());
    assertEquals("shape R Rectangle\n" +
            "motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0 \n" +
            "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0 \n" +
            "\n" +
            "shape C Oval\n" +
            "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255 \n" +
            "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85 \n" +
            "\n", model.getState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlaps() {
    new AnimationModelImpl.Builder()
            .declareShape("R", "rectangle").declareShape("C", "ellipse")
            .addMotion("R", 10, 200, 200, 50, 100, 255, 0, 0,
                    50, 300, 300, 50, 100, 255, 0, 0)
            .addMotion("R", 12, 300, 300, 50, 100, 255, 0, 0,
                    50, 300, 300, 50, 100, 255, 0, 0)
            .addMotion("C", 20, 440, 70, 120, 60, 0, 0, 255,
                    50, 440, 250, 120, 60, 0, 0, 255)
            .addMotion("C", 50, 440, 250, 120, 60, 0, 0, 255,
                    70, 440, 370, 120, 60, 0, 170, 85).build();
  }

  @Test
  public void testAddAndRemove() {
    AnimationModel model = new AnimationModelImpl.Builder()
            .declareShape("R", "rectangle").declareShape("C", "ellipse")
            .addMotion("R", 10, 200, 200, 50, 100, 255, 0, 0,
                    50, 300, 300, 50, 100, 255, 0, 0)
            .addMotion("R", 50, 300, 300, 50, 100, 255, 0, 0
                    , 51, 300, 300, 50, 100, 255, 0, 0)
            .addMotion("C", 20, 440, 70, 120, 60, 0, 0, 255,
                    50, 440, 250, 120, 60, 0, 0, 255)
            .addMotion("C", 50, 440, 250, 120, 60, 0, 0, 255,
                    70, 440, 370, 120, 60, 0, 170, 85).build();
    Shapes oval = new Oval("wi", new Posn2D(3, 5),
            new RGB(100, 39, 100), new Size(30, 30));
    assertEquals(2, model.getShapes().size());
    assertEquals(false, model.getShapes().contains(oval));
    model.addShape(oval);
    assertEquals(true, model.getShapes().contains(oval));
  }

  @Test
  public void testGetAnimatedShape() {
    AnimationModel model = new AnimationModelImpl.Builder()
            .declareShape("R", "rectangle").declareShape("C", "ellipse")
            .addMotion("R", 10, 200, 200, 50, 45, 255, 0, 0,
                    50, 300, 300, 50, 100, 255, 0, 0)
            .addMotion("R", 50, 300, 300, 50, 100, 255, 0, 0
                    , 51, 300, 300, 50, 100, 255, 0, 0)
            .addMotion("C", 20, 440, 70, 120, 60, 0, 0, 255,
                    50, 440, 250, 120, 60, 0, 0, 255)
            .addMotion("C", 50, 440, 250, 120, 60, 0, 0, 255,
                    70, 440, 370, 120, 60, 123, 170, 85).build();
    assertEquals(200, model.getAnimatedShapes(10).get(0).getPosition().getX(),
            0.1);
    assertEquals(205, model.getAnimatedShapes(12).get(0).getPosition().getX(),
            0.1);
    assertEquals(45, model.getAnimatedShapes(10).get(0).getSize().getHeight(),
            0.1);
    assertEquals(52, model.getAnimatedShapes(15).get(0).getSize().getHeight(),
            0.1);
    assertEquals(0, model.getAnimatedShapes(50).get(1).getColor().getRed(),
            0.1);
    assertEquals(92, model.getAnimatedShapes(65).get(0).getColor().getRed(),
            0.1);
    Shapes rect1 = model.getShapes().get(0);
    String[] result = rect1.getShapeState().split("\n", 2);
    assertEquals(true, result[0].substring(34).equals(result[1].substring(9, 35)));
  }
}
