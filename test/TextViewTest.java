import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.view.TextView;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for class TextView.
 */
public class TextViewTest {
  AnimationModel model = new AnimationModelImpl.Builder()
          .declareShape("R", "rectangle").declareShape("C", "ellipse")
          .addMotion("R", 10, 200, 200, 50, 45, 255, 0, 0,
                  50, 300, 300, 50, 100, 255, 0, 0)
          .addMotion("R", 50, 300, 300, 50, 100, 255, 0, 0
                  , 51, 300, 300, 50, 100, 255, 0, 0)
          .addMotion("C", 20, 440, 70, 120, 60, 0, 0, 255,
                  50, 440, 250, 120, 60, 0, 0, 255)
          .addMotion("C", 50, 440, 250, 120, 60, 0, 0, 255,
                  70, 440, 370, 120, 60, 123, 170, 85)
          .setBounds(200, 70, 300, 300).build();
  private List<IReadOnlyShapes> ir = new ArrayList<>();
  private TextView tv = new TextView();

  @Test
  public void testOutPut() {
    ir.add(model.convertShapes().get(0));
    ir.add(model.convertShapes().get(1));
    assertEquals("", tv.getText().toString());
    tv.setCanvas(model.getCanvas());
    tv.render(ir);
    assertEquals("canvas 200 70 300 300\n" +
            "shape R Rectangle\n" +
            "motion R 10 200 200 50 45 255 0 0 50 300 300 50 100 255 0 0 \n" +
            "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0 \n" +
            "\n" +
            "shape C Oval\n" +
            "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255 \n" +
            "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 123 170 85 \n" +
            "\n", tv.getText().toString());

    assertEquals(0, tv.getTempo());
  }

  @Test(expected = NullPointerException.class)
  public void testNull() {
    tv.setCanvas(null);
    tv.render(null);
  }
}
