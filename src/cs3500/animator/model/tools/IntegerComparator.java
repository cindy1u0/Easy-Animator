package cs3500.animator.model.tools;

import java.util.Comparator;

/**
 * A class that compares Integer keys in keyframes and implements the Comparator interface.
 */
public class IntegerComparator implements Comparator<Integer> {
  /**
   * Compares {@code o1} and {@code o2} in terms of their starting times.
   *
   * @return a value indicating whether {@code o1} or {@code o2} occurs earlier.
   */
  @Override
  public int compare(Integer o1, Integer o2) {
    return o1.compareTo(o2);
  }
}