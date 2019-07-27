import org.junit.Test;

import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;
import cs3500.animator.view.FactoryView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualView;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for Utility classes RGB, Posn2D, and Size including setters and getters etc.
 */
public class UtilsTest {
  private final Posn2D p1 = new Posn2D(2, 3);
  private final Posn2D p2 = new Posn2D(2, 5);
  private final Posn2D p3 = new Posn2D(3, 5);
  private final Posn2D p4 = new Posn2D(6, 10);

  private final RGB rgb1 = new RGB(0, 255, 255);
  private final RGB rgb2 = new RGB(3, 100, 40);
  private final RGB rgb3 = new RGB(182, 20, 49);
  private final RGB rgb4 = new RGB(84, 20, 100);

  private final Size size1 = new Size(10, 200);
  private final Size size2 = new Size(10, 80);
  private final Size size3 = new Size(1, 392);
  private final Size size4 = new Size(39, 2);

  @Test
  public void testGetters() {
    assertEquals(2, p1.getX(), 0.1);
    assertEquals(5, p2.getY(), 0.1);
    assertEquals(0, rgb1.getRed(), 0.1);
    assertEquals(100, rgb2.getGreen(), 0.1);
    assertEquals(49, rgb3.getBlue(), 0.1);
    assertEquals(10, size1.getWidth(), 0.1);
    assertEquals(392, size3.getHeight(), 0.1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalid() {
    new RGB(-1, 200, 255);
    new RGB(-1, -3, 255);
    new RGB(-1, -3, 255);
    new RGB(32, -3, 255);
    new RGB(32, 40, -3);
    new RGB(-1, -4, -5);
    new RGB(20, 260, 25);
    new RGB(300, 250, 80);
    new RGB(240, 34, 800);

    new Size(-1, 3);
    new Size(-1, -33);
    new Size(40, -30);
  }

  @Test
  public void testSetters() {
    rgb1.setRGB(rgb4);
    assertEquals(84, rgb1.getRed(), 0.1);
    assertEquals(20, rgb1.getGreen(), 0.1);
    assertEquals(100, rgb1.getBlue(), 0.1);

    p3.setPosn(p4);
    assertEquals(6, p3.getX(), 0.1);
    assertEquals(10, p3.getY(), 0.1);

    size2.setSize(size4);
    assertEquals(39, size2.getWidth(), 0.1);
    assertEquals(2, size2.getHeight(), 0.1);
  }

  @Test
  public void testFactoryView() {
    FactoryView view = new FactoryView();
    assertEquals(TextView.class, view.makeView("text", 2).getClass());
    assertEquals(0, view.makeView("text", 2).getTempo());
    assertEquals(VisualView.class, view.makeView("visual", 3).getClass());
    assertEquals(3, view.makeView("visual", 3).getTempo());
    assertEquals(SVGView.class, view.makeView("svg", 3).getClass());
    assertEquals(0, view.makeView("svg", 10).getTempo());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFactoryViewException() {
    FactoryView view = new FactoryView();
    view.makeView("ds", 2);
  }

  @Test(expected = NullPointerException.class)
  public void testFactoryViewExceptionNull() {
    FactoryView view = new FactoryView();
    view.makeView(null, 2);
  }
}
