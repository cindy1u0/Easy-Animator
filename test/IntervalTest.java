import org.junit.Test;

import cs3500.animator.model.tools.Interval;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * All the tests relate to the Interval class, including overlaps, getters, equals, and hashcode.
 */
public class IntervalTest {
  private final Interval interval1 = new Interval(1, 10);
  private final Interval interval2 = new Interval(10, 15);
  private final Interval interval3 = new Interval(15, 17);
  private final Interval interval4 = new Interval(2, 5);
  private final Interval interval5 = new Interval(8, 11);
  private final Interval interval6 = new Interval(13, 16);

  @Test
  public void testOverlaps() {
    assertEquals(false, interval1.doesOverlap(interval3));
    assertEquals(false, interval3.doesOverlap(interval1));
    assertEquals(false, interval2.doesOverlap(interval1));
    assertEquals(false, interval3.doesOverlap(interval2));
    assertEquals(true, interval4.doesOverlap(interval1));
    assertEquals(true, interval5.doesOverlap(interval2));
    assertEquals(true, interval6.doesOverlap(interval2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegHeight() {
    new Interval(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegWidth() {
    new Interval(1, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegBoth() {
    new Interval(-31, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBiggerStarting() {
    new Interval(4, 2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNull() {
    interval1.doesOverlap(null);
  }

  @Test
  public void testGetters() {
    assertEquals(1, interval1.getStarting());
    assertEquals(15, interval3.getStarting());
    assertEquals(5, interval4.getEnding());
    assertEquals(11, interval5.getEnding());
  }


  @Test
  public void testHashCode() {
    assertNotEquals(interval3.hashCode(), interval1.hashCode());
    assertNotEquals(interval4.hashCode(), interval3.hashCode());
  }

  @Test
  public void testEquals() {
    assertNotEquals(interval1, interval2);
    assertNotEquals(new Interval(10, 16), interval2);
    assertNotEquals(new Interval(7, 11), interval5);
    assertEquals(new Interval(2, 5), interval4);
  }
}
