import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.Controller;
import cs3500.animator.controller.ControllerImpl;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.commands.ChangeColor;
import cs3500.animator.model.commands.Command;
import cs3500.animator.model.commands.Move;
import cs3500.animator.model.commands.Resize;
import cs3500.animator.model.shapes.Oval;
import cs3500.animator.model.shapes.Rect;
import cs3500.animator.model.shapes.Shapes;
import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.Posn2D;
import cs3500.animator.model.tools.RGB;
import cs3500.animator.model.tools.Size;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextView;

/**
 * A test class for the public facing methods in {@link Controller}.
 */
public class ControllerTest {
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

  private TextView textView1 = new TextView();
  private SVGView svgView1 = new SVGView(1);
  private SVGView svgView2 = new SVGView(10);

  private Controller c1 = new ControllerImpl(a1, textView1);
  private Controller c2 = new ControllerImpl(a2, svgView1);
  private Controller c3 = new ControllerImpl(a3, textView1);
  private Controller c4 = new ControllerImpl(a4, svgView2);
  private Controller c5 = new ControllerImpl(a5, textView1);


  // a mock appendable class to test
  private class MockAppendable implements Appendable {

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException("error");
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException("error");
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException("error");
    }
  }

  // tests for appendable nonnull
  @Test(expected = NullPointerException.class)
  public void appendNull() {
    c4.run(null);
    c5.run(null);
  }

  // tests the state exception for appendable
  @Test(expected = IllegalStateException.class)
  public void testBadAppendable() {
    c1.run(new MockAppendable());
    c2.run(new MockAppendable());
  }

  // tests the run method
  // tests for file written correctly
  @Test
  public void testFile() {
    String file = "yeehaw.txt";
    StringBuilder poop = new StringBuilder();
    try {
      FileWriter yeehaw = new FileWriter("yeehaw.txt");
      c1.run(yeehaw);
      yeehaw.flush();
      yeehaw.close();
      BufferedReader buffJeff = new BufferedReader(new FileReader(file));
      String line;
      while ((line = buffJeff.readLine()) != null) {
        poop.append(line).append("\n");
      }

      assertEquals(poop.toString(), "canvas 0 0 700 700\n" +
              "shape R Rectangle\n" +
              "motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0 \n" +
              "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0 \n" +
              "motion R 51 300 300 50 100 255 0 0 70 300 300 25 100 255 0 0 \n" +
              "motion R 70 300 300 25 100 255 0 0 100 200 200 25 100 255 0 0 \n" +
              "\n" +
              "shape C Oval\n" +
              "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255 \n" +
              "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85 \n" +
              "motion C 70 440 370 120 60 0 170 85 80 440 370 120 60 0 255 0 \n" +
              "\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Test
  public void testFileSVG() {
    String file = "iAmBob.txt";
    StringBuilder poop = new StringBuilder();
    try {
      FileWriter yeehaw = new FileWriter("iAmBob.txt");
      c4.run(yeehaw);
      yeehaw.flush();
      yeehaw.close();
      BufferedReader buffJeff = new BufferedReader(new FileReader(file));
      String line;
      while ((line = buffJeff.readLine()) != null) {
        poop.append(line).append("\n");
      }

      assertEquals(poop.toString(), "<svg width=\"700\" height=\"700\"" +
              " version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
              "<ellipse id=\"wi\" cx=\"3.00\" cy=\"5.00\" rx=\"15.00\" " +
              "ry=\"15.00\" fill=\"rgb(100,39,100)\" visibility=\"hidden\">\n" +
              "<animate attributeType=\"xml\" begin=\"0.10s\" dur=\"2.00s\"" +
              " attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
              "<animate attributeType=\"xml\" begin=\"0.10s\" dur=\"0.60s\"" +
              " attributeName=\"rx\" from=\"30.00\" to=\"80.00\" fill=\"freeze\"/>\n" +
              "<animate attributeType=\"xml\" begin=\"0.10s\" dur=\"0.60s\" " +
              "attributeName=\"ry\" from=\"30.00\" to=\"100.00\" fill=\"freeze\"/>\n" +
              "<animate attributeType=\"xml\" begin=\"0.70s\" dur=\"0.30s\" " +
              "attributeName=\"cx\" from=\"3.00\" to=\"20.00\" fill=\"freeze\"/>\n" +
              "<animate attributeType=\"xml\" begin=\"0.70s\" dur=\"0.30s\"" +
              " attributeName=\"cy\" from=\"5.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
              "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"1.00s\"" +
              " attributeName=\"rx\" from=\"80.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
              "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"1.00s\"" +
              " attributeName=\"ry\" from=\"100.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
              "</ellipse>\n" +
              "</svg>\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  // tests for text view
  @Test
  public void runText() {
    StringBuilder yee = new StringBuilder();
    c1.run(yee);
    assertEquals(yee.toString(), "canvas 0 0 700 700\n" +
            "shape R Rectangle\n" +
            "motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0 \n" +
            "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0 \n" +
            "motion R 51 300 300 50 100 255 0 0 70 300 300 25 100 255 0 0 \n" +
            "motion R 70 300 300 25 100 255 0 0 100 200 200 25 100 255 0 0 \n" +
            "\n" +
            "shape C Oval\n" +
            "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255 \n" +
            "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85 \n" +
            "motion C 70 440 370 120 60 0 170 85 80 440 370 120 60 0 255 0 \n\n");

    StringBuilder haw = new StringBuilder();
    c3.run(haw);
    assertEquals(haw.toString(), "canvas 0 0 700 700\n" +
            "shape R Rectangle\n" +
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
            "canvas 0 0 700 700\n" +
            "shape R Rectangle\n" +
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
            "motion wi 10 3 5 80 100 100 39 100 20 3 5 30 30 100 39 100 \n\n");

    StringBuilder dududu = new StringBuilder();
    c5.run(dududu);
    assertEquals(dududu.toString(), "canvas 0 0 700 700\n" +
            "shape R Rectangle\n" +
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
            "canvas 0 0 700 700\n" +
            "shape R Rectangle\n" +
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
            "\n" +
            "canvas 0 0 700 700\n");
  }

  // tests for svg view
  @Test
  public void runSvg() {
    StringBuilder ood = new StringBuilder();
    c2.run(ood);
    assertEquals(ood.toString(), "<svg width=\"700\" height=\"700\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R\" x=\"200.00\" y=\"200.00\" width=\"50.00\" height=\"100.00\"" +
            " fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"10.00s\" dur=\"100.00s\"" +
            " attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
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
            "<rect id=\"dave\" x=\"0.00\" y=\"0.00\" width=\"10.00\" height=\"10.00\" " +
            "fill=\"rgb(0,255,0)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"100.00s\" " +
            "attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"4.00s\" " +
            "attributeName=\"x\" from=\"0.00\" to=\"60.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"4.00s\"" +
            " attributeName=\"y\" from=\"0.00\" to=\"80.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"5.00s\" dur=\"5.00s\"" +
            " attributeName=\"width\" from=\"10.00\" to=\"5.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"5.00s\" dur=\"5.00s\" " +
            "attributeName=\"height\" from=\"10.00\" to=\"5.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"71.00s\" dur=\"29.00s\"" +
            " attributeName=\"fill\" from=\"rgb(0,255,0)\" " +
            "to=\"rgb(200,0,255)\" fill=\"freeze\"/>\n" +
            "</rect>\n" +
            "</svg>");

    StringBuilder isFun = new StringBuilder();
    c4.run(isFun);
    assertEquals(isFun.toString(), "<svg width=\"700\" height=\"700\" " +
            "version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<ellipse id=\"wi\" cx=\"3.00\" cy=\"5.00\" rx=\"15.00\" ry=\"15.00\" " +
            "fill=\"rgb(100,39,100)\" visibility=\"hidden\">\n" +
            "<animate attributeType=\"xml\" begin=\"0.10s\" dur=\"2.00s\"" +
            " attributeName=\"visibility\" from=\"visible\" to=\"hidden\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"0.10s\" dur=\"0.60s\"" +
            " attributeName=\"rx\" from=\"30.00\" to=\"80.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"0.10s\" dur=\"0.60s\" " +
            "attributeName=\"ry\" from=\"30.00\" to=\"100.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"0.70s\" dur=\"0.30s\" " +
            "attributeName=\"cx\" from=\"3.00\" to=\"20.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"0.70s\" dur=\"0.30s\" " +
            "attributeName=\"cy\" from=\"5.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"1.00s\"" +
            " attributeName=\"rx\" from=\"80.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1.00s\" dur=\"1.00s\"" +
            " attributeName=\"ry\" from=\"100.00\" to=\"30.00\" fill=\"freeze\"/>\n" +
            "</ellipse>\n" +
            "</svg>");
  }
}