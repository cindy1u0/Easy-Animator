import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.shapes.IReadOnlyShapes;
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
import cs3500.animator.model.shapes.Shapes;
import cs3500.animator.view.FactoryView;
import cs3500.animator.view.IView;

/**
 * A test class for the IView interface.
 */
public class IViewTest {

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

  private AnimationModelImpl a1 = new AnimationModelImpl(shapeList1, commandList1);
  private AnimationModelImpl a2 = new AnimationModelImpl(shapeList2, commandList2);
  private AnimationModelImpl a3 = new AnimationModelImpl(shapeList3, commandList3);
  private AnimationModelImpl a4 = new AnimationModelImpl(shapeList4, commandList4);
  private AnimationModelImpl a5 = new AnimationModelImpl(shapeList5, commandList5);

  @Test
  public void testSvg() {
    IView svg1 = new FactoryView().makeView("svg", 10);
    List<IReadOnlyShapes> list = new ArrayList<>();
    list.addAll(shapeList5);
    svg1.render(list);
    assertEquals(svg1.getText().toString(), "<svg width=\"0\" height=\"0\" " +
            "version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "</svg>");
    list.addAll(shapeList1);
    svg1.render(list);
    assertEquals(svg1.getText().toString(), "<svg width=\"0\" height=\"0\"" +
            " version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "</svg><svg width=\"0\" height=\"0\" version=\"1.1\"" +
            " xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R\" x=\"200.00\" y=\"200.00\" width=\"50.00\"" +
            " height=\"100.00\" fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"10.00s\" " +
            "attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"4.00s\" " +
            "attributeName=\"x\" from=\"200.00\" to=\"300.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"4.00s\" " +
            "attributeName=\"y\" from=\"200.00\" to=\"300.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"5.10s\" dur=\"1.90s\" " +
            "attributeName=\"width\" from=\"50.00\" to=\"25.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"7.00s\" dur=\"3.00s\"" +
            " attributeName=\"x\" from=\"300.00\" to=\"200.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"7.00s\" dur=\"3.00s\" " +
            "attributeName=\"y\" from=\"300.00\" to=\"200.00\" fill=\"freeze\"/>\n" +
            "</rect>\n" +
            "<ellipse id=\"C\" cx=\"440.00\" cy=\"70.00\" rx=\"60.00\"" +
            " ry=\"30.00\" fill=\"rgb(0,0,255)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"2.00s\" dur=\"8.00s\"" +
            " attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2.00s\" dur=\"3.00s\" " +
            "attributeName=\"cy\" from=\"70.00\" to=\"250.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"5.00s\" dur=\"2.00s\" " +
            "attributeName=\"cy\" from=\"250.00\" to=\"370.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"5.00s\" dur=\"2.00s\" " +
            "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" " +
            "fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"7.00s\" dur=\"1.00s\" " +
            "attributeName=\"fill\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" " +
            "fill=\"freeze\"/>\n" +
            "</ellipse>\n" +
            "</svg>");
  }

  @Test
  public void testSvg2() {
    IView svg1 = new FactoryView().makeView("svg", 1);
    List<IReadOnlyShapes> list = new ArrayList<>();
    list.addAll(shapeList2);
    svg1.render(list);
    assertEquals(svg1.getText().toString(), "<svg width=\"0\" height=\"0\"" +
            " version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R\" x=\"200.00\" y=\"200.00\" width=\"50.00\"" +
            " height=\"100.00\" fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"10.00s\" dur=\"100.00s\" " +
            "attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"10.00s\" dur=\"40.00s\" " +
            "attributeName=\"x\" from=\"200.00\" to=\"300.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"10.00s\" dur=\"40.00s\" " +
            "attributeName=\"y\" from=\"200.00\" to=\"300.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"51.00s\" dur=\"19.00s\" " +
            "attributeName=\"width\" from=\"50.00\" to=\"25.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"70.00s\" dur=\"30.00s\" " +
            "attributeName=\"x\" from=\"300.00\" to=\"200.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"70.00s\" dur=\"30.00s\" " +
            "attributeName=\"y\" from=\"300.00\" to=\"200.00\" fill=\"freeze\"/>\n" +
            "</rect>\n" +
            "<rect id=\"dave\" x=\"0.00\" y=\"0.00\" width=\"10.00\" height=\"10.00\"" +
            " fill=\"rgb(0,255,0)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"100.00s\"" +
            " attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"4.00s\" " +
            "attributeName=\"x\" from=\"0.00\" to=\"60.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"4.00s\" " +
            "attributeName=\"y\" from=\"0.00\" to=\"80.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"5.00s\" dur=\"5.00s\"" +
            " attributeName=\"width\" from=\"10.00\" to=\"5.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"5.00s\" dur=\"5.00s\" " +
            "attributeName=\"height\" from=\"10.00\" to=\"5.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"71.00s\" dur=\"29.00s\" " +
            "attributeName=\"fill\" from=\"rgb(0,255,0)\" to=\"rgb(200,0,255)\"" +
            " fill=\"freeze\"/>\n" +
            "</rect>\n" +
            "</svg>");
  }

  @Test
  public void testSvg3() {
    IView svg1 = new FactoryView().makeView("svg", 5);
    List<IReadOnlyShapes> list = new ArrayList<>();
    list.addAll(shapeList3);
    svg1.render(list);
    assertEquals(svg1.getText().toString(), "<svg width=\"0\" height=\"0\" " +
            "version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R\" x=\"200.00\" y=\"200.00\" width=\"50.00\" height=\"100.00\"" +
            " fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"2.00s\" dur=\"20.00s\" " +
            "attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2.00s\" dur=\"8.00s\"" +
            " attributeName=\"x\" from=\"200.00\" to=\"300.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2.00s\" dur=\"8.00s\" " +
            "attributeName=\"y\" from=\"200.00\" to=\"300.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"10.20s\" dur=\"3.80s\" " +
            "attributeName=\"width\" from=\"50.00\" to=\"25.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"14.00s\" dur=\"6.00s\" " +
            "attributeName=\"x\" from=\"300.00\" to=\"200.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"14.00s\" dur=\"6.00s\" " +
            "attributeName=\"y\" from=\"300.00\" to=\"200.00\" fill=\"freeze\"/>\n" +
            "</rect>\n" +
            "<ellipse id=\"C\" cx=\"440.00\" cy=\"70.00\" rx=\"60.00\" ry=\"30.00\"" +
            " fill=\"rgb(0,0,255)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"4.00s\" dur=\"16.00s\" " +
            "attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"4.00s\" dur=\"6.00s\" " +
            "attributeName=\"cy\" from=\"70.00\" to=\"250.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"10.00s\" dur=\"4.00s\" " +
            "attributeName=\"cy\" from=\"250.00\" to=\"370.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"10.00s\" dur=\"4.00s\" " +
            "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\"" +
            " fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"14.00s\" dur=\"2.00s\"" +
            " attributeName=\"fill\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" " +
            "fill=\"freeze\"/>\n" +
            "</ellipse>\n" +
            "<ellipse id=\"wi\" cx=\"3.00\" cy=\"5.00\" rx=\"15.00\" ry=\"15.00\"" +
            " fill=\"rgb(100,39,100)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"0.20s\" dur=\"4.00s\" " +
            "attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"0.20s\" dur=\"1.20s\" " +
            "attributeName=\"rx\" from=\"30.00\" to=\"80.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"0.20s\" dur=\"1.20s\" " +
            "attributeName=\"ry\" from=\"30.00\" to=\"100.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.40s\" dur=\"0.60s\" " +
            "attributeName=\"cx\" from=\"3.00\" to=\"20.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.40s\" dur=\"0.60s\"" +
            " attributeName=\"cy\" from=\"5.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2.00s\" dur=\"2.00s\" " +
            "attributeName=\"rx\" from=\"80.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2.00s\" dur=\"2.00s\"" +
            " attributeName=\"ry\" from=\"100.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
            "</ellipse>\n" +
            "</svg>");
  }

  @Test
  public void testSvg4() {
    IView svg1 = new FactoryView().makeView("svg", 5);
    List<IReadOnlyShapes> list = new ArrayList<>();
    list.addAll(shapeList4);
    svg1.render(list);
    assertEquals(svg1.getText().toString(), "<svg width=\"0\" height=\"0\"" +
            " version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<ellipse id=\"wi\" cx=\"3.00\" cy=\"5.00\" rx=\"15.00\" ry=\"15.00\"" +
            " fill=\"rgb(100,39,100)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"0.20s\" dur=\"4.00s\"" +
            " attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"0.20s\" dur=\"1.20s\"" +
            " attributeName=\"rx\" from=\"30.00\" to=\"80.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"0.20s\" dur=\"1.20s\"" +
            " attributeName=\"ry\" from=\"30.00\" to=\"100.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.40s\" dur=\"0.60s\"" +
            " attributeName=\"cx\" from=\"3.00\" to=\"20.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.40s\" dur=\"0.60s\"" +
            " attributeName=\"cy\" from=\"5.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2.00s\" dur=\"2.00s\"" +
            " attributeName=\"rx\" from=\"80.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2.00s\" dur=\"2.00s\"" +
            " attributeName=\"ry\" from=\"100.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
            "</ellipse>\n" +
            "</svg>");// }
  }
}
