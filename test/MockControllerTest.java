import org.junit.Test;

import java.io.StringReader;

import cs3500.animator.view.IView;
import cs3500.animator.view.IViewFeatures;

import static org.junit.Assert.assertEquals;

/**
 * Tests that the view is correctly communicating with the controller through mocks.
 */
public class MockControllerTest {
  private Appendable appendable;
  private IView testView;

  private void setUp(String text) {
    Readable readable = new StringReader(text);
    testView = new MockView(readable);
    appendable = new StringBuilder();
    IViewFeatures testController = new TestController(testView, appendable);
  }

  @Test
  public void testRemoveShape() {
    setUp("removeShape");
    testView.render(null);
    assertEquals("Goodbye shape! Hello blank walls!", appendable.toString());
  }

  @Test
  public void testAddShape() {
    setUp("addShape");
    testView.render(null);
    assertEquals("Wowee you added a shape!", appendable.toString());
  }

  @Test
  public void testAddMotion() {
    setUp("addMotion");
    testView.render(null);
    assertEquals("Go the gym for them key motions!", appendable.toString());
  }

  @Test
  public void testRemoveMotion() {
    setUp("removeMotion");
    testView.render(null);
    assertEquals("Awh man you chose not to go to the gym?", appendable.toString());
  }

  @Test
  public void testStart() {
    setUp("Start");
    testView.render(null);
    assertEquals("Woah you pressed start!", appendable.toString());
  }

  @Test
  public void testPause() {
    setUp("Pause");
    testView.render(null);
    assertEquals("Congrats you paused!", appendable.toString());
  }

  @Test
  public void testResume() {
    setUp("Resume");
    testView.render(null);
    assertEquals("Awh why'd you resume?", appendable.toString());
  }

  @Test
  public void testLoop() {
    setUp("Loop");
    testView.render(null);
    assertEquals("Is it actually looping tho?", appendable.toString());
  }

  @Test
  public void testRestart() {
    setUp("Restart");
    testView.render(null);
    assertEquals("Please don't restart.", appendable.toString());
  }
}
