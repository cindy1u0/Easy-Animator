import org.junit.Test;

import cs3500.animator.model.tools.Interval;
import cs3500.animator.model.tools.TreeComparator;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the method in tree comparator class.
 */
public class TreeComparatorTest {
  private final Interval interval1 = new Interval(1, 10);
  private final Interval interval2 = new Interval(10, 15);
  private final Interval interval3 = new Interval(10, 13);
  private final Interval interval4 = new Interval(1, 10);
  private final TreeComparator tc = new TreeComparator();

  @Test
  public void testComparator() {
    assertEquals(-1, tc.compare(interval1, interval2));
    assertEquals(0, tc.compare(interval1, interval4));
    assertEquals(1, tc.compare(interval2, interval3));
  }
}
