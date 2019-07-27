package cs3500.animator.model.tools;

import java.util.Objects;

import cs3500.animator.model.shapes.AbstractShapes;

/**
 * Represents an interval with the starting second and the ending second.
 */
public final class Interval {
  private final int starting;
  private final int ending;

  /**
   * A constructor that sets the starting second and the ending second.
   *
   * @param starting starting point of the interval
   * @param ending   ending pooint of the interval
   */
  public Interval(int starting, int ending) {
    this.starting = starting;
    this.ending = ending;
    ensureTime();
  }

  /**
   * Ensures the time intervals are positive.
   *
   * @throws IllegalArgumentException if either ends of this interval is negative
   */
  private void ensureTime() throws IllegalArgumentException {
    if (this.starting < 0 || this.ending < 0 || this.starting > this.ending) {
      throw new IllegalArgumentException("that's not an actual class sir");
    }
  }

  /**
   * Overridden equals method that checks if two intervals are the same.
   *
   * @param that the object that is checked
   * @return true if the object is an interval and it is the same as the current interval
   */
  @Override
  public boolean equals(Object that) {
    if (!(that instanceof Interval)) {
      return false;
    }
    Interval in = (Interval) that;
    return this.starting == in.starting
            && this.ending == in.ending;
  }

  /**
   * Overridden hashCode method that returns current interval's hashCode.
   *
   * @return an integer that represents the current interval
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.starting, this.ending);
  }

  /**
   * Checks if given Interval {@code i} overlaps with this Interval.
   *
   * @param i the given Interval to be checked for overlaps with this
   * @return true if Interval {@code i} overlaps with this Interval
   * @throws IllegalArgumentException if Object {@code i} is null
   */
  public boolean doesOverlap(Interval i) throws IllegalArgumentException {
    AbstractShapes.ensureNull(i);
    return (i.starting < this.starting && this.starting < i.ending)
            || (i.starting < this.ending && this.ending < i.ending)
            || (this.starting < i.starting && i.ending < this.ending);
  }

  /**
   * Gets the starting time of this Interval.
   *
   * @return this Interval's starting time.
   */
  public int getStarting() {
    return this.starting;
  }

  /**
   * Gets the ending time of this Interval.
   *
   * @return this Interval's ending time.
   */
  public int getEnding() {
    return this.ending;
  }
}

