import org.junit.Test;

import java.io.StringReader;

import cs3500.animator.controller.Controller;
import cs3500.animator.controller.ControllerImpl;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.view.IView;

import static org.junit.Assert.assertEquals;

/**
 * A test class to test if listeners work properly, connecting the view with the model.
 */
public class MockListenerTest {
  private Appendable appendable;
  private IView testView;

  private void setUp(String text) {
    Readable readable = new StringReader(text);
    appendable = new StringBuilder();
    testView = new MockView(readable);
    AnimationModel testModel = new MockModel(appendable);
    Controller controller = new ControllerImpl(testModel, testView);
  }

  @Test
  public void testRemoveShape() {
    setUp("removeShape");
    testView.render(null);
    assertEquals("Goat Man got removed rip", appendable.toString());
  }

  @Test
  public void testAddShape() {
    setUp("addShape");
    testView.render(null);
    assertEquals("Goat Man", appendable.toString());
  }

  @Test
  public void testAddMotion() {
    setUp("addMotion");
    testView.render(null);
    assertEquals("Goat Man has this motion", appendable.toString());
  }

  @Test
  public void testRemoveMotion() {
    setUp("removeMotion");
    testView.render(null);
    assertEquals("Goat Man just lost a limb :(", appendable.toString());
  }

  @Test
  public void testStart() {
    setUp("Start");
    testView.render(null);
    assertEquals("0", appendable.toString());
  }

  @Test
  public void testPause() {
    setUp("Pause");
    testView.render(null);
    assertEquals("", appendable.toString());
  }

  @Test
  public void testResume() {
    setUp("Resume");
    testView.render(null);
    assertEquals("", appendable.toString());
  }

  @Test
  public void testLoop() {
    setUp("Loop");
    testView.render(null);
    assertEquals("0", appendable.toString());
  }

  @Test
  public void testRestart() {
    setUp("Restart");
    testView.render(null);
    assertEquals("", appendable.toString());
  }
}
