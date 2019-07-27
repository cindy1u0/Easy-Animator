package cs3500.animator.model.tools;

import java.util.Comparator;

/**
 * A class that compares Intervals and implements the Comparator interface.
 */
public class TreeComparator implements Comparator<Interval> {
  /**
   * Compares {@code o1} and {@code o2} in terms of their starting times. If both intervals begin at
   * the same time, then {@code o1} and {@code o2} are compared at their ending times.
   *
   * @return a value indicating whether {@code o1} or {@code o2} occurs earlier.
   */
  @Override
  public int compare(Interval o1, Interval o2) {
    if (o1.getStarting() == o2.getStarting()) {
      return Integer.compare(o1.getEnding(), o2.getEnding());
    }
    return Integer.compare(o1.getStarting(), o2.getStarting());
  }
}

